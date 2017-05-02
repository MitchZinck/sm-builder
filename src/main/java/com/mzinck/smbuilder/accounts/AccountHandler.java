package com.mzinck.smbuilder.accounts;

import java.awt.*;

/**
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public interface AccountHandler {

    public void login();
    public void changeProfilePicture(Image image, Tags tags); //image and tags
    public void post(Image image, String description, Tags tags);//image, description, tags
    public void changeBio(String bio, Tags tags);//bio, tags
    public void printDetails();

}
