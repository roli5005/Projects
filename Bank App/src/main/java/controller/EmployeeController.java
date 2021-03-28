package controller;

import model.Account;
import model.Activity;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validator.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.EmployeeView;
import view.ProcessBillView;
import view.TransferView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;


public class EmployeeController {

    private EmployeeView employeeView;
    private final ClientService clientServiceMySQL;
    private final AccountService accountServiceMySQL;
    private final ActivityService activityServiceMySQL;
    private long employeeID;

    public long getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(long id){
        employeeID = id;
    }

    public EmployeeController(EmployeeView employeeView, ClientService clientService, AccountService accountServiceMySQL, ActivityService activityServiceMySQL , long employeeID){

        this.employeeView = employeeView;
        this.clientServiceMySQL = clientService;
        this.accountServiceMySQL = accountServiceMySQL;
        this.activityServiceMySQL = activityServiceMySQL;
        this.SetButtons();
    }

    public void Update(){
        SetButtons();
    }

    public void SetButtons(){
        employeeView.setAddClientButtonListener(new AddClientBtnListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientBtnListener());
        employeeView.setViewClientButtonListener(new ViewClientBtnListener());
        employeeView.setCreateAccountButtonListener(new CreateAccountBtnListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountBtnListener());
        employeeView.setViewAccountButtonListener(new ViewAccountBtnListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountBtnListener());
        employeeView.setLogOutButtonListener(new LogOutBtnListener());
        employeeView.setTransferButtonListener(new TransferMoneyBtnListener());
        employeeView.setProcessBillButtonListener(new ProcessBillsBtnListener());
        employeeView.setVisible();
    }

    private class AddClientBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.clientNameField.getText();
            String card = clientServiceMySQL.generateCardNumber();
            String personalCode = employeeView.clientPersonalNumberField.getText();
            String address = employeeView.clientAddressField.getText();
            String phone = employeeView.clientPhoneNumberField.getText();

            Client client = new ClientBuilder().setName(name)
                    .setPhoneNumber(phone)
                    .setAddress(address)
                    .setCardNumber(card)
                    .setPersonalNumber(personalCode)
                    .build();

            Notification<Client> clientNotification = clientServiceMySQL.addClient(client);

