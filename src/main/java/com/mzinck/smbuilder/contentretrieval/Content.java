package com.mzinck.smbuilder.contentretrieval;

/**
 * Container for content to be stored or retrieved.
 * @author Mitchell Zinck Copyright (2018)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Content {

    private long id;
    private String postTitle;
    private String url;
    private String subreddit;
    private long points; //upvotes/retweets/retags

    /**
     * Empty Constructor.
     */
    public Content(long id, String postTitle, String url, String subreddit, long points){
        this.id = id;
        this.postTitle = postTitle;
        this.url = url;
        this.subreddit = subreddit;
        this.points = points;
    };

    public String getPostTitle() {
        return postTitle;
    }

    public String getUrl() {
        return url;
    }

    public long getPoints() {
        return points;
    }

    public String getSubreddit() {
        return subreddit;
    }

}
