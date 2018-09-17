package com.mzinck.smbuilder.net;

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
     * Empty constructor.
     */
    public Database() {}// SET SQL_SAFE_UPDATES = 0;

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

        return ids.toArray(new Long[0]);
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
