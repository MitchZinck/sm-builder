package com.mzinck.smbuilder.account.platform;

import com.mzinck.smbuilder.account.Account;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;

import java.io.IOException;

/**
 * Functionality for the Instagram platform.
 * @author Mitchell Zinck Copyright (2018)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Instagram extends Account {

    public Instagram4j instagram;

    public void login() {
        instagram = Instagram4j.builder().username(super.getUsername()).password(super.getPassword()).build();
        instagram.setup();
        try {
            instagram.login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void follow(String username) {
        try {
            InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(username));
            instagram.sendRequest(new InstagramFollowRequest(userResult.getUser().getPk()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Instagram (long id, String username, String displayName, String password,
                        String bio, String profilePic, String email, String tag, String platform) {
        super(id, username, displayName, password, bio, profilePic, email, tag, platform);
    }

}
