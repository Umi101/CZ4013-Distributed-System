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

    public int closeAccount(String name, int password, int accNum){
        Account acc = this.accounts.get(accNum);
        if (acc == null) {return -1;} // Error No. -1: Account No. does not exist
//        System.out.println("---------------");
//        System.out.println(acc.getName());
//        System.out.println(name);
//        System.out.println(acc.getName() == name);
        if (!acc.getName().equals(name)) {return -2;} // Error No. -2: Wrong name
        if (acc.getPassword()!= password) {return -3;} // Error No. -3: Wrong password
        this.accounts.remove(accNum);
        System.out.printf("Account %d successfully closed.%n", accNum);
        return 0; // successful
    }
    
    public double checkAccountBalance(String name, int password, int accNum) {
    	Account acc = this.accounts.get(accNum);
    	if (acc == null) {return -1;}
    	return acc.getBalance();
    }
    
    public String checkAccountCurrency(String name, int password, int accNum) {
    	Account acc = this.accounts.get(accNum);
    	return acc.getCurrency();
    }

    public double updateAccount(String name, int password, int accNum, double amount){
        Account acc = this.accounts.get(accNum);
        if (acc == null) {return -1;} // Error No. -1: Account No. does not exist
        double tempBal = acc.getBalance();
        tempBal = tempBal + amount;
        if (tempBal < 0) {return -2;} // Error No. -2: Insufficient cash
        acc.setBalance(tempBal);
        return acc.getBalance();
    }

    private boolean checkDuplicates(int count){
        return accounts.containsKey(count);
    }
}
