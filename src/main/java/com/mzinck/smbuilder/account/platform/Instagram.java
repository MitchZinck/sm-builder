package com.mzinck.smbuilder.account.platform;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.Tag;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.storymetadata.StoryHashtag;
import org.brunocvcunha.instagram4j.storymetadata.StoryMetadata;
import org.brunocvcunha.inutils4j.MyImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
                        String bio, String profilePic, String email, Tag tag, String platform) {
        super(id, username, displayName, password, bio, profilePic, email, tag, platform);
    }

    public void postPicture(Content content, Tag tag) {
        String tagString = "";
        for(String t : tag.getTags()) {
            tagString += "#" + t + " ";
        }

        for(String t : tag.getCaptionTags()) {
            tagString += "#" + t + " ";
        }

        try {
            instagram.sendRequest(new InstagramUploadPhotoRequest(
                    new File("C:\\Users\\Mitchell\\Desktop\\memes\\" + content.getPostTitle()),
                    content.getPostTitle() +  "\n" + tagString));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to post image due to aspect problems.");
        }
    }

    public void postStory(Content content) {
//        Collection<StoryMetadata> meta = new ArrayList<StoryMetadata>();
//        StoryHashtag hashtag = StoryHashtag.builder()
//                .tag_name(content.getSubreddit()) //tag without '#'
//                .build(); //asign x y z rotation for position of tag
//        ((ArrayList<StoryMetadata>) meta).add(hashtag);
        try {
            instagram.sendRequest(new InstagramUploadStoryPhotoRequest(
                    new File("C:\\Users\\Mitchell\\Desktop\\memes\\stories\\" + content.getPostTitle())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postVideo(Content content, Tag tag) {
        String tagString = "";
        for(String t : tag.getTags()) {
            tagString += "#" + t + " ";
        }

        for(String t : tag.getCaptionTags()) {
            tagString += "#" + t + " ";
        }
        File file = new File("C:\\Users\\Mitchell\\Desktop\\memes\\videos\\" + content.getPostTitle());

        try {
            instagram.sendRequest(new InstagramUploadVideoRequest(file,
                    content.getPostTitle() +  "\n" + tagString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        instagram.getClient().close();
    }

}
