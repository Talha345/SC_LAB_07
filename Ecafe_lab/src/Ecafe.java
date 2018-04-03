import java.util.Date;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;



public class Ecafe {
	private Date openTime = new Date();
	private Date closeTime = new Date();
	private Menu menu = new Menu();
	private Order order;
	public int orderTime[] = new int[2];
	
	@SuppressWarnings("deprecation")
	public Ecafe() {
		openTime.setHours(10);
		closeTime.setHours(23);
	}
	
	@SuppressWarnings("deprecation")
	public boolean validOrderTime(int hour, int mint) {
		if(openTime.getHours() > hour || closeTime.getHours() <= hour) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		databaseHandler db_handler = new databaseHandler();
		int itemID = 1, quantity = 0;
		int choice;
		Date time = new Date();
		Scanner sc=new Scanner(System.in);
		Ecafe ecafe = new Ecafe();
		Administrator admin=new Administrator();
		staff staff1=new staff();
		int complete;
		
			for(;;) {
				ecafe.order = new Order();
				System.out.println("\n\t     =========> Welcome to Cafe 420 <=========");
				System.out.println();
				System.out.println("=> Type 0 to place the order!");
				System.out.println("=> Type 1 to login as Administrator!");
				System.out.println("=> Type 2 to login as Staff Member!");
				choice=sc.nextInt();
				switch(choice) {
		         case 0 :
		        	 ecafe.menu.showMenu();
		        	 System.out.println("=> Type 0 to end the order!");
		        	 for(;;) {
							System.out.print("Type the Item ID: ");
							itemID = sc.nextInt();
							if (itemID == 0)
								break;
							System.out.print("=> Quantity of Item-"+itemID+": ");
							quantity = sc.nextInt();
							ecafe.order.addItem(itemID, quantity);
							
						}

						if (itemID == 0) {
							System.out.println("1. Home Delivery");
							System.out.println("2. Self Pick-up");
							int orderType = sc.nextInt();
							if (orderType == 1) {
								ecafe.order.setOrderType("delivery");
								System.out.print("Type the delivery address: ");
								ecafe.order.deliveryAddress = sc.next();
								ecafe.order.placeOrder();
								System.out.println("Bill = Rs."+ecafe.order.getBill());
								
								try {
									db_handler.prep_stmt = db_handler.conn.prepareStatement("insert into orders (type, delivery_addr, bill) values (?,?,?);");
									db_handler.prep_stmt.setString(1, "delivery");
									db_handler.prep_stmt.setString(2, ecafe.order.deliveryAddress);
									db_handler.prep_stmt.setInt(3, ecafe.order.getBill());
									
									int affected_tuples = db_handler.prep_stmt.executeUpdate();
									db_handler.prep_stmt.close();
									
								}catch(SQLException se){
								   se.printStackTrace();
								}

							}
							else if (orderType == 2) {
								ecafe.order.setOrderType("pickup");
								System.out.println("Enter pick up time (24-h format => <hrs mints>):");
								ecafe.orderTime[0] = sc.nextInt();
								ecafe.orderTime[1] = sc.nextInt();
								
									ecafe.order.pickupTime.setHours(ecafe.orderTime[0]);
									ecafe.order.pickupTime.setMinutes(ecafe.orderTime[1]);
									ecafe.order.placeOrder();	
									System.out.println("Bill = Rs."+ecafe.order.getBill());
									Calendar cal = Calendar.getInstance();
							        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							        
									
									
									try {
										db_handler.prep_stmt = db_handler.conn.prepareStatement("insert into orders (type, pickupTime, bill) values (?,?,?)");
										db_handler.prep_stmt.setString(1, "pickup");
										db_handler.prep_stmt.setString(2,sdf.format(cal.getTime()) );
										db_handler.prep_stmt.setInt(3, ecafe.order.getBill());
										
										int affected_tuples = db_handler.prep_stmt.executeUpdate();
										
										db_handler.prep_stmt.close();
									}catch(SQLException se){
									   se.printStackTrace();
									}
								}
								else {
									System.out.println("Your order cannot be placed at this time");
								}
						}
		            break;
		         case 1 :
		        	 
		        	 System.out.println("Please Enter your Username!");
		        	 String username=sc.next();
		        	 System.out.println("Please Enter your Password!");
		        	 String password=sc.next();
		        	 admin.login(username, password);
		        	 admin.showOrders();
		        	 break;
		        		 
		         case 2 :
		        	 System.out.println("Please Enter your Username!");
		        	 String user=sc.next();
		        	 System.out.println("Please Enter your Password!");
		        	 String pass=sc.next();
		        	 staff1.login(user, pass);
		        	 staff1.showStatus();
		        	 System.out.println("Type Order ID to mark that order as complete!");
		        	 complete= sc.nextInt();
		        	 staff1.markComplete(complete);	
		        	 break;
		            
						}		

					}
			
				}
			}
		
	

