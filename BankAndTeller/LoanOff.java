package bank.project;
import java.util.*;
import java.util.concurrent.Semaphore;

public class LoanOff extends Thread {
	
	private Semaphore s_mutex;  
        private Semaphore texit[];
        private Semaphore tln;
        private Semaphore tcwt;
        private Semaphore lcf[];
        private Semaphore ttc; 
        private int ccustnum;
        BankAccountBase cbankacct;
        Queue<Transaction> queue;
        Transaction tr = new Transaction();
        boolean[] tcust_done;
        boolean can_quit = false;
        BankCustomer tbc ;
        
	public LoanOff(
		Semaphore t_s_mutex,
                Semaphore te[],
                Semaphore tl,
                Semaphore cwt,
                Semaphore lf[],
                Semaphore tc,
                int custnum,
                BankAccountBase bankacct,
                Queue<Transaction> que,
                boolean[] cust_done
                ) {
		super();
		
		s_mutex = t_s_mutex;
                texit = te;
                tcwt = cwt;
                tln=tl;
                lcf = lf;
                ttc = tc; 
                ccustnum = custnum; 
                cbankacct = bankacct; 
                queue = que;
                tcust_done = cust_done;
	}
	public void run() {
        System.out.println(this.getName()+ " Loan Officer has started work ");  
  
		while (true) {           
			if (can_quit) {
                         System.out.println(this.getName()+ " Loan Officer is leaving work ");  
                          tln.release();
                           break;
			}               
                         try {
                         if (!queue.isEmpty()) 
                          { 

                            s_mutex.acquire();
                            tr = (Transaction) queue.remove();
                            s_mutex.release();
                            }
                            else 
                            {
                                tr = null;
                            }
                        } catch(Exception ex) {
                            System.out.println(ex);
                        }
                        if (tr != null) {
                        System.out.println("Customer " +  tr.getBankCustomer().getName() + " requests Loan officer " + 
                        this.getName() + " for loan of " + tr.getAmount());
                        try {
                            Thread.sleep(100);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }     
                        
                        System.out.println(" Loan Officer " + this.getName() + " processing loan   " + 
                                "for amount " + tr.getAmount() + " for customer " +
                                tr.getBankCustomer().getName());
                   
                        try {
                            Thread.sleep(400);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        } 
                        int trtype = tr.getType(); 
                        ccustnum = tr.getBankCustomer().getAccNum();
                        
                        switch (trtype){
   /*                         case 1: // debit 
                                
                               cbankacct.debitAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                            System.out.println(" Balance for customer " + ccustnum + " after transaction " + 
                                    trtype + " is " + cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));
                                break;
                            case 2: // credit 
                                 cbankacct.creditAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                           System.out.println(" Balance for customer " + ccustnum + " after transaction " + 
                                    trtype + " is " + cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));
                                break;
     */                       case 3: // loan 
                                 cbankacct.creditAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                           System.out.print(" Loan officer approved loan for customer of " + ccustnum + " Account Balance after loan approval " + 
                                    "is " + cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));
                                cbankacct.creditLoanAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum)); 
                               System.out.println(". Customer " + ccustnum + " leaves Loan Officer ");
                                break;    
                                
                        }
                        
                        lcf[ccustnum].release(); 
                        }
                        
                int sem_cond = 1; 
                
                    if ((texit[0].availablePermits() == sem_cond) &&
                       (texit[1].availablePermits() == sem_cond) &&
                       (texit[2].availablePermits() == sem_cond) &&
                       (texit[3].availablePermits() == sem_cond) &&
                       (texit[4].availablePermits()== sem_cond))
                    {
                      
                 can_quit = true;
             
                    }            
           }    
        }
}
