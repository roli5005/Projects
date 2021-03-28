package Model;

public class Stock {
    public int idproduct;
    public String name;
    public int quantity;
    public double price;

    Stock(String name, int quantity, double price){
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }
    public Stock() {

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the idproduct
     */
    public int getIdproduct() {
        return idproduct;
    }
    /**
     * @param idproduct the idproduct to set
     */
    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }


}
