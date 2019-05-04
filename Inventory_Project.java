import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class Inventory_Project {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		try
		{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;	
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "DATABASE ADMIN", "1252");
		if(conn!=null)
		{
			//System.out.println("Connected");
			Scanner in = new Scanner(System.in);
			
			System.out.println("1. Admin Login\n2. Agent Login\n3. Exit");
			System.out.print("Choose an Option: ");
			int option = in.nextInt();
			if(option == 1)
			{
				System.out.println("Enter the Admin UserName, UserId and Password");
				String uName = in.next();
				String uId = in.next();
				String password = in.next();
				PreparedStatement pst = conn.prepareStatement("INSERT INTO User_Details VALUES(?,?,?,?)");
				pst.setString(1, uName);
				pst.setString(2, uId);
				pst.setString(3, password);
				pst.setString(4, "Admin");
				pst.executeUpdate();
				
			  boolean temp = true;	
			  while(temp != false)
				{
				System.out.println("1. Add Product\n2. Display Inventory Details\n3. Logout");
				System.out.print("Choose your option: ");
				int n = in.nextInt();
					
				switch(n)
				{
					case 1:
						System.out.println("Add Product Details: ");
						System.out.println("Enter the Product ID: ");
						int prodId = in.nextInt();
						System.out.println("Enter the Product Name: ");
						String prodName = in.next();
						System.out.println("Enter the Min sell: ");
						int minSell = in.nextInt();
						System.out.println("Enter the Price: ");
						int price = in.nextInt();
						
						PreparedStatement ps = conn.prepareStatement("INSERT INTO Product_Details VALUES(?,?,?,?)");
						ps.setInt(1, prodId);
						ps.setString(2, prodName);
						ps.setInt(3, minSell);
						ps.setInt(4, price);
						int status = ps.executeUpdate();
						break;
						
					case 2:
						Statement stmt = conn.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT * FROM Product_Details");
						System.out.println("The products Available in Inventory are: ");
						while(rs.next())
						{
							System.out.println(" "+rs.getString(2));
						}
						break;
						
					case 3:
						temp = false;
						break;
				}
		      }
			}
			
			else if(option == 2)
			{
				System.out.println("Enter the Agent UserName, UserId and Password  ");
				String userName = in.next();
				String userId = in.next();
				String userPass = in.next();
				PreparedStatement p = conn.prepareStatement("INSERT INTO User_Details VALUES(?,?,?,?)");
				p.setString(1, userName);
				p.setString(2, userId);
				p.setString(3, userPass);
				p.setString(4, "Agent");
				p.executeUpdate();
				
				System.out.println();
				boolean temp = true;
				while(temp != false)
				{
					
				System.out.println("1. Buy\n2. Logout");
				int opt = in.nextInt();
	
				  switch(opt)
					{
				  
				      case 1:
				    	  Scanner sc = new Scanner(System.in);
				    	  System.out.println("Enter the Product Id: ");
				    	  int var = sc.nextInt();
				    	  
				    	  Statement st = conn.createStatement();
				    	  System.out.println("Product details are: ");
				    	  ResultSet r = st.executeQuery("SELECT ProdName, MinSellQuan, Price FROM Product_Details WHERE ProdId IN var");
				    	  //String proName;
				    	  int msq = 0;
				    	  int pr = 0;
				    	  while(r.next()) {
				    		  //proName = r.getString(1);
				    		  msq = r.getInt(2);
				    		  pr = r.getInt(3);
				    	  System.out.print(r.getString(1));
				    	  System.out.print(" "+r.getInt(2));
				    	  System.out.println(" " +r.getInt(3));
				    	  }
				    	  System.out.println("Enter the Quantity you want to buy: ");
				    	  int quant = in.nextInt();
				    	  
				    	  if(quant > msq) {
				    		  System.out.println("Your Booking is Confirmed!!. \nYour Total Price is: " + pr*quant);
				    	  }
				    	  else {
				    		  System.out.println("The Product is not Available!!");
				    	  }
				    	  break;
				    	  
				     case 2:	
				    	 System.out.println("You have successfully LOGOUT.");
				    	 temp = false;
				    	 break;
					}
				}
			}
			else {
				System.out.println("You have successfully exit from the Interface: ");
			}
		}
		else
		{
			System.out.println("Not Connected");
		}		
		
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Driver class not found. Exception occured!!");
		}
		catch(SQLException se)
		{
			System.out.println("Exception occured while making connection");
		}
	}

}
