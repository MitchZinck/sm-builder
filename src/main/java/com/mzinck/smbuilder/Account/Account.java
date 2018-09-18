package com.mzinck.smbuilder.Account;

/**
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Account {

    private long id;
    private String username;
    private String displayName;
    private String password;
    private String bio;
    private String profilePic;
    private String email;
    private String tag;

    /**
     * Constructor for setting account data.
     * @param id
     * @param username
     * @param displayName
     * @param password
     * @param bio
     * @param email
     * @param tag
     */
    public Account(long id, String username, String displayName, String password,
                    String bio, String profilePic, String email, String tag) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.bio = bio;
        this.profilePic = profilePic;
        this.email = email;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getEmail() {
        return email;
    }

    public String getTag() {
        return tag;
    }
}
