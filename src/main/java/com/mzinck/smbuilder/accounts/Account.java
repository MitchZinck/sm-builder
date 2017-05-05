package com.mzinck.smbuilder.accounts;

import com.mzinck.smbuilder.posts.Post;

import java.awt.image.BufferedImage;

/**
 * An object representing an active social media account.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Account implements AccountHandler {

    private String name = ""; //name of the account - placeholder
    private String bio = ""; //bio - placeholder
    private String password = "";
    private String tag = "";

    /**
     * Empty Constructor.
     */
    public Account() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void post(Post post) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeBio(String bio, String[] names) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeBios(String bio) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePassword(String password, String[] names) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePasswords(String password) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeProfilePicture(BufferedImage image, String[] names) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeProfilePictures(BufferedImage image) {

    }
}
