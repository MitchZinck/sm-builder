package com.mzinck.smbuilder.net.websites.impl;

import com.mzinck.smbuilder.net.websites.WebsiteHandler;
import com.mzinck.smbuilder.posts.Post;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class is used to scrape Reddit and return quality posts.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Reddit implements WebsiteHandler {

    private ArrayList<Post> posts;

    @Override
    public boolean scrapePosts(Map<URL, String> map) {

        return false;
    }

    @Override
    public ArrayList<Post> getPosts() {
        return posts;
    }

}
