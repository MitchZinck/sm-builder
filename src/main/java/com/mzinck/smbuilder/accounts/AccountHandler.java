package com.mzinck.smbuilder.accounts;

import com.mzinck.smbuilder.posts.Post;

import java.awt.image.BufferedImage;

/**
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public interface AccountHandler {

    /**
     * Gets the tag associated with the account.
     * @return the tag associated with the account.
     */
    public String getTag();

    /**
     * Logs the account into its associated social media platform.
     */
    public void login();

    /**
     * Posts to the accounts associated media platform.
     * @param post the Post object contains the details of what is to be posted.
     */
    public void post(Post post);//post

    /**
     * Changes the bio of specific accounts.
     * @param bio the String containing the new bio.
     * @param names the names of the accounts you want the bio changed.
     */
    public void changeBio(String bio, String[] names);//bio, names

    /**
     * Changes the bios of all social media accounts.
     * @param bio the String containing the new bio.
     */
    public void changeBios(String bio);//bio for all accounts

    /**
     * Changes the password of specific accounts.
     * @param password the String containing the new password.
     * @param names the names of the accounts you want the password changed.
     */
    public void changePassword(String password, String[] names); //password, names

    /**
     * Changes the passwords of all social media accounts.
     * @param password the String containing the new password.
     */
    public void changePasswords(String password); //password for all accounts

    /**
     * Changes the profile picture of specific accounts.
     * @param image the BufferedImage containing the new profile picture.
     * @param names the names of the accounts you want the profile picture changed.
     */
    public void changeProfilePicture(BufferedImage image, String[] names); //image and account names

    /**
     * Changes the profile picture of all social media accounts.
     * @param image the BufferedImage containing the new profile picture.
     */
    public void changeProfilePictures(BufferedImage image); //image for all accounts

    /**
     * The account into a string.
     */
    public String toString();

}
