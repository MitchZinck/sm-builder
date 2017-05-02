package com.mzinck.smbuilder.net.io;

import com.google.gson.Gson;
import com.mzinck.smbuilder.accounts.Account;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * This class is used to read all active social media accounts.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class JsonReader {

    private Account account; //the account currently read

    private String folder; //folder to read accounts from

    private Gson gson; //gson object to convert .json to an object

    /**
     * Default constructor.
     */
    public JsonReader() {}

    /**
     * Constructs a JsonReader object to read a folder of accounts
     * in .json format.
     * @param folder The folder to read accounts from.
     */
    public JsonReader(String folder) {
        this.folder = folder;
        gson = new Gson();
    }

    /**
     * Reads the next account from the specified folder.
     * @return A read Accoun
     */
    public ArrayList<Account> loadAccounts() {
        ArrayList<Account> list = new ArrayList<Account>();
        try(Stream<Path> paths = Files.walk(Paths.get(folder))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(filePath.toString()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    account = gson.fromJson(br, Account.class);
                    list.add(account);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();//shutdown
        }
        return list;
    }

}

