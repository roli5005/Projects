package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Bill;
import Model.Order;

public class OrderData {
    public Connection conn;
    public ProductData prod;
    public ClientData client;
    public List<Order> orderslist;
    public List<Bill> billslist;

    /**
     * constructor
     * @param conn
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    OrderData(Connection conn) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        this.conn = conn;
        prod = new ProductData(conn);
        client = new ClientData(conn);
        orderslist = new ArrayList<Order>();
        billslist = new ArrayList<Bill>();
    }
    /**
     * creates bill using the parameters
     * @param Product
     * @param Quantity
     * @throws SQLException
     */
    public void createBill(String Product, int Quantity) throws SQLException {
        int idprod = prod.getProductID(Product);
        int price = prod.getProductPrice(Product);
        String cmd = "insert into bill (stock_idproduct,Quantity,TotalPrice) values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(cmd);
        ps = conn.prepareStatement(cmd);
        ps.setInt(1, idprod);
        ps.setInt(2, Quantity);
        ps.setDouble(3, Quantity * price);
        int status = ps.executeUpdate();

        if(status !=0 )
            System.out.println("success");
        else System.out.println("FAIL");
        ps.close();

        Bill bill = new Bill();
        bill.setIdbill(this.getBillID(Product, Quantity));
        bill.setQuantity(Quantity);
        bill.setStock_idproduct(idprod);
        bill.setTotalPrice(Quantity * price);
        billslist.add(bill);
    }
    /**
     * creates an order using the parameters
     * @param Client
     * @param Product
     * @param Quantity
     * @throws SQLException
     */
    public void createOrder(String Client,String Product, int Quantity)throws SQLException {
        if(prod.checkStock(Product,Quantity)!=-1) {
            this.createBill(Product,Quantity);
            int idclient = client.getClientID(Client);
            int idbill = this.getBillID(Product,Quantity);
            String cmd = "INSERT INTO `management`.`order` (`client_idclient`,`bill_idbill`) VALUES ('"+idclient+"','"+idbill+"')";
            PreparedStatement ps = conn.prepareStatement(cmd);
            ps = conn.prepareStatement(cmd);
            int status = ps.executeUpdate();

            if(status !=0 )
                System.out.println("success");
            else System.out.println("FAIL\n");
            ps.close();

            Order ord = new Order();
            ord.setBill_idbill(idbill);
            ord.setClient_idclient(idclient);
            ord.setClientName(Client);
            ord.setQuantity(Quantity);
            ord.setStockProduct(Product);
            orderslist.add(ord);

            int price = prod.getProductPrice(Product);
            prod.updateStock(Product, prod.checkStock(Product, Quantity) - Quantity,price);
        }
    }
    /**
     * returns id of the bill
     * @param Product
     * @param Quantity
     * @return
     * @throws SQLException
     */
    public int getBillID(String Product, int Quantity) throws SQLException {
        int id=0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select idbill from bill where stock_idproduct ='"+prod.getProductID(Product)+"' && Quantity='"+Quantity+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            id = result.getInt("idbill");
        stm.close();
        return id;
    }

    /**
     * returns id of the order
     * @param idbill
     * @param idclient
     * @return
     * @throws SQLException
     */
    public int getOrderID(int idbill,int idclient) throws SQLException {
        int id=0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select idorder from order where client_idclient ='"+idclient+"' && bill_idbill='"+idbill+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            id = result.getInt("idorder");
        stm.close();
        return id;
    }
    /**
     * returns order quantity by id of the bill
     * @param idbill
     * @return
     * @throws SQLException
     */
    public int getOrderQuantity(int idbill) throws SQLException {
        int Q = 0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Quantity from bill where idbill ='"+idbill+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            Q=result.getInt("Quantity");
        return Q;
    }
    /**
     * returns the list of bills
     * @return
     */
    public List<Bill> getBills(){
        return billslist;
    }
    /**
     * returns the list of orders
     * @return
     */
    public List<Order> getOrder(){
        return orderslist;
    }
}
