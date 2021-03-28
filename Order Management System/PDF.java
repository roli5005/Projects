package Presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import DataAccess.DataConnection;
import Model.Bill;
import Model.Client;
import Model.Order;
import Model.Stock;

public class PDF {
    public Document doc;
    public PdfPTable table;
    public DataConnection Management;
    /**
     * constructor
     * @param connection
     * @param Filename
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public PDF(DataConnection connection,String Filename) throws FileNotFoundException, DocumentException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Management = connection;
        doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(Filename));
        doc.open();

    }
    /**
     * closes the pdf doc
     */
    public void terminate() {
        doc.close();
    }
    /**
     * creates a pdf table with bills
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws SQLException
     */
    public void createBillsTable() throws FileNotFoundException, DocumentException, SQLException {
        List<Bill> bills = Management.order.getBills();
        table = new PdfPTable(4);
        PdfPCell C1 = new PdfPCell(new Paragraph("BillID"));
        PdfPCell C3 = new PdfPCell(new Paragraph("ProductID"));
        PdfPCell C4 = new PdfPCell(new Paragraph("Quantity"));
        PdfPCell C5 = new PdfPCell(new Paragraph("Total Price"));
        table.addCell(C1);
        table.addCell(C3);
        table.addCell(C4);
        table.addCell(C5);
        for(Bill a:bills) {
            if(a!=null) {
                C1 = new PdfPCell(new Paragraph(""+a.getIdbill()+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.getStock_idproduct()+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.getQuantity()+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.getTotalPrice()+""));
                table.addCell(C1);}
        }
        doc.add(table);

    }
    /**
     * creates pdf table with the products of the stock
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws SQLException
     */
    public void createStockTable()throws FileNotFoundException, DocumentException, SQLException {
        List<Stock> stock = Management.prod.getStock();
        table = new PdfPTable(4);
        PdfPCell C = new PdfPCell(new Paragraph("ProductID"));
        PdfPCell C1 = new PdfPCell(new Paragraph("Product"));
        PdfPCell C2 = new PdfPCell(new Paragraph("Quantity"));
        PdfPCell C3 = new PdfPCell(new Paragraph("Price"));

        table.addCell(C);
        table.addCell(C1);
        table.addCell(C2);
        table.addCell(C3);

        for(Stock a: stock) {
            if(a!=null) {
                C1 = new PdfPCell(new Paragraph(""+a.idproduct+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(a.name));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.quantity+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.price+""));
                table.addCell(C1);
            }
        }
        doc.add(table);
    }
    /**
     * creates pdf table with the clients
     * @throws FileNotFoundException
     * @throws DocumentException
     * @throws SQLException
     */
    public void createClientTable() throws FileNotFoundException, DocumentException, SQLException{
        List<Client> clients = Management.client.getClients();
        table = new PdfPTable(2);
        PdfPCell C1 = new PdfPCell(new Paragraph("Name"));
        PdfPCell C2 = new PdfPCell(new Paragraph("Address"));
        table.addCell(C1);
        table.addCell(C2);

        for(Client a:clients) {
            if(a!=null) {
                C1 = new PdfPCell(new Paragraph(a.getName()));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(a.getAddress()));
                table.addCell(C1);
            }
        }

        doc.add(table);
    }
    /**
     * creates a pdf table with the orders
     * @throws DocumentException
     * @throws SQLException
     */
    public void createOrderTable() throws DocumentException, SQLException {
        table = new PdfPTable(4);
        List<Order> orders = Management.order.getOrder();
        PdfPCell C2 = new PdfPCell(new Paragraph("Client Name"));
        PdfPCell C3 = new PdfPCell(new Paragraph("Product"));
        PdfPCell C4 = new PdfPCell(new Paragraph("Quantity"));
        PdfPCell C1 = new PdfPCell(new Paragraph("BillID"));
        table.addCell(C2);
        table.addCell(C3);
        table.addCell(C4);
        table.addCell(C1);
        for(Order a:orders) {
            if(a!=null) {
                C1 = new PdfPCell(new Paragraph(a.getClientName()));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(a.getStockProduct()));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.getQuantity()+""));
                table.addCell(C1);
                C1 = new PdfPCell(new Paragraph(""+a.getBill_idbill()+""));
                table.addCell(C1);
            }
        }

        doc.add(table);
    }
    /**
     * creates table with an under-stock message
     * @throws DocumentException
     */
    public void createUnderstockPDF() throws DocumentException {
        doc.add(new Paragraph("The product is under-stock and the order cannot be done."));
    }

}

