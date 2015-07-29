package bank.project;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;
public class Project2 {

	public Project2() {
		super();
	}
        
        
	public static void main(String[] args) {
                
            
		int buffer_size = 20; // size of buffer
                Semaphore teller_counter = new Semaphore(2,true);
                Semaphore[] cust_finish = new Semaphore [5];  
                Semaphore[] loan_finish = new Semaphore [5];
                Semaphore[] cust_exit = new Semaphore [5];
                Semaphore[] teller_exit = new Semaphore [5];
                Semaphore[] loan_exit = new Semaphore [5];
                Semaphore[] tel_exit = new Semaphore [2];
                Semaphore lo_exit = new Semaphore(1,true);
                Semaphore cust_with_teller = new Semaphore(2,true);
                Semaphore loan_counter = new Semaphore(1,true);
                Semaphore cust_with_loanoff = new Semaphore(1,true);
                Semaphore s_mutex1 = new Semaphore(1, true); 
                Semaphore s_mutex2 = new Semaphore(1, true); 
		// create a semaphore mutex for the queue
		Semaphore s_mutex = new Semaphore(1, true);  
                BankAccountBase bankacctn = new BankAccountBase(); 
                Queue<Transaction> queue = new LinkedList<>();
                Queue<Transaction> queue2 = new LinkedList<>();
                boolean[] cust_done = new boolean[5];                 
                Customer cust;
                for (int i = 0; i < 5; i++) { 
                 cust_finish[i] = new Semaphore(1,true);
                 loan_finish[i] = new Semaphore(1,true);
                 cust_exit[i] = new Semaphore(1,true);  
                 teller_exit[i] = new Semaphore(1,true);
                 loan_exit[i] = new Semaphore(1,true);
                }
                
                for (int i = 0; i < 2; i++) { 
                  tel_exit[i] = new Semaphore(1,true);  
                }
                  
                for (int i = 0; i < 5; i++) { //loop to create multiple Customers
                        
                        BankCustomer bankcust = new BankCustomer(Integer.toString(i), i - 0);
                        bankacctn.addCustomerAccount(bankcust);
                        try {      
			cust_exit[i].acquire();
                        teller_exit[i].acquire();
                        loan_exit[i].acquire();
                        }
                        catch (Exception e) {
				System.out.println( "Exception in semaphore:"+ " exception type:\n" + e);
			}
                        cust = new Customer(s_mutex,  
                        teller_counter,cust_finish,loan_finish,cust_exit,teller_exit,loan_exit,
                        cust_with_teller,loan_counter,
                        cust_with_loanoff,s_mutex1,s_mutex2, i,bankacctn, queue, queue2, cust_done);
                        cust.setName(Integer.toString(i));
                        cust.start();
		}

  		Teller tell;  
                for (int i = 0;i < 2;i++) { //loop to create multiple Tellers
                       try {      
                        tel_exit[i].acquire(); }
                        catch (Exception e) {
				System.out.println( "Exception in semaphore:"+ " exception type:\n" + e);
			}
                        tell = new Teller(s_mutex,teller_exit,tel_exit,
                        cust_with_teller,cust_finish,teller_counter, i, bankacctn, queue, cust_done);
                        tell.setName(Integer.toString(i));
			tell.start();
		}
                
                LoanOff loanOff;  
                for (int i = 0;i < 1;i++) { //loop to create multiple Tellers
                         try {  
                        lo_exit.acquire(); }
                        catch (Exception e) {
				System.out.println( "Exception in semaphore:"+ " exception type:\n" + e);
			} 
                        loanOff = new LoanOff(s_mutex,loan_exit,lo_exit,
                        cust_with_teller,loan_finish,teller_counter, i, bankacctn, queue2, cust_done);
                        loanOff.setName(Integer.toString(i));
			loanOff.start();
		}
                
                int sem_cond = 1; 
                while (true)
                {
                    if ((cust_exit[0].availablePermits() == sem_cond) &&
                       (cust_exit[1].availablePermits() == sem_cond) &&
                       (cust_exit[2].availablePermits() == sem_cond) &&
                       (cust_exit[3].availablePermits() == sem_cond) &&
                       (cust_exit[4].availablePermits()== sem_cond) &&
                       (loan_exit[0].availablePermits() == sem_cond) &&
                       (loan_exit[1].availablePermits() == sem_cond) &&
                       (loan_exit[2].availablePermits() == sem_cond) &&
                       (loan_exit[3].availablePermits() == sem_cond) &&
                       (loan_exit[4].availablePermits()== sem_cond) && 
                        (tel_exit[0].availablePermits() == sem_cond)  &&
                        (tel_exit[1].availablePermits() == sem_cond)  &&       
                         (lo_exit.availablePermits() == sem_cond))
                        {
                        break;
                        }
                    }             
                    
                System.out.println("Bank Simulation Summary "); 
                System.out.println("               Ending Balance     "  + "    Loan Amount " );
                int total = 0; int totloan = 0;
                for (int i = 0; i < 5 ; i++)
                { 
                System.out.println("Customer " + i + ":        " + bankacctn.getBankbalance(i)+ "                " +
                  bankacctn.getBankloan(i)); 
                  total = total + bankacctn.getBankbalance(i);
                  totloan = totloan +  bankacctn.getBankloan(i);
                  // System.out.println("Totals are " + total + "     " + totloan);
                }
                
                System.out.println("Totals are         " + total + "               " + totloan); 
		System.exit(0);

	}
}
