/*
 *
 *
 * @vivek
 */

package bank.project;

import java.util.*;

public class BankAccountBase{

/*The attribute accounts  which is an Array of Bank Accounts */
private BankAccount accounts[]; 
 
public BankAccountBase()
{
accounts = new BankAccount[5];
}
/** The constructor method. The new ArrayList called accounts is created. The BankAccount objects will be stored in this ArrayList. When it is created it will be empty. The size of the ArrayList does not need to be specified as an ArrayList is dynamic*/ 

 
/*A method to add a Bank Account of an existing customer */ 
public void addCustomerAccount(BankCustomer customer)
{
accounts[customer.getAccNum()] = new BankAccount(customer); 
}
 
public String getCustomerName(BankCustomer customer)
{
    return accounts[customer.getAccNum()].getCustomerName();
}

public BankAccount getBankAccount(int custnum)
{
    return accounts[custnum];
}

public BankCustomer getBankCustomer(int custnum)
{
    return accounts[custnum].getCustomer();
}
/** A method to credit an existing account in the collection of accounts. If the BankAccount with the specified account number is not found, an error message is returned. If it is found then it will be credited with the specified amount and a message is returned to indicate a successful credit */

    /**
     * A method to credit an existing account in the collection of accounts.If the BankAccount with the specified account number is not found, an error message is returned. If it is found then it will be credited with the specified amount and a message is returned to indicate a successful credit
     * @param customer
     */
    public int getBankbalance(BankCustomer customer)
{
    return accounts[customer.getAccNum()].getBalance();
}

     public int getBankbalance(int custnum)
{
    return accounts[custnum].getBalance();
}
    public int getBankloan(int custnum)
{
    return accounts[custnum].getLoan();
}
public void creditAccount(int accountNumber, int anAmount, BankCustomer cust){

 //   System.out.println(" Customer " + cust.getName() + " Balance before crediting  " + anAmount 
 //       + " is Balance: " + accounts[cust.getAccNum()].getBalance());  
    
accounts[cust.getAccNum()].credit(anAmount); 

//System.out.println(" Customer " + cust.getName() + " credited with " + anAmount 
//        + " and balance is " + accounts[cust.getAccNum()].getBalance()); 
}
 
/** A method to debit an existing account in the collection of accounts. If the BankAccount with the specified account number is not found, an error message is returned. If it is found then if the specified amount is <= balance, it will be debited with the specified amount and a message  returned to indicate a successful credit, otherwise an error message indicating insufficient funds is returned */  
public void debitAccount(int accountNumber, int anAmount, BankCustomer cust) {

//System.out.println(" Customer " + cust.getName() + " Balance before debiting  " + anAmount 
//        + " is Balance: " + accounts[cust.getAccNum()].getBalance());     
accounts[cust.getAccNum()].debit(anAmount); 

//System.out.println(" Customer " + cust.getName() + " debited with " + anAmount 
//        + " and balance is " + accounts[cust.getAccNum()].getBalance());     
} 

/** A method to debit an existing account in the collection of accounts. If the BankAccount with the specified account number is not found, an error message is returned. If it is found then if the specified amount is <= balance, it will be debited with the specified amount and a message  returned to indicate a successful credit, otherwise an error message indicating insufficient funds is returned */  
public void creditLoanAccount(int accountNumber, int anAmount, BankCustomer cust) {

//System.out.println(" Customer " + cust.getName() + " Loan Balance before adding  " + anAmount 
//        + " is Balance: " + accounts[cust.getAccNum()].getBalance());     
accounts[cust.getAccNum()].loancredit(anAmount); 

//System.out.println(" Customer " + cust.getName() + " debited with " + anAmount 
//        + " and balance is " + accounts[cust.getAccNum()].getBalance());     
}     
} // end BankAccountBase class

