package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.AccountBo;
import lk.ijse.gdse.bo.custom.CustomerBo;
import lk.ijse.gdse.bo.custom.LoanBo;
import lk.ijse.gdse.model.AccountDTO;

import lk.ijse.gdse.model.tm.AccountTm;
import lk.ijse.gdse.model.tm.CustomerTm;



import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountFormController {


    public JFXButton btnBack;
    public JFXComboBox cmbCustomerId;
    public JFXComboBox<String> cmbAccountType;
    public JFXComboBox<String> cmbStatus;
    public TextField txtNic;
    public DatePicker txtOpenDate;
    @FXML
    private TableColumn<AccountDTO, String> colAccountBalance;

    @FXML
    private TableColumn<AccountDTO, String> colAccountType;

    @FXML
    private TableColumn<AccountDTO, String> colCustomerId;

    @FXML
    private TableColumn<AccountDTO, String> colOpenDate;

    @FXML
    private TableColumn<AccountDTO, String> colStatus;

    @FXML
    private TableColumn<AccountDTO, String> colaccountNo;

    @FXML
    private TableView<AccountTm> tblAccount;

    @FXML
    private TextField txtAccountBalance;
    @FXML
    private TextField txtaccountNo;

    AccountBo accountBo= (AccountBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.ACCOUNT);
    private void initializeComboBoxType() {
        ObservableList<String> accountType = FXCollections.observableArrayList(
                "Saving Account",
                "Fix Account"

        );
        cmbAccountType.setItems(accountType);
    }
    private void initializeComboBoxStatus() {
        ObservableList<String> status = FXCollections.observableArrayList(
                "Active",
                "Closed"
        );
        cmbStatus.setItems(status);
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtaccountNo.clear();
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbAccountType.getSelectionModel().clearSelection();
        txtAccountBalance.clear();
        txtOpenDate.setValue(null);
        cmbStatus.getSelectionModel().clearSelection();
        txtNic.clear();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String accountNo = txtaccountNo.getText();

        if (!accountNo.isEmpty()) {
            try {
                boolean isDeleted = accountBo.deleteAccount(accountNo);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Account deleted successfully!").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Account!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting Account: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter an Account No to delete!").show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String accountNo = txtaccountNo.getText();
        String CustomerId = String.valueOf(cmbCustomerId.getValue());
        String AccountType = cmbAccountType.getValue();
        String AccountBalance = txtAccountBalance.getText();
        String OpenDate = String.valueOf(txtOpenDate.getValue());
        String Status = cmbStatus.getValue();


        AccountDTO accountDTO = new AccountDTO(accountNo, CustomerId, AccountType, AccountBalance,OpenDate,Status);


        try {if(isValied()){
            boolean isSaved = accountBo.saveAccount(accountDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Account saved successfully").show();
                initialize();
                clearFields();
            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Account saving failed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving Account: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String accountNo = txtaccountNo.getText();
        String CustomerId = String.valueOf(cmbCustomerId.getValue());
        String AccountType = cmbAccountType.getValue();
        String AccountBalance = txtAccountBalance.getText();
        String OpenDate = String.valueOf(txtOpenDate.getValue());
        String Status = cmbStatus.getValue();


        AccountDTO accountDTO = new AccountDTO(accountNo, CustomerId, AccountType, AccountBalance,OpenDate,Status);
        try {if(isValied()){
            boolean isUpdated = accountBo.updateAccount(accountDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Account updated").show();
                initialize();
                clearFields();
            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Account update failed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating Account: " + e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String nic = txtNic.getText();


            AccountDTO accountDTO = accountBo.searchByNicAccount(nic);
            if (accountDTO != null) {
                txtaccountNo.setText(accountDTO.getANo());
                cmbCustomerId.setValue(accountDTO.getCId());
                cmbAccountType.setValue(accountDTO.getAType());
                txtAccountBalance.setText(accountDTO.getABalance());
                txtOpenDate.setValue(LocalDate.parse(accountDTO.getOpenDate()));
                cmbStatus.setValue(accountDTO.getStatus());

            } else {
                String customerId = accountBo.getCustomerId(nic);
                if (customerId != null) {
                    cmbCustomerId.setValue(customerId);
                    new Alert(Alert.AlertType.INFORMATION, "Customer ID is found").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer not found for NIC: " + nic).show();
                }
            }
    }
   /* private void setCustomerData(Account account) {
        if (account != null) {
            txtaccountNo.setText(account.getANo());
            cmbCustomerId.setValue(account.getCId());
            cmbAccountType.setValue(account.getAType());
            txtAccountBalance.setText(account.getABalance());
            txtOpenDate.setText(account.getOpenDate());
            cmbStatus.setValue(account.getStatus());

        } else {
            // No loan found, try to find related customer ID
            String customerId = LoanRepo.getCustId(nic);
            if (customerId != null) {
                cmbCustomerId.setValue(customerId);
                new Alert(Alert.AlertType.INFORMATION, "Your Customer ID is found").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not found for NIC: " + nic).show();
            }
        }

        // Reset fields and table





}*/
    public void initialize() throws SQLException, ClassNotFoundException {
        txtaccountNo.setText(generateNewId());

        getAccountIds();
        initializeComboBoxStatus();
        initializeComboBoxType();
       // generateNewId();
        setDate();
        setCellValueFactory();
        loadAllCustomers();
        addTableSelectionListener();
    }
    @FXML

    public void setCellValueFactory() throws SQLException {
        colaccountNo.setCellValueFactory(new PropertyValueFactory<>("a_no"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        colAccountType.setCellValueFactory(new PropertyValueFactory<>("a_type"));
        colAccountBalance.setCellValueFactory(new PropertyValueFactory<>("a_balance"));
        colOpenDate.setCellValueFactory(new PropertyValueFactory<>("open_date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllCustomers() throws ClassNotFoundException {
        ObservableList<AccountTm> obList = FXCollections.observableArrayList();

        try {
            List<AccountDTO> accountDTOList = accountBo.getAllAccounts();
            for (AccountDTO accountDTO : accountDTOList) {
                AccountTm tm = new AccountTm(
                        accountDTO.getANo(),
                        accountDTO.getCId(),
                        accountDTO.getAType(),
                        accountDTO.getABalance(),
                        accountDTO.getOpenDate(),
                        accountDTO.getStatus()
                );

                obList.add(tm);
            }

            tblAccount.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void txtAccountTypeOnAction(ActionEvent event) {
    }

    public void txtAccountBalanceOnAction(ActionEvent event) {

    }

    public void txtOpenDateOnAction(ActionEvent event) {

    }

    public void txtStatusOnAction(ActionEvent event) {

    }

    public void txtCustomerIDOnAction(ActionEvent event) {

    }

    private String getLastAccountId(){//limit quiry eken last eka gannawa
        List<AccountTm> tempAccountList = new ArrayList<>(tblAccount.getItems());
        Collections.sort(tempAccountList);
        return tempAccountList.get(tempAccountList.size() - 1).getC_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return accountBo.generateNewAccountId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblAccount.getItems().isEmpty()) {
            return "A001";
        } else {
            String id = getLastAccountId();
            int newCustomerId = Integer.parseInt(id.replace("A", "")) + 1;
            return String.format("A%03d", newCustomerId);
        }

    }
    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtOpenDate.setValue(LocalDate.parse(String.valueOf(now)));
    }
    private void getAccountIds() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = accountBo.getIdsAccount();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void txtAccountNoOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ACCOUNTID,txtaccountNo);
    }

    public void txtOpenDateOnKeyReleased(KeyEvent keyEvent) {
       // Regex.setTextColor(lk.ijse.gdse.Util.TextField.DATE,txtOpenDate);
    }

    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNic);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.ACCOUNTID,txtaccountNo)) return false;
      //  if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.DATE,txtOpenDate)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNic)) return false;

        return true;
    }
    private void addTableSelectionListener() {
        tblAccount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                AccountTm selectedAccount = newValue;
                txtaccountNo.setText(selectedAccount.getA_no());
                cmbCustomerId.setValue(selectedAccount.getC_id());
                cmbAccountType.setValue(selectedAccount.getA_type());
                txtAccountBalance.setText(selectedAccount.getA_balance());
                txtOpenDate.setValue(LocalDate.parse(selectedAccount.getOpen_date()));
                cmbStatus.setValue(selectedAccount.getStatus());
            }
        });
    }
}