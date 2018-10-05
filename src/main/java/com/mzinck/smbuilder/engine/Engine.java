package com.mzinck.smbuilder.engine;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.account.platform.Instagram;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieve;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieveHandler;
import com.mzinck.smbuilder.contentretrieval.impl.reddit.Reddit;
import com.mzinck.smbuilder.net.Database;
import org.apache.commons.io.FileUtils;
import org.brunocvcunha.instagram4j.requests.InstagramGetRecentActivityRequest;
import org.brunocvcunha.inutils4j.MyImageUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static void main(String[] args) {
        config = new Config();
        config.setConfig();
        connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());

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
            } else if(command.equalsIgnoreCase("post")) {
                ArrayList<Content> content = connection.grabTodaysContent();
                Content c = content.get(0);
                try {
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + c.getPostTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).post(content.get(0), testAccount.getTag());
            } else if(command.contains("closedb")) {
                connection.closeConnection();
            } else if(command.contains("poststory")) {
                ContentRetrieveHandler retrieve = new Reddit(config.getRedditUsername(), config.getRedditPassword(),
                        config.getRedditClientId(), config.getRedditClientSecret());
                retrieve.setTag(testAccount.getTag());
                Content story = ((Reddit) retrieve).getStory();
                if(story == null) {
                    return;
                }
                try {
                    FileUtils.copyURLToFile(new URL(story.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + story.getPostTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).postStory(story);
            } else if(command.equalsIgnoreCase("postvideo")) {
                ArrayList<Content> content = connection.grabTodaysContent();
                Content c = content.get(0);
                try {
                    FileUtils.copyURLToFile(new URL(c.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + c.getPostTitle()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((Instagram) testAccount).postVideo(content.get(0), testAccount.getTag());
            }
        }
    }

    /**
     * Sets a daily routine of retrieving and storing content for future posting.
     */
    public static void startContentRetrieve() {
        contentRetrieve = new ContentRetrieve(connection, config);
        contentRetrieve.initiate();
        contentRetrieve.retrieveAndStoreContent();
    }

    public static void postContent() {
        accounts = connection.grabAllAccounts();
        for(Account account : accounts) {
            if(account instanceof Instagram) {
                ((Instagram)account).login();
            }
        }
    }

    public static void loginTestAccInstagram() {
        ((Instagram)testAccount).login();
    }

}
