package view;

import javax.swing.*;
import java.util.Objects;

public class ProcessBillView extends JFrame {

    private final JTextField amount = new JTextField();
    private final JTextField detail = new JTextField();
    private JComboBox<String> accountLists;

    public ProcessBillView(String[] accounts){
        accountLists = new JComboBox<>(accounts);
        Object[] BillBox = {
                "Account:", accountLists,
                "Amount of money:",amount,
                "Utility:", detail
        };
        JOptionPane.showConfirmDialog(this,BillBox, "Transfer", JOptionPane.OK_CANCEL_OPTION);
    }
    public String getAmount() {
        return amount.getText();
    }

    public String getDetail() {
        return detail.getText();
    }

    public String getAccount() {
        return Objects.requireNonNull(accountLists.getSelectedItem()).toString();
    }
}
