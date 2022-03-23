package entity;

import java.util.HashMap;

public class Bank {
    private HashMap<Integer, Account> accounts;
    private int count;

    public Bank(){
        accounts = new HashMap<>();
        count = 0;
    }

    public int openAccount(String name, int password, String currency, double balance){
        if (checkDuplicates(count) == true) return -1;
        Account newAcc = new Account(name, count, password, currency, balance);
        accounts.put(newAcc.getAccNum(), newAcc);
        count++;
        System.out.println("------ New account successfully opened.");
        newAcc.printAccountInfo();
        return newAcc.getAccNum();
    }

    private boolean checkDuplicates(int count){
        return accounts.containsKey(count);
    }
}
