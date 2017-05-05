package com.mzinck.smbuilder.posts;

import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * This class is used to represent a post to be made to a social media account.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Post {

    private URL url; //url the post was gotten from
    private String tag; //tag the post is associated with
    private String description; //description the post will have
    private BufferedImage image; //image for the post

    /**
     * Constructor to initialize a post.
     */
    public Post(URL url, String tag, BufferedImage image) {
        this.url = url;
        this.tag = tag;
        this.image = image;
    }

    /**
     * Returns the url associated with the post
     * @return the url associated with the post
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Sets the url associated with the post.
     * @param url the url to be associated with the post.
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Returns the tag associated with the post.
     * @return the tag associated with the post.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the tag associated with the post.
     * @param tag the tag to be associated with the post.
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the image associated with the post.
     * @return the image associated with the post.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Sets the image associated with the post.
     * @param image the image to be associated with the post.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Sets the description associated with the post.
     * @param description the description to be associated with the post.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description associated with the post.
     * @return the description associated with the post.
     */
    public String getDescription() {
        return description;
    }

}
