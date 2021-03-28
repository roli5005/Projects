package BusinessLogic;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.itextpdf.text.DocumentException;

import DataAccess.DataConnection;
import Presentation.PDF;
import Presentation.Validate;

public class Controller {
    private  DataConnection con;
    private Validate input;

    /**
     * constructor
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Controller() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        con = new DataConnection();
        input = new Validate();
    }

    /**
     * it's a function that represents the controller unit, containing the logic and the way all components work
     * @param args
     * @throws SQLException
     * @throws DocumentException
     * @throws FileNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, DocumentException, FileNotFoundException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        int i=0;String[] command = new String[10];
        Controller C = new Controller();
        int j = 1;
        while(C.input.Fileinput[i]!=null)
        {
            command = Validate.BreakDownCommand(C.input.Fileinput[i]);
            System.out.println(C.input.Fileinput[i]);
            if(C.input.ValidateCommand(command)==1)
            {
                if(C.input.SelectCommand(command)==1) C.con.client.deleteClient(command[2]+" "+command[3]);
                if(C.input.SelectCommand(command)==2) C.con.prod.deleteProduct(command[2]);
                if(C.input.SelectCommand(command)==3) C.con.client.insertClient(command[2]+" "+command[3],command[4]);
                if(C.input.SelectCommand(command)==4) C.con.prod.insertProduct(command[2], Integer.parseInt(command[3]), Double.parseDouble(command[4]));
                if(C.input.SelectCommand(command)==5)if(C.con.prod.checkStock(command[3], Integer.parseInt(command[4]))!=-1) C.con.order.createOrder(command[1]+" "+command[2], command[3], Integer.parseInt(command[4]));
                else { PDF report = new PDF(C.con,"d:/ReportOrder"+j+".pdf");
                    report.createUnderstockPDF(); j++;
                    report.terminate();}
                if(C.input.SelectCommand(command)==6) { PDF report = new PDF(C.con,"d:/ReportClient"+j+".pdf");
                    report.createClientTable(); j++;
                    report.terminate();}
                if(C.input.SelectCommand(command)==7) {PDF report = new PDF(C.con,"d:/ReportStock"+j+".pdf"); report.createStockTable(); j++; report.terminate();}
                if(C.input.SelectCommand(command)==8) { PDF report = new PDF(C.con,"d:/ReportOrder"+j+".pdf"); report.createOrderTable();j++; report.terminate();
                    PDF report2 = new PDF(C.con,"d:/ReportBills"+j+".pdf"); report2.createBillsTable(); report2.terminate(); j++;
                }
                if(C.input.SelectCommand(command)==0) System.out.println("Invalid command");
            }
            else System.out.println("Invalid Command");
            i++;
        }
    }
}
