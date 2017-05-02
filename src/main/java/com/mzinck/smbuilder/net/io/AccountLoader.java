package com.mzinck.smbuilder.net.io;

import com.mzinck.smbuilder.accounts.Account;

import java.util.ArrayList;

/**
 * This class is used to store all active social media accounts.
 *
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class AccountLoader {

    private final String FOLDER = "C:\\Users\\Mitchell\\Desktop\\accounts"; //folder to read accounts from

    private ArrayList<Account> accounts; //holds all the current accounts in use

    /**
     * Constructor that loads and then holds the accounts in an ArrayList.
     */
    public AccountLoader() {
        JsonReader jr = new JsonReader(FOLDER);
        accounts = jr.loadAccounts();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
