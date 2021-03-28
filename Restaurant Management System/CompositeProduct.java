package BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class CompositeProduct implements MenuItem{
	public List<BaseProduct> baseprod = new ArrayList<BaseProduct>();
	public String Name;
	public float Price;
	
	public CompositeProduct(String Name) {
		this.Name = Name;
	}
	public CompositeProduct(String Name,List<BaseProduct> prodlist) {
		this.Name = Name;
		baseprod = prodlist;
		computePrice();
	}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public float getPrice() {
	return Price;
}
public void setPrice(float price) {
	Price = price;
}
public float computePrice() {
	float price=0;
	for(BaseProduct prod: baseprod) {
		price += prod.computePrice();
	}
	this.Price = price;
	return Price;
}
public void add(BaseProduct item) {
	this.baseprod.add(item);
}
public void remove(BaseProduct item) {
	this.baseprod.remove(item);
}
}
