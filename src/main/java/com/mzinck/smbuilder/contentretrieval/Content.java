package com.mzinck.smbuilder.contentretrieval;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
    private boolean posted;
    private long points; //upvotes/retweets/retags
    private String postTitleAsMD5;

    /**
     * Sets content information and then hashes the title for storage.
     * The reason for hashing the title is because when storing content locally you
     * may have an emoji or characters in the title that breaks saving the content.
     */
    public Content(long id, String postTitle, String url, String subreddit, boolean posted, long points){
        this.id = id;
        this.postTitle = postTitle;
        this.url = url;
        this.subreddit = subreddit;
        this.posted = posted;
        this.points = points;

        byte[] bytesOfMessage = new byte[0];
        byte[] md5 = null;
        try {
            bytesOfMessage = postTitle.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md5 = md.digest(bytesOfMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        postTitleAsMD5 = Base64.getEncoder().encodeToString(md5);
        postTitleAsMD5 = postTitleAsMD5.replaceAll("\\\\", "");
        postTitleAsMD5 = postTitleAsMD5.replaceAll("/", "");
    };

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostTitleAsMD5() {
        return postTitleAsMD5;
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

    public boolean getPosted() {
        return posted;
    }

    public long getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
