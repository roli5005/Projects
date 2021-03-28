package PresentationLayer;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;


import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;

public class AdminGUI  extends JFrame{
	
	Restaurant restaurant;

    JButton btn1=new JButton("Add Menu Item");
    JButton btn2=new JButton("Delete Menu Item");
    JButton btn3=new JButton("Edit Menu Item");
    
    JTextField ItemName = new JTextField(40);
    JTextField ItemPrice = new JTextField(6);
    final JTable table=new JTable();
    JScrollPane scroll=new JScrollPane();
    JLabel NameLabel = new JLabel("ITEM NAME");
    JLabel PriceLabel = new JLabel("ITEM PRICE");
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AdminGUI(Restaurant rest){
		restaurant = rest;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setBounds(200,200,600,400);
        setTitle("Admin Window");
        getContentPane().setLayout(null);        
        scroll.setBounds(30,120,500,200);
        getContentPane().add(scroll); 
        scroll.setViewportView(table);
        //set the model
        table.setModel(model);
        table.setFont(new FontUIResource(new Font("Calibri", 0, 18)));    
        model.addColumn("Select");
        model.addColumn("Menu Item");
        model.addColumn("Price");   
        //init the table
        int i=0;
        for(MenuItem c:restaurant.Menu) {
        	model.addRow(new Object[0]);
            model.setValueAt(false,i,0);
            model.setValueAt(c.getName(), i, 1);
            model.setValueAt(c.computePrice()+" Lei", i, 2);
        	i++;
        } 
       
        addItems();
	}
	public void addItems() {
		ItemName.setBounds(150, 80, 150, 30);
        ItemPrice.setBounds(430,80,50,30);
        btn1.setBounds(30,30,130,30);
        btn2.setBounds(190, 30, 150, 30);
        btn3.setBounds(370, 30, 130, 30);

        NameLabel.setFont(new FontUIResource(new Font("Calibri", 0, 18)));
        NameLabel.setBounds(50, 80, 100, 30);
        PriceLabel.setFont(new FontUIResource(new Font("Calibri", 0, 18)));
        PriceLabel.setBounds(330, 80, 100, 30);
        
        btn1.addActionListener(new AddItem());
        btn3.addActionListener(new EditItem());
        btn2.addActionListener(new DeleteItem());
        getContentPane().add(btn1);
        getContentPane().add(btn2);
        getContentPane().add(btn3);
        getContentPane().add(ItemName);
        getContentPane().add(ItemPrice);
        getContentPane().add(NameLabel);
        getContentPane().add(PriceLabel);
	}
	//TABLE MODEL FOR THE MENU
    DefaultTableModel model=new DefaultTableModel()
    {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Class<?> getColumnClass(int column)
        {
            switch(column)
            {
                case 0:
                    return Boolean.class;
                case 1:
                    return String.class;
                case 2:
                    return String.class;

                default:
                    return String.class;
            }
        }
    };
	
	public void updateTable() {
		DefaultTableModel dm = (DefaultTableModel)table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged(); 
		int i=0;
        for(MenuItem c:restaurant.Menu) {
        	dm.addRow(new Object[0]);
            dm.setValueAt(false,i,0);
            dm.setValueAt(c.getName(), i, 1);
            dm.setValueAt(c.computePrice()+" Lei", i, 2);
        	i++;
        }
	}
	
	public class AddItem implements ActionListener{
        public void actionPerformed(ActionEvent e){
        String Name = ItemName.getText();
        float price = Float.parseFloat( ItemPrice.getText());
        if(restaurant.ItemExists(Name)!=1)
        {restaurant.addToMenu(Name, price);
        updateTable();}
        }
	}
	
	public class EditItem implements ActionListener{
		public void actionPerformed(ActionEvent e){
        	for(int i=0;i<table.getRowCount();i++)
            {
        Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
        	if(checked) {
        		restaurant.editMenu(table.getValueAt(i, 1).toString(),Float.parseFloat(ItemPrice.getText()));
        	}
            }
        updateTable();
        }
	}
	public class DeleteItem implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	for(int i=0;i<table.getRowCount();i++)
            {
        Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
        	if(checked) {
        		restaurant.removeFromMenu(table.getValueAt(i, 1).toString());
        	}
            }
        updateTable();
        }
	}

}
