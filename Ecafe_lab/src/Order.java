import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class Order {
	databaseHandler db_handler = new databaseHandler();
	private String type;
	private int bill;
	public String deliveryAddress;
	public Date pickupTime = new Date();
	private ArrayList<Integer> itemQuantity = new ArrayList<>();
	private ArrayList<Integer> orderItems = new ArrayList<>();
	
	public Order() {
		
		deliveryAddress = null;
		bill = 0;
		type = null;
	}
	
	//checking if type of order is delivery
	public boolean isDelivery() {
		return type.equals("delivery") ? true: false;
	}
	
	//checking if type of order is pickup
	public boolean isPickup() {
		return type.equals("pickup") ? true: false;
	}
	
	//adding item to order
	public void addItem(int item, int quantity) {
		try {
			db_handler.stmt = db_handler.conn.createStatement();
			int tuples = db_handler.stmt.executeUpdate("insert into order_items (item_id, quantity) values ("+item+","+quantity+")");
			db_handler.stmt.close();
			
		}catch(SQLException se){
		   se.printStackTrace();
		}
		
		orderItems.add(item);
		itemQuantity.add(quantity);
	}
	
	public String getOrderType() {
		return type;
	}
	
	public void setOrderType(String orderType) {
		this.type = orderType;
	}
	
	//placing order
	public void placeOrder() {
		calcBill();
		
		
			System.out.println("Your order is placed");
		
	}
	
	//calculating bill of order
	public int calcBill() {
		for (int i=0; i<orderItems.size(); i++) {
			try {
				db_handler.prep_stmt = db_handler.conn.prepareStatement("select price from items where Id = ?");
				db_handler.prep_stmt.setInt(1,orderItems.get(i));
				ResultSet items = db_handler.prep_stmt.executeQuery();
				if(items.next()) {
					bill += items.getInt("price")*itemQuantity.get(i);
				}
				items.close();
				db_handler.prep_stmt.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return bill;
	}
	
	public int getBill() {
		return bill;
	}
}