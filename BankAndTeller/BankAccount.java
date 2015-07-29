/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vivek
 */
package bank.project;    
public class BankAccount
{ 
/*A class attribute which is used by the class to allocate a unique account number to each object that it creates. Account numbers will begin at 1  */	
static int NEXTNUMBER = 1;
 

/*The account number */	
	private int accountNumber;

/* The balance of the account */
	private int balance;	

/* The loan amount is */
        private int loan;
        
/* an association with the BankCustomer object who owns the account. Enables a BankAccount object to send messages to the customer it belongs to */ 
	private BankCustomer customer;
	 
 
 
/* The constructor method */
/*A BankAccount cannot exist unless it is owned by a BankCustomer */

public BankAccount(BankCustomer customer)
{   /*The class allocates NEXTNUMBER as this object’s accountNumber. The class then increments NEXTNUMBER by 1, ensuring that the next object it creates gets a different account number */
this.accountNumber = customer.getAccNum();
this.customer = customer;
 balance = 1000;
 loan = 0;
}		
	
 
/* get methods */
	public int getAccountNumber()
	{ 	return accountNumber;				}
	
	public int getBalance() 
	{	return balance;				}
	
 public int getLoan() 
	{	return loan;				}
	

	public String getCustomerName()
	{	return customer.getName();				}

        public BankCustomer getCustomer()
	{	return customer;				}
	
/* The toString() method */
	public String toString()
	{ 
        return "Account number " + accountNumber + " belonging to " + customer.getName()
                + " has a balance of " + balance  + " and loan of " + loan;
	}
	
 
/*Credits the account with an amount of money and returns a string confirming the *credit */
public String credit(double anAmount) 
{
    
balance +=anAmount;
return " Account number " + accountNumber + " has been credited with "+ anAmount;
}	

 

/*returns true if existing balance is greater than or equal to the amount to be  *debited, false otherwise */	
public boolean canDebit(double anAmount)
{
return anAmount<=balance;
}

public String loancredit(double anAmount) 
{
    
loan +=anAmount;
return " Loan Account number " + accountNumber + " has been credited with "+ anAmount;
}	
 
 
/** Returns a string confirming the debit of an amount of money from the account. 
* If the account can be debited by the amount then the returned string should  confirm the debit otherwise it should state that this account has insufficient funds. */
 
public String debit(double anAmount) 
{ 
if(this.canDebit(anAmount))
   {
    balance -= anAmount;	
     return +  anAmount + 
" has been debited from Acount number " + 	accountNumber;
	}
else
	{
     	return "Account number " + 	accountNumber  + 
	" has insufficient funds to debit " 	+anAmount; 
	}
	
}
	
} // end BankAccount class definition

