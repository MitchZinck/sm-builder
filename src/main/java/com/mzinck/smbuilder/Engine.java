package com.mzinck.smbuilder;

import com.mzinck.smbuilder.accounts.Account;
import com.mzinck.smbuilder.accounts.AccountHandler;
import com.mzinck.smbuilder.net.io.AccountLoader;

import java.util.ArrayList;

/**
 * @author Mitchell Zinck Copyright (2017)
 * @version 1.0
 * @see <a href="github.com/mitchzinck">Github</a>
 */

public class Engine {

    public static void main(String[] args) {
        AccountLoader al = new AccountLoader();
        ArrayList<Account> accounts = al.getAccounts();
        for(AccountHandler ah : accounts) {
            ah.printDetails();
        }
    }

}
