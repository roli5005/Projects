package PresentationLayer;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Observer;

public class ChefGUI extends JFrame implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<MenuItem> Orders = new ArrayList<MenuItem>();
	final JTable table=new JTable();
	private int NrOfOrders=0;
	
	public ChefGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200,200,600,400);
        setTitle("Chef Window");
        getContentPane().setLayout(null);
        JScrollPane scroll=new JScrollPane();
        scroll.setBounds(30,30,500,300);
        table.setFont(new FontUIResource(new Font("Calibri", 0, 18)));
        getContentPane().add(scroll);
        scroll.setViewportView(table);
        table.setModel(model);  
        model.addColumn("Number");
        model.addColumn("Menu Item");
        for(MenuItem c:Orders) {
        	model.addRow(new Object[0]);
            model.setValueAt(NrOfOrders,NrOfOrders,0);
            model.setValueAt(c.getName(), NrOfOrders, 1);
        	NrOfOrders++;
        }
        getContentPane().setVisible(true);
	}
	DefaultTableModel model=new DefaultTableModel()
    {
		private static final long serialVersionUID = 1L;

		public Class<?> getColumnClass(int column)
        {
            switch(column)
            {
                case 0:
                    return int.class;
                case 1:
                    return String.class;

                default:
                    return String.class;
            }
        }
    };
	
	
	public void update(CompositeProduct item) {
		Orders.add(item);
		String Text = item.getName()+" was ordered";
		JOptionPane.showMessageDialog(null, Text);
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged(); 
		NrOfOrders=0;
		for(MenuItem c:Orders) {
        	model.addRow(new Object[0]);
            model.setValueAt(NrOfOrders,NrOfOrders,0);
            model.setValueAt(c.getName(), NrOfOrders, 1);
        	NrOfOrders++;
        }
	}



}
