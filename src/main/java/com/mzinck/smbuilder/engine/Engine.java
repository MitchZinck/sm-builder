package com.mzinck.smbuilder.engine;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.account.platform.Instagram;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieve;
import com.mzinck.smbuilder.net.Database;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TO-DO
//- Auto posting
//- Posting with tags
//- Follow 5 users per day


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

    public final static boolean PRODUCTION = false;

    public static void main(String[] args) {
        config = new Config();
        config.setConfig();
        connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());
        connection.connect();
        contentRetrieve = new ContentRetrieve(connection, config);

        //doContentRetrieve();
        postContent();

        if(PRODUCTION == false) {
            testing();
            return;
        }

        contentScheduler = Executors.newScheduledThreadPool(1);
        contentScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                doContentRetrieve();
            }
        }, 0, 24, TimeUnit.HOURS);

        postingScheduler = Executors.newScheduledThreadPool(1);
        postingScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 2, TimeUnit.HOURS);

    }

    /**
     * Sets a daily routine of retrieving and storing content for future posting.
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
                Content content = connection.grabNextPost(account.getTag().getTags().toArray(new String[0]));
                if(content == null) {
                    continue;
                }
                saveContent(content, false);
                ((Instagram)account).login();
                if(content.getUrl().contains(".jpg") || content.getUrl().contains(".png")) {
                    ((Instagram)account).postPicture(content, account.getTag());
                } else {
                    ((Instagram)account).postVideo(content, account.getTag());
                }
                Content story = contentRetrieve.retrieveStoryContent(account);
                if(story != null) {
                    saveContent(story, true);
                    ((Instagram) account).postStory(story);
                }
                // READDTHIS connection.setContentPosted(content.getId(), true);
                ((Instagram)account).follow("mitchzinck");
                ((Instagram)account).logout();
            }
        }
        connection.closeConnection();
    }

    /**
     * Saves the content locally to be posted.
     */
    public static void saveContent(Content content, boolean story) {
        if(story == true) {
            try {
                FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + content.getPostTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        if(content.getUrl().contains(".jpg") || content.getUrl().contains(".png")) {
            try {
                FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + content.getPostTitle()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            if(content.getUrl().contains("gifv")) {
                content.setUrl(content.getUrl().substring(0, content.getUrl().length() - 5) + ".mp4");
            }
            FileUtils.copyURLToFile(new URL(content.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + content.getPostTitle()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fixAspectRatio() {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    public static void loginTestAccInstagram() {
        ((Instagram)testAccount).login();
    }

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
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + c.getPostTitle()));
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
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + c.getPostTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).postVideo(content.get(0), testAccount.getTag());
            }
        }
    }

}
