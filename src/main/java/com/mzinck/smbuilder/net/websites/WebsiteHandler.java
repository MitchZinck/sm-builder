package com.mzinck.smbuilder.net.websites;

import com.mzinck.smbuilder.posts.Post;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * An interface to use with the websites.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public interface WebsiteHandler {

    public boolean scrapePosts(Map<URL, String> map);
    public ArrayList<Post> getPosts();

}
