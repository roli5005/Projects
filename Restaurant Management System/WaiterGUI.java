package PresentationLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Observable;
import BusinessLayer.Observer;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;

public class WaiterGUI extends JFrame implements Observable{
	Restaurant restaurant;
	List<Order> orders = new ArrayList<Order>();
	final JTable table=new JTable();
    final JTable table2=new JTable();
    private Observer obs;
    JButton btn=new JButton("Create Order");
    JButton btn1=new JButton("Create Bill");
    JButton refr = new JButton("Refresh");
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    //CONSTRUCTOR
    public WaiterGUI(Restaurant restaurant)
    {
    	this.restaurant = restaurant;
        //the form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200,200,800,400);
        setTitle("Waiter Window");
        getContentPane().setLayout(null);
        JScrollPane scroll=new JScrollPane();
        scroll.setBounds(30,120,300,200);
        getContentPane().add(scroll); 
        JScrollPane scroll2=new JScrollPane();
        scroll2.setBounds(350,120,300,200);
        getContentPane().add(scroll2);
        scroll.setViewportView(table);
        scroll2.setViewportView(table2);
        init();
        
    }
    public void init() {
    	table.setModel(model);
        model.addColumn("Select");
        model.addColumn("Menu Item");
        model.addColumn("Price");
        int i=0;
        for(MenuItem c:restaurant.Menu) {
        	model.addRow(new Object[0]);
            model.setValueAt(false,i,0);
            model.setValueAt(c.getName(), i, 1);
            model.setValueAt(c.computePrice()+" Lei", i, 2);
        	i++;
        }
        table2.setModel(model2);   
        model2.addColumn("Select");
        model2.addColumn("Order");
        model2.addColumn("Table");
        model2.addColumn("Total Price");

        //ADD BUTTONs TO FORM
        btn.setBounds(30,30,130,30);
        btn1.setBounds(350, 30, 130, 30);
        refr.setBounds(190,30,130,30);
        btn.addActionListener(new CreateOrders());
        btn1.addActionListener(new CreateBill());
        refr.addActionListener(new Update());
        getContentPane().add(btn);
        getContentPane().add(btn1);
        getContentPane().add(refr);
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
  //TABEL FOR THE ORDERS
    DefaultTableModel model2=new DefaultTableModel()
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
                    return int.class;
                case 3:
                	return int.class;

                default:
                    return String.class;
            }
        }
    };
    public void updateMenu() {
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
    public void updateOrders() {
    	DefaultTableModel dm = (DefaultTableModel)table2.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		int i=0;
		for(Order x:orders) {
			dm.addRow(new Object[0]);
            dm.setValueAt(false,i,0);
            dm.setValueAt(x.hashCode(), i, 1);
            dm.setValueAt(x.getTable(), i, 2);
            float price = getTotalPrice(restaurant.Orders.get(x));
            dm.setValueAt(price,i,3);
            i++;
		}
    }
    public float getTotalPrice(Collection<MenuItem> orderlist) {
    	float price=0;
    	for(MenuItem x:orderlist)
    		price+=x.computePrice();
    	return price;
    }
    public class CreateOrders implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	 // TODO Auto-generated method stub
        	
        	Collection<MenuItem> Order = new ArrayList<MenuItem>();
            //GET SELECTED ROW
            for(int i=0;i<table.getRowCount();i++)
            {
                Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
                String col=table.getValueAt(i, 1).toString()+" selected";
                //DISPLAY
                if(checked)
                {
                    JOptionPane.showMessageDialog(null, col);
                    Order.add(restaurant.Menu.get(i));
                    if(restaurant.Menu.get(i).getClass().getName()=="BusinessLayer.CompositeProduct")
                    	notifyObserver(restaurant.Menu.get(i));
                }
            }
            Order ord = new Order();
            restaurant.Orders.put(ord, Order);
            orders.add(ord);
            updateOrders();
        }
	}
    public class Update implements ActionListener{
        public void actionPerformed(ActionEvent e){
        updateMenu();
        updateOrders();
        }
	}
    public class CreateBill implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	for(int i=0;i<table2.getRowCount();i++)
            {
        Boolean checked=Boolean.valueOf(table2.getValueAt(i, 0).toString());
        	if(checked) {
        		Order ord = orders.get(i);
        		orders.remove(i);
        		updateOrders();
        		restaurant.createBill(ord);
        	}
            }
        
        }
	}
	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		obs = o;
		
	}

	@Override
	public void removeObserver() {
		// TODO Auto-generated method stub
		obs = null;
	}

	@Override
	public void notifyObserver(MenuItem item) {
		// TODO Auto-generated method stub
		obs.update((CompositeProduct)item);
	}
	
    

}

