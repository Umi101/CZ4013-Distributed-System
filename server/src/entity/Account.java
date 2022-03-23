package entity;

public class Account {
	private String name;
	private int accNum;
	private int password;
	private String currency;
	private double balance;
	
	public Account(String name, int accNum, int password, String currency, double balance) {
		super();
		this.name = name;
		this.accNum = accNum;
		this.password = password;
		this.currency = currency;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void printAccountInfo(){
		System.out.println("------ Printing Account Info:");
		System.out.printf("------ Account name: %s%n",this.name);
		System.out.printf("------ Account No.: %s%n",this.accNum);
		System.out.printf("------ Account password: %s%n",this.password);
		System.out.printf("------ Account currency: %s%n",this.currency);
		System.out.printf("------ Account balance: %f%n",this.balance);
	}
}
