/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vivek
 */
package bank.project;

public class Transaction {
	private int type;
	private int amount;
        private BankCustomer bc;
        
        public Transaction()
        {
            this.type = -1;
            this.amount = -1;
            this.bc = new BankCustomer(" ",-1 );
        }

        public Transaction(int type, int amount, BankCustomer bc) {
            this.type = type;
            this.amount = amount;
            this.bc = bc;
        }
        
           
        public int getType() {
            return type; 
        }
          
         public int getAmount() {
            return amount; 
        }
         
         public BankCustomer getBankCustomer() {
             return bc; 
         }
        
	public String display() {
           return ("The transaction is : " + type + " and amount is " + amount + " and customer is " + 
                   bc.getName());
          
	}
}
