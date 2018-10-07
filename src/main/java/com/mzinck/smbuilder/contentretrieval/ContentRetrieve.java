package com.mzinck.smbuilder.contentretrieval;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.contentretrieval.impl.reddit.Reddit;
import com.mzinck.smbuilder.net.Database;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Retrieves content related to specific tags and stores the content for later posting.
 * @author Mitchell Zinck Copyright (2018)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>56
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

    /**
     * Retrieves content from Reddit and stores in the database.
     */
    public void retrieveAndStoreContent() {
        setTags();
        ContentRetrieveHandler retrieve = new Reddit(config.getRedditUsername(), config.getRedditPassword(),
                config.getRedditClientId(), config.getRedditClientSecret());

        for(Tag tag : tags) {
            retrieve.setTag(tag);
            ArrayList<Content> content = retrieve.getContent();
            for(Content c : content) {
                connection.storeContent(c);
                //System.out.println(c.getPostTitle() + " | " + c.getSubreddit() + " | " + c.getUrl());
            }
        }
    }

    public Content retrieveStoryContent(Account account) {
        ContentRetrieveHandler retrieve = new Reddit(config.getRedditUsername(), config.getRedditPassword(),
                config.getRedditClientId(), config.getRedditClientSecret());
        retrieve.setTag(account.getTag());
        Content story = ((Reddit) retrieve).getStory();
        if(story == null) {
            return null;
        }
        try {
            FileUtils.copyURLToFile(new URL(story.getUrl()), new File("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + story.getPostTitle()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return story;
    }

    /**
     * Sets the tags (content type) that needs to be retrieved.
     */
    public void setTags() {
        Long[] ids = connection.grabAllAccountIds();
        for(long id : ids) {
            tags.add(connection.getTag(id));
        }
    }

}
