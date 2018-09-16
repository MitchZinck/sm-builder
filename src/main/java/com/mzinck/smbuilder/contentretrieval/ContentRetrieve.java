package com.mzinck.smbuilder.contentretrieval;

import com.mzinck.smbuilder.contentretrieval.impl.reddit.Reddit;

/**
 * Retrieves content related to specific tags and stores the content for later posting.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class ContentRetrieve {

    public static void main(String[] args) {
        ContentRetrieveHandler retrieve = new Reddit();
        Tag tag = new Tag("WallStreetBets", "Aww", "Funny");
        retrieve.setTag(tag);
        retrieve.setConfig();
        retrieve.getContent();
    }

}
