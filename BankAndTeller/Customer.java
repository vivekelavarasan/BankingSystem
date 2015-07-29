package bank.project;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Customer extends Thread {
	
	private Semaphore s_mutex;
        private Semaphore ctc; 
        private Semaphore ccf[];
        private Semaphore lcf[];
        private Semaphore cexit[];
        private Semaphore qexit[];
        private Semaphore lexit[];
        private Semaphore ccwt;
        private Semaphore clc;
        private Semaphore ccwl;
        private Semaphore csm1;
        private Semaphore csm2;
        private BankCustomer bankcust; 
        private int ccustnum;
        BankAccountBase cbankacct;
        Queue<Transaction> queue ;
        Queue<Transaction> queue2 ;
        int loopcount = 0; 
        Transaction tr;
        boolean[] ccust_done;
        boolean cust_busy = false;

	public Customer(
		
		Semaphore t_s_mutex,
                Semaphore tc, 
                Semaphore cf[],
                Semaphore lf[],
                Semaphore ce[],
                Semaphore qe[],
                Semaphore le[],
                Semaphore cwt,
                Semaphore lc,
                Semaphore cwl,
                Semaphore sm1,
                Semaphore sm2,
                int custnum,
                BankAccountBase bankacctn,
                Queue<Transaction> que,
                Queue<Transaction> que2,
                boolean[] cust_done
                ) {
		super();
		
		s_mutex = t_s_mutex;
                ctc = tc;
                ccf = cf;
                lcf = lf;
                cexit = ce;
                qexit = qe;
                lexit = le;
                ccwt = cwt;
                clc = lc;
                ccwl = cwl;
                csm1 = sm1;
                csm2 = sm2; 
                ccustnum = custnum; 
                cbankacct = bankacctn;  
                queue = que;
                queue2 = que2; 
                ccust_done = cust_done;
                
	}
	public void run() {
		boolean status = true;
		int element = 0;
		Result res = null;
                int tran_amount = 0; int minnum = 100; int maxnum = 500; int tran_type = 0;
                Random ran = new Random();
                bankcust = new BankCustomer(this.getName(),ccustnum);
                cbankacct.addCustomerAccount(bankcust);
                System.out.println("Customer " + ccustnum + " created ");
  		while (true) {
	                tran_amount = 0; minnum = 100; maxnum = 500; 
                        tran_amount = ((ran.nextInt(500) + 99)/100)*100;
                        minnum = 1; maxnum = 3; 
                        tran_type = ran.nextInt(3) + 1;
                        tr = new Transaction(tran_type,tran_amount,bankcust);
 //                     System.out.println(" Customer " + ccustnum + " tran " + tran_amount + " " + tran_type);
                try {        
                      if ((tran_type == 1)|| (tran_type == 2))
                      {
                        ctc.acquire();
                        s_mutex.acquire();                         
                        queue.add(tr);
                        s_mutex.release();
                        ccf[ccustnum].acquire();
                        ctc.release();
                      }
                      else
                      {
                        clc.acquire();
                        s_mutex.acquire();
                        queue2.add(tr);
                        s_mutex.release();
                        lcf[ccustnum].acquire();
                        clc.release();   
                       }
//                        ccwt.release();
		//	System.out.println(this.getName()+ "<- Customer is exiting of CS");
                }
                catch (Exception e) {
				System.out.println(
					"Exception in semaphore :"
						+ " exception type:\n"
						+ e);
			}
	 
                        if (loopcount == 2) {
                            ccust_done[ccustnum] = true;
           //                 System.out.println(this.getName()+ "<- Customer is done with 3 transactions ");
                            try {
           //                 System.out.println(" Sem value at exit " + ccf[ccustnum].availablePermits());
                            ccf[ccustnum].acquire();  // wait till last transaction is completed by teller or loan officer 
                            ccf[ccustnum].release();
                            lcf[ccustnum].acquire();
                            lcf[ccustnum].release();
                            cexit[ccustnum].release(); // semaphore customer leaving the bank
                            qexit[ccustnum].release();// semaphore customer telling teller - he is leaving
                            lexit[ccustnum].release();// semaphore customer telling loan officer - he is leaving
                            }
                        catch (Exception e) {System.out.println( "Exception in semaphore:"+ " exception type:\n" + e);
			}   
                            break;
                        }
                        loopcount++; 
		}

	}
}
