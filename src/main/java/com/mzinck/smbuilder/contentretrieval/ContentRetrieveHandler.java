package com.mzinck.smbuilder.contentretrieval;

import java.util.ArrayList;

/**
 * Interface for retrieving content from multiple platforms.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public interface ContentRetrieveHandler  {

    /**
     * Retrieves top 10 pieces of content in the last 24 hours.
     */
    public ArrayList<Content> getContent();

    /**
     * Sets the tag associated with the content to retrieve.
     * @param tag the content to retrive
     */
    public void setTag(Tag tag);

    /**
     * @return the @Tag associated with the content.
     */
    public Tag getTag();

}
