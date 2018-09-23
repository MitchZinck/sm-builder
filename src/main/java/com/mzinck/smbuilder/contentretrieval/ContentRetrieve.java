package com.mzinck.smbuilder.contentretrieval;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.impl.reddit.Reddit;
import com.mzinck.smbuilder.net.Database;

import java.util.ArrayList;

/**
 * Retrieves content related to specific tags and stores the content for later posting.
 * @author Mitchell Zinck Copyright (2018)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class ContentRetrieve {

    public ArrayList<Tag> tags;
    public Database connection;
    public Config config;

    public ContentRetrieve(Database connection, Config config) {
        this.connection = connection;
        this.config = config;
    }

    /**
     * Initializes a database connection. Retrieves content and then stores.
     */
    public void initiate() {
        tags = new ArrayList<Tag>();
        connection.connect();
    }

    public void retrieveAndStoreContent() {
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

    public void setTags() {
        ArrayList<Account> accounts = connection.grabAllAccounts();
        for(Account account : accounts) {
            tags.add(connection.getTags(account.getId()));
        }
    }

}
