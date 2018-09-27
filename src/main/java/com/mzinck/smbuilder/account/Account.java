package com.mzinck.smbuilder.account;

/**
 * Class that holds important account info.
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
    private String platform;

    /**
     * Constructor for setting account data.
     * @param id the account id
     * @param username the account username
     * @param displayName the account display name
     * @param password the account password
     * @param bio the account bio
     * @param email the account email
     * @param tag the tag (theme) associated with the accounts content.
     * @param platform the social media platform the account is associated with.
     */
    public Account(long id, String username, String displayName, String password,
                    String bio, String profilePic, String email, String tag, String platform) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.bio = bio;
        this.profilePic = profilePic;
        this.email = email;
        this.tag = tag;
    }

    /**
     * Empty Constructor.
     */
    public Account() {}

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

    public String getPlatform() { return platform; }
}
