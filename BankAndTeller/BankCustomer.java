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
public class BankCustomer {
 
/* attributes*/
private String name; 
private int accnum;

/* constructor method */
public BankCustomer(String name, int accnum){
this.name = name;
this.accnum = accnum;
}
 
/* set methods */
public void setName(String name)
{ this.name = name;}

public void setAccNum(int accnum) {
this.accnum = accnum; 
}
 
/* get methods */

public String getName()
{ return name; }

public int getAccNum() 
{ return accnum; } 
 
/*toString() method */
public String toString()
{ return "\n" + "Name:  " + name + " Cust Acc: " + accnum; }

}  
