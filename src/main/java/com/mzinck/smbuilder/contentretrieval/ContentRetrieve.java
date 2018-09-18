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

    public static void main(String[] args) {
        Config config = new Config();
        config.setConfig();
        /*ContentRetrieveHandler retrieve = new Reddit(config.getRedditUsername(), config.getRedditPassword(),
                config.getRedditClientId(), config.getRedditClientSecret());
        Tag tag = new Tag("WallStreetBets", "Aww", "Funny");
        retrieve.setTag(tag);
        retrieve.getContent();*/
        Database connection = new Database(config.getSqlURL(), config.getSqlUser(), config.getSqlPassword());
        connection.connect();
        ArrayList<Account> accounts = connection.grabAllAccounts();
        for(Account account : accounts) {
            Tag tag = connection.getTags(account.getId());
        }
    }

}
