package Model;

public class Order {
    private int idorder;
    private int client_idclient;
    private int bill_idbill;
    private String clientName;
    private String stockProduct;
    private int quantity;

    public Order() {

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
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return the stockProduct
     */
    public String getStockProduct() {
        return stockProduct;
    }

    /**
     * @param stockProduct the stockProduct to set
     */
    public void setStockProduct(String stockProduct) {
        this.stockProduct = stockProduct;
    }

    /**
     * @return the idorder
     */
    public int getIdorder() {
        return idorder;
    }

    /**
     * @param idorder the idorder to set
     */
    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }

    /**
     * @return the client_idclient
     */
    public int getClient_idclient() {
        return client_idclient;
    }

    /**
     * @param client_idclient the client_idclient to set
     */
    public void setClient_idclient(int client_idclient) {
        this.client_idclient = client_idclient;
    }

    /**
     * @return the bill_idbill
     */
    public int getBill_idbill() {
        return bill_idbill;
    }

    /**
     * @param bill_idbill the bill_idbill to set
     */
    public void setBill_idbill(int bill_idbill) {
        this.bill_idbill = bill_idbill;
    }


}
