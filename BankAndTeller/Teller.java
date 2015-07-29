package bank.project;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Teller extends Thread {
	
	private Semaphore s_mutex; 
        private Semaphore texit[];
        private Semaphore tlexit[];
        private Semaphore tcwt;
        private Semaphore tcf[];
        private Semaphore ttc; 
        private int ccustnum;
        BankAccountBase cbankacct;
        Queue<Transaction> queue;
        Transaction tr = new Transaction();
        boolean[] tcust_done;
        boolean can_quit = false;
        BankCustomer tbc ;
        
	public Teller(
		
		Semaphore t_s_mutex,
                Semaphore te[],
                Semaphore tl[],
                Semaphore cwt,
                Semaphore cf[],
                Semaphore tc,
                int custnum,
                BankAccountBase bankacct,
                Queue<Transaction> que,
                boolean[] cust_done
                ) {
		super();		
		s_mutex = t_s_mutex;
                texit = te;
                tlexit = tl;
                tcwt = cwt;
                tcf = cf;
                ttc = tc; 
                ccustnum = custnum; 
                cbankacct = bankacct; 
                queue = que;
                tcust_done = cust_done;
	}
	public void run() {
        System.out.println(this.getName()+ " Teller has started work  ");  
		while (true) {           
			if (can_quit) {
                          System.out.println(this.getName()+ " Teller is leaving work "); 
                          int tlidx = Integer.parseInt(this.getName());
                          tlexit[tlidx].release();
                           break;
			}
                         try {
                            if (!queue.isEmpty()) 
                            { 
                            s_mutex.acquire();
                            tr = (Transaction) queue.remove();
                            s_mutex.release();
                            ccustnum = tr.getBankCustomer().getAccNum(); 
                            }
                            else 
                            {
                                tr = null;
                            }
                        } catch(Exception ex) {
                            System.out.println(ex);
                        }
                
                        if (tr != null) {
                        
                        if  (tr.getType() == 1) {   
                        System.out.println("Customer " +  tr.getBankCustomer().getName() + " requests Teller " + 
                        this.getName() + " for cash of " + tr.getAmount());
                        }
                        else if (tr.getType() == 2) {
                        System.out.println("Customer " +  tr.getBankCustomer().getName() + " request Teller " + 
                        this.getName() + " for crediting " + tr.getAmount());   
                        }
                            
                        try {
                            Thread.sleep(100);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }        
                        if (tr.getType() == 1) {
                        System.out.println(" Teller " + this.getName() + " processing cash request  " + 
                                "for amount " + tr.getAmount() + " for customer " +
                                tr.getBankCustomer().getName());
                        }
                        else if (tr.getType() == 2) {
                        System.out.println(" Teller " + this.getName() + " processing credit request  " + 
                                "for amount " + tr.getAmount() + " for customer " +
                                tr.getBankCustomer().getName());
                        }
                            
                        try {
                            Thread.sleep(400);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        } 
                        int trtype = tr.getType(); 
                        ccustnum = tr.getBankCustomer().getAccNum();
              
                        switch (trtype){
                            case 1: // debit 
                                
                               cbankacct.debitAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                               
                               
                               System.out.print(" Teller " + this.getName() + " gives cash to customer " + ccustnum + " of " +
                                       tr.getAmount() + ". Account Balance after cash withdrawal is " + 
                                       cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));    
                               System.out.println(" Customer " + ccustnum + " leaves Teller " + this.getName());
                                break;
                            case 2: // credit 
                                 cbankacct.creditAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                           System.out.print(" Teller " + this.getName() + " accepts credit for customer " + ccustnum + " of " +
                                       tr.getAmount() + ". Account Balance after credit is " + 
                                       cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));
                           System.out.println(". Customer " + ccustnum + " leaves Teller " + this.getName());
                                break;
                           /* case 3: // loan 
                                 cbankacct.creditAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum));
                           System.out.println(" Balance for customer " + ccustnum + " after transaction " + 
                                    trtype + " is " + cbankacct.getBankbalance(cbankacct.getBankCustomer(ccustnum)));
                                cbankacct.creditLoanAccount(ccustnum,tr.getAmount(),cbankacct.getBankCustomer(ccustnum)); 
                                break; */       
                        }
                     
                        tcf[ccustnum].release();     // signal customer transaction is done  
                        }
                        
                int sem_cond = 1; 
                
                    if ((texit[0].availablePermits() == sem_cond) &&
                       (texit[1].availablePermits() == sem_cond) &&
                       (texit[2].availablePermits() == sem_cond) &&
                       (texit[3].availablePermits() == sem_cond) &&
                       (texit[4].availablePermits()== sem_cond))
                    {
                      
                 can_quit = true;
   //              System.out.println(" ------   All customers are done, queue empty, teller leaving ");
                    }
             
         //   System.out.println(" Teller " + this.getName() + " is looping ");
           }    
        }
}
        
