package AmdPackage;
//import java.util.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Random;


public class Bank3 { 

	static Scanner scan = new Scanner(System.in);
	
	public void Acc_open(int Acc_No) 
	{			
		int Amount =0;
		try {
			
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			Statement stmt= con.createStatement();
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Full Name: ");
			String Full_Name= br.readLine();
			System.out.println("Email id: ");
			String Email= br.readLine();		
			
			String sql="INSERT INTO Account(Acc_No,Full_name,Email,Amount) VALUES("+Acc_No+",'"+Full_Name+"','"+Email+"',"+Amount+")";
			int v=stmt.executeUpdate(sql);
			if(v>0) {
				System.out.println("\nMR/MRS "+Full_Name.toUpperCase()+" YOUR ACCOUNT CREATED SUCCESSFULLY!!");
				System.out.println("YOUR ACCOUNT NO : "+Acc_No);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void Check_bal(int acnt)
	{
		int bal=0;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			Statement stmt= con.createStatement();
			
				ResultSet rs=stmt.executeQuery("select Amount from Account where Acc_No="+acnt+""); 
				if(rs.next()) {
					bal= rs.getInt("Amount");
				}
				System.out.println("AVAILABLE BALANCE: "+bal);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Withdrawl(int acnt)
	{
		System.out.println("Enter Amount To Withdrawn: ");
		int Amount=scan.nextInt();
		
		int value=0;
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			Statement stmt= con.createStatement();
			
			ResultSet rs1=stmt.executeQuery("select Amount from Account where Acc_No="+acnt+"");
			if(rs1.next())
			{
				value= rs1.getInt("Amount");
			}
			if(value<Amount) {
				System.out.println("INSUFFICIENT BALANCE!!!");
			}
			else if(Amount==0) {
				System.out.println("ENTER VALID AMOUNT");
			}
			else {
				ResultSet rs2=stmt.executeQuery("Update Account set Amount="+value+"-"+Amount+" where Acc_No="+acnt+"");
				if(rs2.next()) {
					System.out.println("\nAMOUNT DEBITED SUCCESSFULLY!!!");
				}	
			}
			int bal=0;
			ResultSet rs3=stmt.executeQuery("select Amount from Account where Acc_No="+acnt+""); 
			if(rs3.next()) {
				bal= rs3.getInt("Amount");
			}
			System.out.println("CURRENT AVAILABLE BALANCE: "+bal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Deposite(int acnt)
	{
		System.out.println("Enter Amount To Deposite: ");
		int Amount=scan.nextInt();
		int value=0;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
			Statement stmt= con.createStatement();
			
			ResultSet rs1=stmt.executeQuery("select Amount from Account where Acc_No="+acnt+"");
			if(rs1.next())
			{
				value= rs1.getInt("Amount");
			}
			
			if(Amount<=0) {
				System.out.println("ENTER VALID AMOUNT!!!");
			}
			else {
				ResultSet rs2=stmt.executeQuery("Update Account set Amount="+value+"+"+Amount+" where Acc_No="+acnt+"");
				if(rs2.next()) {
					System.out.println("AMOUNT CREDITED SUCCESSFULLY!!!");
				}
			}
			int bal=0;
			ResultSet rs3=stmt.executeQuery("select Amount from Account where Acc_No="+acnt+""); 
			if(rs3.next()) {
				bal= rs3.getInt("Amount");
			}
			System.out.println("CURRENT AVAILABLE BALANCE: "+bal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception{
		
		Bank3 bank =new Bank3();
		Connection con= null;
		Statement stmt = null;
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech", "scott", "tiger");
		stmt= con.createStatement();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
				
				
					System.out.println("\n1.OPEN NEW ACCOUNT");
					System.out.println("2.LOGIN INTO ACCOUNT");
					System.out.println("3.EXIT");
					System.out.println("Enter Your Choice: ");
					int ch=scan.nextInt();
					switch(ch) {
					case 1: Random rnd= new Random();
							int Acc_No=rnd.nextInt(99999);
							bank.Acc_open(Acc_No);
							
							System.out.println("\n*************WELCOME****************");
							while(true)
							{	
								System.out.println("\n1.CHECK BALANCE");
								System.out.println("2.WITHDRAW AMOUNT");
								System.out.println("3.DEPOSITE AMOUNT");
								System.out.println("4.EXIT");
								System.out.println("Enter Your Choice: ");
								int ch3=scan.nextInt();
								switch(ch3) {
								case 1: bank.Check_bal(Acc_No);
										break;
								case 2: bank.Withdrawl(Acc_No);
										break;
								case 3: bank.Deposite(Acc_No);
										break;
								case 4: System.out.println("THANK YOU VISIST AGAIN!!");
										return;
								default: System.out.println("WRONG INPUT!!!");
								}
							}
							
					case 2: System.out.println("Enter Account No:");
							int acnt= scan.nextInt();

							ResultSet rs1 = stmt.executeQuery("select Acc_No from Account where Acc_No="+acnt+"");
							
								if(rs1.next()) {
									System.out.println("\n**************WELCOME AGAIN******************");
									while(true)
									{
											System.out.println("\n1.CHECK BALANCE");
											System.out.println("2.WITHDRAW AMOUNT");
											System.out.println("3.DEPOSITE AMOUNT");
											System.out.println("4.EXIT");
											System.out.println("Enter Your Choice: ");
											int ch2=scan.nextInt();
											switch(ch2) {
											case 1: bank.Check_bal(acnt);
													break;
											case 2: bank.Withdrawl(acnt);
													break;
											case 3: bank.Deposite(acnt);
													break;
											case 4: System.out.println("THANK YOU VISIT AGAIN!!");
													return;
											default: System.out.println("WRONG INPUT!!!");
											}
									}
								}
								else {
									System.out.println("SORRY !!!  ACCOUNT NOT PRESENT!!!!");
									return;
								}
							
						
					case 3:
						System.out.println("THANK YOU! VISIT AGAIN! HAVE A NICE DAY!");
						return;
					}
	
			}
		}

}


