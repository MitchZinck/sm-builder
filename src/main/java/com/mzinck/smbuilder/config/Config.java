package com.mzinck.smbuilder.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Handles the configuration data for social media accounts and databases.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Config {

    private String redditUsername = "";
    private String redditPassword = "";
    private String redditClientId = "";
    private String redditClientSecret = "";
    private String sqlURL = "";
    private String sqlUser = "";
    private String sqlPassword = "";

    /**
     * Sets the configuration parameters such as login info.
     */
    public void setConfig() {
        File file = new File("C:\\Users\\Mitchell\\Desktop\\config.txt");
        try {
            Scanner scan = new Scanner(file);
            redditUsername = scan.nextLine();
            redditPassword = scan.nextLine();
            redditClientId = scan.nextLine();
            redditClientSecret = scan.nextLine();
            sqlURL = scan.nextLine();
            sqlUser = scan.nextLine();
            sqlPassword = scan.nextLine();
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRedditUsername() {
        return redditUsername;
    }

    public String getRedditPassword() {
        return redditPassword;
    }

    public String getRedditClientId() {
        return redditClientId;
    }

    public String getRedditClientSecret() {
        return redditClientSecret;
    }

    public String getSqlURL() {
        return sqlURL;
    }

    public String getSqlUser() {
        return sqlUser;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }
}
