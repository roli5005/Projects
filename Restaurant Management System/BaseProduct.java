package BusinessLayer;

public class BaseProduct implements MenuItem{
String Name;
float Price;

public BaseProduct(String Name,float Price) {
	this.Name = Name;
	this.Price = Price;
}
public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public void setPrice(float price) {
	this.Price = price;
}
@Override
public float computePrice() {
	// TODO Auto-generated method stub
	return this.Price;
}
}
