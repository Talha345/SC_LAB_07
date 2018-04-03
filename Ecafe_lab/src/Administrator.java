import java.sql.*;
import java.util.Scanner;


public class Administrator {
	databaseHandler db_handler = new databaseHandler();
	Scanner sc=new Scanner(System.in);
	private String username="admin";
	private String password="admin";
	
	public Administrator(){

}
	
	public void login(String user,String pass){
		if(username.equals(user) && password.equals(pass)){
		System.out.println("You have logged in Successfully!");
		}
			else
			{	
			System.out.println("Invalid Username and Password!");
			System.out.println("Please Enter your Username!");
			String us=sc.next();
			System.out.println("Please Enter your Password!");
    	 	String pas=sc.next();
    	 	login(us,pas);
    	 	
			}
			
		}
		

	public void showOrders(){
		System.out.println("============================================= ORDERS ==============================================");
		System.out.println("Order ID         Order Type              Delivery Address          Pickup Time                 Bill");
		System.out.println("---------------------------------------------------------------------------------------------------");
		try {
			db_handler.prep_stmt = db_handler.conn.prepareStatement("select * from orders");
			ResultSet items = db_handler.prep_stmt.executeQuery();
			while(items.next()) {
				System.out.printf("   %-14d%-24s%-25s%-28s%-10d",items.getInt("id"),items.getString("type"),items.getString("delivery_addr"), items.getString("pickupTime"),items.getInt("bill"));
				System.out.println();
			}
			items.close();
			db_handler.prep_stmt.close();
		}catch(SQLException se){
		   se.printStackTrace();
		}
		System.out.println("------------------------------------------------------------------");
	}
	
	
	}
	

	
