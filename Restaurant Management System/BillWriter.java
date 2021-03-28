package DataLayer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import BusinessLayer.BaseProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;

public class BillWriter {
	
	public void CreateBill(Collection<MenuItem> List, Order order) throws IOException {
	File file = new File("d://Bill"+order.hashCode()+".txt");
	  
    //Create the file
    if (file.createNewFile()){
      System.out.println("File is created!");
    }else{
      System.out.println("File already exists.");
    }
     
    //Write Content
    FileWriter writer = new FileWriter(file);
    writer.write("Table "+order.getTable()+"\n");
    writer.write("---------------------\n");
    float total=0;
    for(MenuItem x:List) {
    	writer.write(x.getName()+"  "+x.computePrice()+" Lei\n");
    	total+=x.computePrice();
    }
    writer.write("---------------------\n");
    writer.write("Total: "+total+" Lei\n");
    writer.write(order.getDate().toString()+" "+order.getTime());
    writer.close();
	}
	
	public static void main(String[] args) throws IOException {
		BillWriter bill = new BillWriter();
		Collection<MenuItem> ord = new ArrayList<MenuItem>();
		BaseProduct fries = new BaseProduct("Fries",5);
    	BaseProduct meat = new BaseProduct("Chicken",8);
    	BaseProduct soup = new BaseProduct("Soup",7);
    	ord.add(meat);
    	ord.add(fries);
    	ord.add(soup);
    	Order o = new Order();
		bill.CreateBill(ord,o);
	}
}
