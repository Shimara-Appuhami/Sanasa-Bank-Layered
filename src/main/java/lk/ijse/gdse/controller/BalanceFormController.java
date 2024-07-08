package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.BalanceBo;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.model.BalanceDTO;
import lk.ijse.gdse.model.tm.BalanceTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class BalanceFormController {

    public JFXButton btnBack;
    public TableColumn colBalanceId;
    public TextField txtBalanceId;
    public DatePicker txtLastUpdatedDate;


    public TextField txtNic;
    public TableColumn colNic;
    public TextField txtLoanId;
    @FXML

    private TableColumn<BalanceDTO, String> colPrincipalBalance;

    @FXML
    private TableColumn<BalanceDTO, String> colInterestBalance;

    @FXML
    private TableColumn<BalanceDTO, String> colTotalBalance;

    @FXML
    private TableColumn<BalanceDTO, String> colLastUpdatedDate;

    @FXML
    private TableView<BalanceTm> tblBalance;


    @FXML
    private TextField txtPrincipalBalance;

    @FXML
    private TextField txtInterestBalance;

    @FXML
    private TextField txtTotalBalance;
    BalanceBo balanceBo= (BalanceBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.BALANCE);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtBalanceId.clear();
        txtLoanId.clear();
        txtNic.clear();
        txtPrincipalBalance.clear();
        txtInterestBalance.clear();
        txtTotalBalance.clear();
        txtLastUpdatedDate.setValue(null);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String bId = txtBalanceId.getText();

        if (!bId.isEmpty()) {
            try {
                boolean isDeleted = balanceBo.deleteBalance(bId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Balance deleted successfully!").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete balance!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting balance: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Balance ID to delete!").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String bId = txtBalanceId.getText();
        String principalBalance = txtPrincipalBalance.getText();
        String interestBalance = txtInterestBalance.getText();
        String totalBalance = txtTotalBalance.getText();
        String lastUpdatedDate = String.valueOf(txtLastUpdatedDate.getValue());
        String loanId=txtLoanId.getText();

        BalanceDTO balanceDTO = new BalanceDTO(bId, loanId,principalBalance, interestBalance, totalBalance, lastUpdatedDate);

        try {
            if (isValied()) {
                boolean isSaved = balanceBo.saveBalance(balanceDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Balance saved successfully").show();
                    initialize();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Balance saving failed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving Balance: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String bId = txtBalanceId.getText();

        String principalBalance = txtPrincipalBalance.getText();
        String interestBalance = txtInterestBalance.getText();
        String totalBalance = txtTotalBalance.getText();
        String lastUpdatedDate = String.valueOf(txtLastUpdatedDate.getValue());
        String loanId=txtLoanId.getText();

        BalanceDTO balanceDTO = new BalanceDTO(bId, loanId,principalBalance, interestBalance, totalBalance, lastUpdatedDate);
        try {
            if (isValied()) {
                boolean isUpdated = balanceBo.updateBalance(balanceDTO);

                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Balance updated").show();
                    initialize();
                    clearFields();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Balance update failed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating Balance: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic);
        String nic = txtNic.getText();


            BalanceDTO balance = balanceBo.searchByNicBalance(nic);
            if (balance != null) {
                // calculateBalances();
                txtBalanceId.setText(balance.getBId());
                //txtNic.setText(balance.getNic());
                txtPrincipalBalance.setText(balance.getPrincipalBalance());
                txtInterestBalance.setText(balance.getInterestBalance());
                txtTotalBalance.setText(balance.getTotalBalance());
                txtLastUpdatedDate.setValue(LocalDate.parse(balance.getLastUpdatedDate()));
                txtLoanId.setText(balance.getLoanId());

            } else {
                String amount = balanceBo.getAmount(nic);
                String payBalance=balanceBo.getPayBalance(nic);
                String totalBalance=balanceBo.getTotalBalance(nic);
                String lastUpdatedDate=balanceBo.getLastPaymentDate(nic);
                String loanId=balanceBo.getLoanId(nic);
                if (balance==null) {
                    txtPrincipalBalance.setText(amount);
                    txtInterestBalance.setText(payBalance);
                    txtTotalBalance.setText(totalBalance);
                    txtLastUpdatedDate.setValue(LocalDate.parse(lastUpdatedDate));
                    txtLoanId.setText(loanId);
                    new Alert(Alert.AlertType.INFORMATION, "Customer Data Calculated").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "balance not found for NIC: " + nic).show();
                }


        }
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        generateNewId();
        setCellValueFactory();
        loadAllBalances();
        txtBalanceId.setText(generateNewId());
    }

    public void setCellValueFactory() throws SQLException {
        colBalanceId.setCellValueFactory(new PropertyValueFactory<>("b_id"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colPrincipalBalance.setCellValueFactory(new PropertyValueFactory<>("principal_balance"));
        colInterestBalance.setCellValueFactory(new PropertyValueFactory<>("interest_balance"));
        colTotalBalance.setCellValueFactory(new PropertyValueFactory<>("total_balance"));
        colLastUpdatedDate.setCellValueFactory(new PropertyValueFactory<>("last_updated_date"));
    }

    private void loadAllBalances() throws ClassNotFoundException {
        ObservableList<BalanceTm> obList = FXCollections.observableArrayList();

        try {
            List<BalanceDTO> balanceList = balanceBo.getAllBalances();
            for (BalanceDTO balance : balanceList) {
                BalanceTm tm = new BalanceTm(
                        balance.getBId(),
                        balance.getLoanId(),
                        balance.getPrincipalBalance(),
                        balance.getInterestBalance(),
                        balance.getTotalBalance(),
                        balance.getLastUpdatedDate()
                );
                obList.add(tm);
            }

            tblBalance.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard-form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    public void txtLastUpdatedDateOnAction(ActionEvent event) {
    }

    public void txtPrincipalBalanceOnAction(ActionEvent event) {
    }

    public void txtInterestBalanceOnAction(ActionEvent event) {
    }

    public void txtTotalBalanceOnAction(ActionEvent event) {
    }

    private String getLastCustomerId(){//limit quiry eken last eka gannawa
        List<BalanceTm> tempCustomersList = new ArrayList<>(tblBalance.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getB_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return balanceBo.generateNewBalanceId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblBalance.getItems().isEmpty()) {
            return "B001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("B", "")) + 1;
            return String.format("B%03d", newCustomerId);
        }

    }
    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }

    public void txtBalanceIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.BALANCEID, txtBalanceId);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.BALANCEID, txtBalanceId)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic)) return false;


        return true;
    }

    public void txtBalanceIdOnAction(ActionEvent event) {
    }

    void calculateBalances(ActionEvent event) {
        // try {
        //  String nic = txtNic.getText();
        // Get loan amount and interest balance from the repository
        //double loanAmount = BalanceRepo.calculatePaymentAmount(nic);
        //  double interestBalance = BalanceRepo.calculatePaymentAmount(nic);

        // Calculate principal balance and total balance
//            double principalBalance = loanAmount - interestBalance;
//            double totalBalance = principalBalance + interestBalance;
//
//            // Set calculated values to respective text fields
//            txtPrincipalBalance.setText(String.valueOf(principalBalance));
//            txtInterestBalance.setText(String.valueOf(interestBalance));
//            txtTotalBalance.setText(String.valueOf(totalBalance));
//        } catch (NumberFormatException e) {
//            showAlert(Alert.AlertType.ERROR, "Invalid input. Please enter valid numbers.");
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error calculating balances: " + e.getMessage());
//        }
//    }
    }

    public void txtLoanIdOnAction(ActionEvent event) {
    }

    @FXML
    void btnPrintOnAction(ActionEvent actionEvent) {
        printReport();
    }

    private void printReport() {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/balance-report" +
                    ".jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("p_txtNic", txtNic.getText());
            parameters.put("p_txtPrincipalBalance", txtPrincipalBalance.getText());
            parameters.put("p_txtInterestBalance", txtInterestBalance.getText());
            parameters.put("p_txtTotalBalance", txtTotalBalance.getText());
            parameters.put("p_txtLastUpdatedDate", String.valueOf(txtLastUpdatedDate.getValue()));

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DbConnection.getInstance().getConnection());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}