import java.sql.*;
import java.util.Scanner;

public class staff {
	databaseHandler db_handler = new databaseHandler();
	Scanner sc=new Scanner(System.in);
	private String user1="staff";
	private String pass1="staff";
	
	public staff(){

}
	
	public void login(String user,String pass){
		if(user1.equals(user) && pass1.equals(pass)){
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
	
	public void showStatus(){
		System.out.println("============================ ORDER STATUS ===============================");
		System.out.println("Order ID         Order Type              Order Bill          Order Status");
		System.out.println("-------------------------------------------------------------------------");
		try {
			db_handler.prep_stmt = db_handler.conn.prepareStatement("select * from staff");
			ResultSet items = db_handler.prep_stmt.executeQuery();
			while(items.next()) {
				System.out.printf("  %-16d%-24s%-22d%-10s",items.getInt("ID"),items.getString("TYPE"),items.getInt("BILL"), items.getString("STATUS"));
				System.out.println();
			}
			items.close();
			db_handler.prep_stmt.close();
		}catch(SQLException se){
		   se.printStackTrace();
		}
		System.out.println("-------------------------------------------------------------------------");
	}
	
	public void markComplete(int id){
		try {
			
			db_handler.prep_stmt = db_handler.conn.prepareStatement("update orders set status='Complete' where id = ?");
			db_handler.prep_stmt.setInt(1,id);
			int affected_tuples = db_handler.prep_stmt.executeUpdate();
			db_handler.prep_stmt.close();
			System.out.printf("Order %d has been marked as complete",id);
		}catch(SQLException se){
		   se.printStackTrace();
		}
			
	}
	
	}
	

