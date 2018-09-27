package com.mzinck.smbuilder.engine;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.ContentRetrieve;
import com.mzinck.smbuilder.net.Database;

import java.util.ArrayList;

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

    public static void main(String[] args) {
        config = new Config();
        config.setConfig();
        connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());

        postContent(); //set as loop
        //setupContentRetrieve();
    }

    /**
     * Sets a daily routine of retrieving and storing content for future posting.
     */
    public static void setupContentRetrieve() {
        contentRetrieve = new ContentRetrieve(connection, config);
        contentRetrieve.initiate();
        contentRetrieve.retrieveAndStoreContent();
    }

    public static void postContent() {
        accounts = connection.grabAllAccounts();
        for(Account account : accounts) {

        }
    }

}
