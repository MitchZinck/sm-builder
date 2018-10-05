package com.mzinck.smbuilder.net;

import com.mzinck.smbuilder.account.Account;
import com.mzinck.smbuilder.account.platform.Instagram;
import com.mzinck.smbuilder.contentretrieval.Content;
import com.mzinck.smbuilder.contentretrieval.Tag;

import java.sql.*;
import java.util.ArrayList;

/**
 * Handles all database services.
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Database {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String url, user, password;

    /**
     * Constructor that sets database config info.
     * @param url the connection url.
     * @param user the login username.
     * @param password the login password.
     */
    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }// SET SQL_SAFE_UPDATES = 0;

    /**
     * Connect to the database.
     */
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return a list of active social media accounts.
     */
    public Long[] grabAllAccountIds() {
        ArrayList<Long> ids = new ArrayList<Long>();
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement("SELECT `id` FROM smbuilder.accounts");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ids.add(resultSet.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return ids.toArray(new Long[0]);
    }

    /**
     * Grabs all active accounts from the database.
     * @return an arraylist of accounts.
     */
    public ArrayList<Account> grabAllAccounts() {
        ArrayList<Account> accounts = new ArrayList<Account>();
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement("SELECT * FROM smbuilder.accounts");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String platform = resultSet.getString("platform");
                if(platform.equalsIgnoreCase("instagram")) {
                    accounts.add(new Instagram(resultSet.getLong("id"), resultSet.getString("username"),
                            resultSet.getString("displayname"), resultSet.getString("password"),
                            resultSet.getString("bio"), resultSet.getString("profilepic"),
                            resultSet.getString("email"), null, platform));
                }
            }
            resultSet.close();
            for(Account a : accounts) {
                a.setTag(getTag(a.getId()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return accounts;
    }

    /**
     * Grabs all active accounts from the database.
     * @return an arraylist of accounts.
     */
    public ArrayList<Content> grabTodaysContent() {
        ArrayList<Content> content = new ArrayList<Content>();
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement("SELECT * FROM smbuilder.content WHERE `posted` = ?");
            statement.setBoolean(1, false);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                content.add(new Content(resultSet.getLong("id"), resultSet.getString("post_title"),
                        resultSet.getString("content_url"), resultSet.getString("content_subreddit"),
                        resultSet.getLong("points")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return content;
    }

    /**
     * Grabs a test account from the database.
     * @return a test account.
     */
    public Account grabTestAccount() {
        Account account = null;
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement("SELECT * FROM smbuilder.accounts WHERE id = ?");
            statement.setInt(1, 1);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                System.out.println(resultSet.toString());
                account = new Instagram(resultSet.getLong("id"), resultSet.getString("username"),
                        resultSet.getString("displayname"), resultSet.getString("password"),
                        resultSet.getString("bio"), resultSet.getString("profilepic"),
                        resultSet.getString("email"), null, resultSet.getString("platform"));
            }
            resultSet.close();

            account.setTag(getTag(account.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return account;
    }

    /**
     * Grabs tags for specific account.
     * @return a @Tag.
     */
    public Tag getTag(long id) {
        ArrayList<String> tags = null;
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement("SELECT * FROM smbuilder.tags WHERE `tagid` = ?");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            tags = new ArrayList<String>();
            while (resultSet.next()) {
                tags.add(resultSet.getString("tagname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return new Tag(tags);
    }

    /**
     * Stores content into the database for future retrieval.
     * @param content the content to be stored.
     */
    public void storeContent(Content content) {
        try {
            if (connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }

            statement = connection.prepareStatement(
                    "INSERT INTO smbuilder.CONTENT(post_title, content_url, content_subreddit, points, posted) VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, content.getPostTitle());
            statement.setString(2, content.getUrl());
            statement.setString(3, content.getSubreddit());
            statement.setLong(4, content.getPoints());
            statement.setBoolean(5, false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
