package Model;

public class Bill {
    private int idbill;
    private int stock_idproduct;
    private int Quantity;
    private double totalPrice;


    public Bill(){

    }
    /**
     * @return the quantity
     */
    public int getQuantity() {
        return Quantity;
    }


    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


    /**
     * @return the totalPrice
     */
    public double getTotalPrice() {
        return totalPrice;
    }


    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    /**
     * @return the idbill
     */
    public int getIdbill() {
        return idbill;
    }
    /**
     * @param idbill the idbill to set
     */
    public void setIdbill(int idbill) {
        this.idbill = idbill;
    }
    /**
     * @return the stock_idproduct
     */
    public int getStock_idproduct() {
        return stock_idproduct;
    }
    /**
     * @param stock_idproduct the stock_idproduct to set
     */
    public void setStock_idproduct(int stock_idproduct) {
        this.stock_idproduct = stock_idproduct;
    }
}
