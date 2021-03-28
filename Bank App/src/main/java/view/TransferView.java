package view;

import javax.swing.*;

public class TransferView extends JFrame{

        private final JTextField amount = new JTextField();
        private JComboBox<String> ClientOptions;
        private JComboBox<String> ClientOptions2;

        public TransferView(String[] options1List,String[] options2List){
        ClientOptions = new JComboBox<>(options1List);
        ClientOptions2 = new JComboBox<>(options2List);
            Object[] TransferBox = {
                    "Account:", ClientOptions,
                    "Amount of money:",amount,
                    "Account:", ClientOptions2
            };
            JOptionPane.showConfirmDialog(this,TransferBox, "Transfer", JOptionPane.OK_CANCEL_OPTION);

        }

    public String getAmount() {
        return amount.getText();
    }

    public String getClientOptions() {
        return ClientOptions.getSelectedItem().toString();
    }

    public String getClientOptions2() {
        return ClientOptions2.getSelectedItem().toString();
    }





}
