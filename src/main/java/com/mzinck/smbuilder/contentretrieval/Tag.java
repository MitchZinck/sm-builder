package com.mzinck.smbuilder.contentretrieval;

import java.util.ArrayList;

/**
 * Represents a tag or set of tags related to content retrieval.
 * @author Mitchell Zinck Copyright (2018)
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
    private ArrayList<String> tags;

    /**
     * The tags that are to be posted with the caption.
     */
    private ArrayList<String> captionTags;

    /**
     * Constructor
     * @param tags The tag that is needed to retrieve a specific type of content.
     */
    public Tag(ArrayList<String> tags, ArrayList<String> captionTags) {
        this.tags = tags;
        this.captionTags = captionTags;
    }

    /**
     * @return The tag that is needed to retrieve a specific type of content.
     */
    public String getTag() {
        return tag;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<String> getCaptionTags() { return captionTags; }
}