            if (clientNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client added successfully!");

                String[] clientsId = clientServiceMySQL.getRepository().findAllCardNumbers();
                String[] accountsList = accountServiceMySQL.getRepository().findAllIDs();

                employeeView.dispose();
                employeeView = new EmployeeView(clientsId,accountsList);
                Update();

            }

        }
    }

    private class UpdateClientBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getSelectedClientId() == null) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select client!");
            } else {
                String clientCard = employeeView.getSelectedClientNumber();
                String name = employeeView.clientNameField.getText();
                String card = employeeView.clientCardNumberField.getText();
                String personalCode = employeeView.clientPersonalNumberField.getText();
                String address = employeeView.clientAddressField.getText();
                String phone = employeeView.clientPhoneNumberField.getText();

                Client client = new ClientBuilder()
                        .setId(clientServiceMySQL.getRepository().findByCardNumber(clientCard).getId())
                        .setName(name)
                        .setPhoneNumber(phone)
                        .setAddress(address)
                        .setCardNumber(card)
                        .setPersonalNumber(personalCode)
                        .build();
                Notification<Client> clientNotification = clientServiceMySQL.updateClient(client);

                if (clientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated successfully!");
                }
            }
        }
    }

    private class ViewClientBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(employeeView.getSelectedClientId()==null){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select client ID");
            }
            else {
                String clientCard = employeeView.getSelectedClientNumber();
                Long id = clientServiceMySQL.getRepository().findByCardNumber(clientCard).getId();
                Client client = clientServiceMySQL.viewClient(id);
                employeeView.clientNameField.setText(client.getName());
                employeeView.clientCardNumberField.setText(client.getIdentity_card_number());
                employeeView.clientPhoneNumberField.setText(client.getPhone_number());
                employeeView.clientAddressField.setText(client.getAddress());
                employeeView.clientPersonalNumberField.setText(client.getPersonal_nr_code());
            }
        }
    }

    private class CreateAccountBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> accountNotification = new Notification<>();

            if(employeeView.getSelectedClientId()==null){
                accountNotification.addError("Please select client!");
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            }
            else {
                String clientCard = employeeView.getSelectedClientNumber();
                Long id = clientServiceMySQL.getRepository().findByCardNumber(clientCard).getId();
                String type = employeeView.accountTypeField.getText();
                float balance = Float.parseFloat(employeeView.accountBalanceField.getText());
                String identificationNumber = accountServiceMySQL.generateIdentificationNumber();
                Date dateOfCreation = Date.valueOf(LocalDate.now());
                Account newAccount = new AccountBuilder().setClientId(id)
                        .setBalance(balance)
                        .setDateOfCreation(dateOfCreation)
                        .setType(type)
                        .setIdentificationNumber(identificationNumber)
                        .build();


                accountNotification = accountServiceMySQL.createAccount(newAccount);

                if (accountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created successfully!");

                    String[] accountsId = accountServiceMySQL.getRepository().findAllIDs();
                    String[] clientsList = clientServiceMySQL.getRepository().findAllCardNumbers();

                    employeeView.dispose();
                    employeeView = new EmployeeView(clientsList,accountsId);
                    Update();
                }
            }


        }
    }

    private class UpdateAccountBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Account> accountNotification = new Notification<>();

            if(employeeView.getSelectedAccountIdNumber()==null){
                accountNotification.addError("Please select account!");
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
            }
            else{
                String identificationNumber = employeeView.getSelectedAccountIdNumber();
                Account account = accountServiceMySQL.getRepository().findByIdentificationNumber(identificationNumber);
                float balance = Float.parseFloat(employeeView.accountBalanceField.getText());

                account.setBalance(balance);

                accountNotification = accountServiceMySQL.updateAccount(account);

                if(accountNotification.hasErrors()){
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), accountNotification.getFormattedErrors());
                }
                else
                {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account updated successfully!");
                }

            }
        }
    }

    private class ViewAccountBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(employeeView.getSelectedAccountIdNumber()==null){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select account ID");
            }
            else {
                String idNumber = employeeView.getSelectedAccountIdNumber();
                Long accountID = accountServiceMySQL.getRepository().findByIdentificationNumber(idNumber).getId();
                Account account = accountServiceMySQL.viewAccount(accountID);

                employeeView.accountBalanceField.setText( ""+account.getBalance());
                employeeView.accountTypeField.setText(account.getType());
                employeeView.accountIdentityField.setText(account.getIdentification_number());
                employeeView.accountDateField.setText(account.getDate_of_creation().toString());
            }

        }
    }

    private class DeleteAccountBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(employeeView.getSelectedAccountIdNumber()==null){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please select account!");
            }
            else {
                String idNumber = employeeView.getSelectedAccountIdNumber();
                Long accountID = accountServiceMySQL.getRepository().findByIdentificationNumber(idNumber).getId();
                accountServiceMySQL.deleteAccount(accountID);

                String[] clients = clientServiceMySQL.getRepository().findAllCardNumbers();

                if(accountServiceMySQL.getRepository().findAllIDs()!=null) {
                    String[] accountsId = accountServiceMySQL.getRepository().findAllIDs();
                    employeeView.dispose();
                    employeeView = new EmployeeView(clients, accountsId);
                    Update();
                }
                else{
                    employeeView.dispose();
                    employeeView = new EmployeeView(clients, new String[0]);
                    Update();
                }


            }
        }
    }

    private class TransferMoneyBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] accounts = accountServiceMySQL.getRepository().findAllAccountNumbers();
            TransferView transferView = new TransferView(accounts,accounts);

            String fromAccountNumber = transferView.getClientOptions();
            String toAccountNumber = transferView.getClientOptions2();
            float amount = Float.parseFloat(transferView.getAmount());

            Account fromAccount = accountServiceMySQL.getRepository().findByIdentificationNumber(fromAccountNumber);
            fromAccount.setBalance(fromAccount.getBalance()-amount);
            accountServiceMySQL.updateAccount(fromAccount);

            Account toAccount = accountServiceMySQL.getRepository().findByIdentificationNumber(toAccountNumber);
            toAccount.setBalance(toAccount.getBalance()+amount);
            accountServiceMySQL.updateAccount(toAccount);

            Activity activity = new Activity();
            activity.setEmployee_id(employeeID);
            activity.setAction("Transaction");
            activity.setDate(Date.valueOf(LocalDate.now()));
            activity.setDetails("to account with Identification Number "+toAccountNumber);
            activity.setAmount_of_money(amount);

            if(activityServiceMySQL.addActivity(activity).hasErrors()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Something went wrong !");
            }
            else
            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transaction completed!");

        }
    }

    private class ProcessBillsBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] accounts = accountServiceMySQL.getRepository().findAllAccountNumbers();
            ProcessBillView processBillView = new ProcessBillView(accounts);

            String accountNumber = processBillView.getAccount();
            String utility = processBillView.getDetail();
            float amount =  Float.parseFloat(processBillView.getAmount());
            Account account = accountServiceMySQL.getRepository().findByIdentificationNumber(accountNumber);
            account.setBalance( account.getBalance()-amount);
            accountServiceMySQL.updateAccount(account);

            Activity activity = new Activity();
            activity.setEmployee_id(employeeID);
            activity.setAmount_of_money(amount);
            activity.setDate(Date.valueOf(LocalDate.now()));
            activity.setAction("Utility Bill");
            activity.setDetails(utility);

            if(activityServiceMySQL.addActivity(activity).hasErrors()){
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Something went wrong !");
            }
            else
            JOptionPane.showMessageDialog(employeeView.getContentPane(), "Bill processed successfully !");
        }
    }

    private class LogOutBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeID = -1L;
        }
    }

    public EmployeeView getEmployeeView(){return employeeView;}

}

