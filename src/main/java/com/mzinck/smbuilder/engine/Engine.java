package com.mzinck.smbuilder.engine;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.account.platform.Instagram;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieve;
import com.mzinck.smbuilder.net.Database;
import com.sapher.youtubedl.YoutubeDL;
import com.sapher.youtubedl.YoutubeDLException;
import com.sapher.youtubedl.YoutubeDLRequest;
import com.sapher.youtubedl.YoutubeDLResponse;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * The brain of the program.
 * @author Mitchell Zinck Copyright (2018)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Engine {

    public static Config config;
    public static Database connection;
    public static ContentRetrieve contentRetrieve;
    public static ArrayList<Account> accounts;
    public static Account testAccount;
    public static ScheduledExecutorService contentScheduler, postingScheduler;

    public final static boolean PRODUCTION = true;

    /**
     * Every 24 hours retrieve content from Reddit that is associated with all active Instagram Accounts.
     * Every 2 hours post new content to all accounts.
     */
    public static void main(String[] args) {
        config = new Config();
        config.setConfig();
        connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());
        connection.connect();
        contentRetrieve = new ContentRetrieve(connection, config);

        if(PRODUCTION == false) {
            testing();
            return;
        }

        contentScheduler = Executors.newScheduledThreadPool(1);
        contentScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                doContentRetrieve();
                System.out.println("Finished retrieving content @ " + System.currentTimeMillis());
            }
        }, 12, 12, TimeUnit.HOURS); // dont run 2/26

        postingScheduler = Executors.newScheduledThreadPool(1);
        postingScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                postContent();
                System.out.println("Finished posting content @ " + System.currentTimeMillis());
            }
        }, 0, 60, TimeUnit.MINUTES);

    }

    /**
     * Initiates and retrieves content.
     */
    public static void doContentRetrieve() {
        contentRetrieve.initiate();
        contentRetrieve.retrieveAndStoreContent();
        connection.closeConnection();
    }

    /**
     * Posts content on all active accounts along with a new story.
     */
    public static void postContent() {
        accounts = connection.grabAllAccounts();
        contentRetrieve.initiate();
        for(Account account : accounts) {
            if(account instanceof Instagram) {
                boolean hasPosted = false;
                while (hasPosted == false) {
                    boolean isDuplicate = true;
                    Content content = null;
                    while (isDuplicate == true) {
                        content = connection.grabNextPost(account.getTag().getTags().toArray(new String[0]));
                        if (content == null) {
                            ((Instagram) account).logout();
                            connection.closeConnection();
                            return;
                        }
                        if (saveContent(content, false) == false) {
                            System.out.println("Duplicate Post: \"" + content.getPostTitle() + "\'");
                            connection.setContentPosted(content.getId(), true);
                            continue;
                        }
                        isDuplicate = false;
                    }
                    ((Instagram) account).login();
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (content.getUrl().contains(".jpg") || content.getUrl().contains(".png")) {
                        hasPosted = ((Instagram) account).postPicture(content, account.getTag());
                    } else {
                        hasPosted =((Instagram) account).postVideo(content, account.getTag());
                    }
                    try {
                        Thread.sleep(15000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Content story = contentRetrieve.retrieveStoryContent(account);
                    if (story != null) {
                        saveContent(story, true);
                        ((Instagram) account).postStory(story);
                    }
                    connection.setContentPosted(content.getId(), true);
                    ((Instagram) account).logout();
                }

                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        connection.closeConnection();
    }

    public static boolean redditVideoDownload(String filename, String videoUrl) {

        // Destination directory
        String directory = "C:\\Users\\Mitchell\\Desktop\\memes\\videos";

        // Build request
        YoutubeDLRequest request = new YoutubeDLRequest(videoUrl, directory);
        request.setOption("ffmpeg-location \"C:\\Program Files\\nodejs\"");
        request.setOption("output \"" + filename + ".mp4\"");
        YoutubeDLResponse response = null;
        try {
            response = YoutubeDL.execute(request);
        } catch (YoutubeDLException e) {
            e.printStackTrace();
        }
        Path source = Paths.get("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + filename + ".mp4");
        try {
            Files.move(source, source.resolveSibling("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + filename));
        } catch (IOException e) {
            e.printStackTrace();
            if(e instanceof  FileAlreadyExistsException) {
                return false;
            }
        }
        return true;
    }

    /**
     * Saves the content locally to be posted.
     */
    public static boolean saveContent(Content content, boolean story) {
        if(story == true) {
            try {
                FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + content.getPostTitleAsMD5()));
                fixAspectRatio("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + content.getPostTitleAsMD5());
            } catch (IOException e) {
                e.printStackTrace();
                if(e instanceof  FileAlreadyExistsException) {
                    return false;
                }
            }
            return true;
        }
        if(content.getUrl().contains(".jpg") || content.getUrl().contains(".png")) {
            try {
                FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + content.getPostTitleAsMD5()));
                fixAspectRatio("C:\\Users\\Mitchell\\Desktop\\memes\\" + content.getPostTitleAsMD5());
            } catch (IOException e) {
                e.printStackTrace();
                if(e instanceof  FileAlreadyExistsException) {
                    return false;
                }
            }
            return true;
        }

        if(content.getUrl().contains("v.redd.it")) {
            if(redditVideoDownload(content.getPostTitleAsMD5(), content.getUrl()) == false) {
                return false;
            }
            return true;
        }
        try {
            if(content.getUrl().contains("gifv") || content.getUrl().contains("mp4")) {
                content.setUrl(content.getUrl().substring(0, content.getUrl().length() - 5) + ".mp4");
            }
            FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + content.getPostTitleAsMD5()));
        } catch (IOException e) {
            e.printStackTrace();
            if(e instanceof  FileAlreadyExistsException) {
                return false;
            }
        }
        return true;
    }

    public static BufferedImage getSRGBBufferedImage(BufferedImage image) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace
                .getInstance(ColorSpace.CS_sRGB), null);
        BufferedImage sourceImgGray = op.filter(image, null);

        return sourceImgGray;
    }

    /**
     * Sometimes images are outside of Instagrams acceptable aspect ratio range.
     * This function adds whitespace when needed so the image can be uploaded to Instagram.
     * More @ <link>https://faq.buffer.com/article/951-publish-image-aspect-ratios-for-instagram-direct-scheduling</link>
     */
    public static void fixAspectRatio(String filePath) {
        File file = new File(filePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {}
        double ar = (double)image.getWidth() / (double)image.getHeight();

        if(ar > 0.8 && ar < 1.91) { //within spec
            return;
        }

        if(image.getHeight() > image.getWidth()) { //image is too tall
            if(ar <= 0.8) {
                double whitespace = (double)image.getHeight() * 0.82;//max portrait ratio
                BufferedImage newImage = new BufferedImage((int)whitespace, image.getHeight(), image.getType());

                Graphics g = newImage.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0,0,(int)whitespace,image.getHeight());
                g.drawImage(image, (((int)whitespace - image.getWidth()) / 2), 0, null);
                g.dispose();
                newImage = getSRGBBufferedImage(newImage);
                File outputfile = new File(filePath);
                if(!outputfile.exists()) {
                    try {
                        outputfile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    ImageIO.write(newImage, "jpg", outputfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else { //image is too wide
            if(ar >= 1.91) {
                double whitespace = (double)image.getWidth() / 1.89; //max landscape aspect ratio
                BufferedImage newImage = new BufferedImage(image.getWidth(), (int)whitespace, image.getType());

                Graphics g = newImage.getGraphics();

                g.setColor(Color.white);
                g.fillRect(0,0, image.getWidth(),(int) whitespace);
                g.drawImage(image, 0, (((int)whitespace - image.getHeight()) / 2), null);
                g.dispose();
                File outputfile = new File(filePath);
                newImage = getSRGBBufferedImage(newImage);
                if(!outputfile.exists()) {
                    try {
                        outputfile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    ImageIO.write(newImage, "jpg", outputfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Set of commands for testing specific functions.
     */
    public static void testing() {
        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()) {
            String command = scan.nextLine();
            if(command.equalsIgnoreCase("db")) {
                connection.connect();
                System.out.println("Successfully connected to database " + connection.getUrl());
            }
            if(command.equalsIgnoreCase("login")) {
                testAccount = connection.grabTestAccount();
                ((Instagram) testAccount).login();
            } else if(command.contains("follow")) {
                String username = command.split(" ")[1];
                ((Instagram) testAccount).follow(username);
            } else if(command.equalsIgnoreCase("postPicture")) {
                ArrayList<Content> content = connection.grabAllTodaysContent();
                Content c = content.get(0);
                try {
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + c.getPostTitleAsMD5()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).postPicture(content.get(0), testAccount.getTag());
            } else if(command.contains("closedb")) {
                connection.closeConnection();
            } else if(command.contains("poststory")) {
                Content story = contentRetrieve.retrieveStoryContent(testAccount);
                ((Instagram) testAccount).postStory(story);
            } else if(command.equalsIgnoreCase("postvideo")) {
                ArrayList<Content> content = connection.grabAllTodaysContent();
                Content c = content.get(0);
                try {
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + c.getPostTitleAsMD5()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).postVideo(content.get(0), testAccount.getTag());
            }
        }
    }

}
