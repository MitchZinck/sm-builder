package com.mzinck.smbuilder.accounts;

import java.awt.*;

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

    @Override
    public void login() {

    }

    @Override
    public void changeProfilePicture(Image image, Tags tags) {

    }

    @Override
    public void post(Image image, String description, Tags tags) {

    }

    @Override
    public void changeBio(String bio, Tags tags) {

    }

    @Override
    public void printDetails() {
        System.out.println("Name: " + name);
        System.out.println("Bio: " + bio);
    }

}
