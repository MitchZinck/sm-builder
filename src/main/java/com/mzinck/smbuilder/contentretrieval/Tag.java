package com.mzinck.smbuilder.contentretrieval;

import java.util.ArrayList;

/**
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Tag {

    /**
     * Overall reach of tag defined by a name.
     */
    private String tag;

    /**
     * The tags that are needed to retrieve a specific type of content.
     */
    private String[] tags;

    /**
     * Constructor
     * @param tag The tag that is needed to retrieve a specific type of content.
     */
    public Tag(String... tags) {
        this.tags = tags;
    }

    /**
     * @return The tag that is needed to retrieve a specific type of content.
     */
    public String getTag() {
        return tag;
    }

    public String[] getTags() {
        return tags;
    }

}
