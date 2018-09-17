package com.mzinck.smbuilder.contentretrieval;

import com.mzinck.smbuilder.config.Config;
import com.mzinck.smbuilder.net.Database;

/**
 * Retrieves content related to specific tags and stores the content for later posting.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class ContentRetrieve {

    public static void main(String[] args) {
        Config config = new Config();
        config.setConfig();
        /*ContentRetrieveHandler retrieve = new Reddit();
        ((Reddit) retrieve).setUsername(config.getRedditUsername());
        ((Reddit) retrieve).setPassword(config.getRedditPassword());
        ((Reddit) retrieve).setClientId(config.getRedditClientId());
        ((Reddit) retrieve).setClientSecret(config.getRedditClientSecret());
        Tag tag = new Tag("WallStreetBets", "Aww", "Funny");
        retrieve.setTag(tag);
        retrieve.getContent();*/
        Database connection = new Database();
        connection.setUrl(config.getSqlURL());
        connection.setUser(config.getSqlUser());
        connection.setPassword(config.getSqlPassword());
        connection.connect();
        Long[] ids = connection.grabAllAccountIds();

    }

}
