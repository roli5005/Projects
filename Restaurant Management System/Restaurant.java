package BusinessLayer;

import java.awt.EventQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import DataLayer.BillWriter;
import PresentationLayer.AdminGUI;
import PresentationLayer.ChefGUI;
import PresentationLayer.WaiterGUI;


public class Restaurant implements IRestaurantProcessing{
	public HashMap <Order,Collection<MenuItem>> Orders = new HashMap<Order,Collection<MenuItem>>();
	public List<MenuItem> Menu;
	
	public Restaurant(List<MenuItem> Menu) {
		this.Menu = Menu;
	}
	
	public static void main(String[]args) throws IOException {
		BaseProduct fries = new BaseProduct("Fries",5);
    	BaseProduct meat = new BaseProduct("Chicken",8);
    	BaseProduct soup = new BaseProduct("Soup",7);
    	BaseProduct veggies = new BaseProduct("Tomatoes",1);
    	BaseProduct hamburger = new BaseProduct("Hamburger",15);
    	BaseProduct rice = new BaseProduct("Rice",4);
    	BaseProduct fish = new BaseProduct("Fish",(float)11.5);
    	BaseProduct coke = new BaseProduct("Coca-Cola",(float)2.99);
    	BaseProduct sprite= new BaseProduct("Sprite",(float)2.99);
    	BaseProduct taco = new BaseProduct("Taco",(float)5.5);
    	CompositeProduct hamburgerMenu = new CompositeProduct("Hamburger Menu");
    	CompositeProduct tacoMenu = new CompositeProduct("Taco Menu");
    	CompositeProduct Lunch1 = new CompositeProduct("Chicken with fries");
    	tacoMenu.add(taco);
    	tacoMenu.add(fries);
    	tacoMenu.add(coke);
    	hamburgerMenu.add(hamburger);
    	hamburgerMenu.add(fries);
    	hamburgerMenu.computePrice();
    	Lunch1.add(fries);
    	Lunch1.add(meat);
    	Lunch1.computePrice();
    	List<MenuItem> Menu = new ArrayList<MenuItem>();
    	Menu.add(Lunch1);
    	Menu.add(hamburgerMenu);
    	Menu.add(hamburger);
    	Menu.add(veggies);
    	Menu.add(fries);
    	Menu.add(soup);
    	Menu.add(meat);
    	Menu.add(tacoMenu);
    	Menu.add(sprite);
    	Menu.add(coke);
    	Menu.add(rice);
    	Menu.add(taco);
    	Menu.add(fish);
    	Restaurant rest = new Restaurant(Menu);
    	
    	
    	
    	EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                AdminGUI admin=new AdminGUI(rest);
                admin.setVisible(true);
                WaiterGUI waiter = new WaiterGUI(rest);
                waiter.setVisible(true);
                ChefGUI chef = new ChefGUI();
                waiter.addObserver(chef);
                chef.setVisible(true);
            }
        });
    	
	}
	public void addToMenu(String Name, float price) {
		BaseProduct item = new BaseProduct(Name,price);
		Menu.add(item);
	}
	public void editMenu(String Name,float price) {
		for(MenuItem x:Menu) {
			if(Name==x.getName())x.setPrice(price);;
		}
	}
	public void removeFromMenu(String Name) {
		for(MenuItem x:Menu) {
			if(Name.contentEquals(x.getName())) {Menu.remove(x); break;}
		}
	}
	public void createOrder(Collection<MenuItem> ClientsOrder) {
		Order ord = new Order();
		Orders.put(ord, ClientsOrder);
	}
	public void createBill(Order order) {
		BillWriter bill = new BillWriter();
		try {
			bill.CreateBill(Orders.get(order),order);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Orders.remove(order);
	}
	public int ItemExists(String item) {
		for(MenuItem x:this.Menu) {
			if(x.getName()==item)return 1;
		}
		return 0;
	}
}
