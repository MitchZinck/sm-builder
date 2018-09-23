package com.mzinck.smbuilder.contentretrieval;

import com.mzinck.smbuilder.Account.Account;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.impl.reddit.Reddit;
import com.mzinck.smbuilder.net.Database;

import java.util.ArrayList;

/**
 * Retrieves content related to specific tags and stores the content for later posting.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class ContentRetrieve {

    public static ArrayList<Tag> tags;
    public static Config config;
    public static Database connection;

    public static void main(String[] args) {
        tags = new ArrayList<Tag>();
        config = new Config();
        config.setConfig();
        connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());
        connection.connect();
        setTags();

        ContentRetrieveHandler retrieve = new Reddit(config.getRedditUsername(), config.getRedditPassword(),
                config.getRedditClientId(), config.getRedditClientSecret());

        for(Tag tag : tags) {
            retrieve.setTag(tag);
            ArrayList<Content> content = retrieve.getContent();
            for(Content c : content) {
                connection.storeContent(c);
                System.out.println(c.getPostTitle() + " | " + c.getSubreddit() + " | " + c.getUrl());
            }
        }
    }

    public static void setTags() {
        ArrayList<Account> accounts = connection.grabAllAccounts();
        for(Account account : accounts) {
            tags.add(connection.getTags(account.getId()));
        }
    }

}
