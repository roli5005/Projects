package BusinessLayer;

import java.util.Collection;

public interface IRestaurantProcessing {
	public void addToMenu(String Name, float price);
	public void editMenu(String Name, float price);
	public void removeFromMenu(String Name);
	public void createOrder(Collection<MenuItem> ClientsOrder);
	public void createBill(Order order);
}
