package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Stock;

public class ProductData {
    public Connection conn;
    /**
     * constructor
     * @param conn
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    ProductData(Connection conn) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        this.conn = conn;
    }
    /**
     * creates a new product and inserts it into the database
     * @param Name
     * @param Quantity
     * @param Price
     * @throws SQLException
     */
    public void insertProduct(String Name,int Quantity,double Price) throws SQLException {
        if(productexists(Name)==0)
        {
            String sql = "INSERT INTO `management`.`stock` (`Product`,`Quantity`,`Price`) VALUES ('"+Name+"','"+Quantity+"','"+Price+"')";
            PreparedStatement ps = conn.prepareStatement(sql);

            int status = ps.executeUpdate();

            if(status !=0 )
                System.out.println("success");
            else System.out.println("FAIL\n");
        }
        else {
            System.out.println("The product already exists, we will update it");
            this.updateStock(Name, Quantity, Price);
        }
    }
    /**
     * updates the informations about the product
     * @param Name
     * @param Quantity
     * @param Price
     * @throws SQLException
     */
    public void updateStock(String Name,int Quantity,double Price) throws SQLException{
        if(Quantity==0) this.deleteProduct(Name);
        else {
            String sql = "update stock set Quantity=?,Price=? where Product=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(3, Name);
            ps.setInt(1,Quantity);
            ps.setDouble(2, Price);
            int status = ps.executeUpdate();

            if(status !=0 )
                System.out.println("success");
            else System.out.println("FAIL\n");
        }
    }
    /**
     * deletes a product
     * @param Name
     * @throws SQLException
     */
    public void deleteProduct(String Name)throws SQLException {
        String sql = "delete from Stock where Product = '"+Name+"'";
        PreparedStatement ps = conn.prepareStatement(sql);
        int status = ps.executeUpdate();

        if(status !=0 )
            System.out.println("success");
        else System.out.println("FAIL\n");
        ps.close();

    }
    /**
     * checks if there is enough quantity of the specified product for the order
     * @param Product
     * @param Quantity
     * @return
     * @throws SQLException
     */
    public int checkStock(String Product, int Quantity) throws SQLException{
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Quantity from stock where Product ='"+Product+"'");
        ResultSet result = stm.getResultSet();
        int qnt = 0;
        while(result.next())
            qnt = result.getInt("Quantity");
        stm.close();
        if(qnt>=Quantity)return qnt;
        else return -1;
    }
    /**
     * checks if a product exists
     * @param Name
     * @return
     * @throws SQLException
     */
    public int productexists(String Name) throws SQLException {
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select * from stock");
        ResultSet result = stm.getResultSet();
        String aux="";
        int ok = 0;
        while(result.next()) {
            aux = result.getString("Product");
            if(aux.contentEquals(Name))ok=1;
        }
        stm.close();
        return ok;
    }
    /**
     * returns the id of a product
     * @param Name
     * @return
     * @throws SQLException
     */
    public int getProductID(String Name) throws SQLException {
        int id=0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select idproduct from stock where Product ='"+Name+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            id = result.getInt("idproduct");
        stm.close();
        return id;
    }
    /**
     * returns the name of the product by id
     * @param ID
     * @return
     * @throws SQLException
     */
    public String getProductName(int ID) throws SQLException {
        String name="No such product";
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Product from stock where idproduct = '"+ID+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            name = result.getString("Product");
        return name;
    }
    /**
     * returns the price of a product
     * @param Product
     * @return
     * @throws SQLException
     */
    public int getProductPrice(String Product) throws SQLException {
        int price=0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Price from stock where Product ='"+Product+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            price = result.getInt("Price");
        stm.close();
        return price;
    }

    /**
     * returns a list of products
     * @return
     * @throws SQLException
     */
    public List<Stock> getStock()throws SQLException{
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select * from stock");
        ResultSet result = stm.getResultSet();

        List<Stock> products = new ArrayList<Stock>();
        while(result.next())
        {
            Stock product = new Stock();
            product.setIdproduct(result.getInt("idproduct"));
            product.setName( result.getString("Product"));
            product.setQuantity(result.getInt("Quantity"));
            product.setPrice(result.getDouble("Price"));
            products.add(product);
        }
        stm.close();
        return products;
    }
}
