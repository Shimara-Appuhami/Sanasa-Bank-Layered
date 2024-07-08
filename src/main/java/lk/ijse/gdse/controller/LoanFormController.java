package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.gdse.bo.custom.LoanBo;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.entity.InterestRate;
import lk.ijse.gdse.entity.Loan;
import lk.ijse.gdse.model.CustomerLoanDTO;
import lk.ijse.gdse.model.LoanDTO;
import lk.ijse.gdse.model.tm.CustomerLoanTm;
import lk.ijse.gdse.model.tm.CustomerTm;
import lk.ijse.gdse.model.tm.InterestRateTm;
import lk.ijse.gdse.model.tm.LoanTm;

import lk.ijse.gdse.repository.CustomerLoanRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanFormController {

    public JFXButton btnBack;
    public TextField txtLoanTerm;
    public TextField txtollateral;
    public TextField txtpurpose;
    public JFXComboBox cmbLoanType;
    public TableColumn colLoanTerm;
    public TableColumn colCollateral;
    public TableColumn colPurpose;
    public TextField txtCustomerId;
    public TextField txtNic;
    public TextField txtPercentage;
    public TableColumn colPercentage;
    public DatePicker txtDate;
    public JFXButton btnPrint;
    @FXML
    private TableColumn<LoanDTO, String> colApplication;

    @FXML
    private TableColumn<LoanDTO, String> colLoanAmount;

    @FXML
    private TableColumn<LoanDTO, String> colLoanId;

    @FXML
    private TableColumn<LoanDTO, String> colLoanType;

    @FXML
    private TableView<LoanTm> tblLoan;


    @FXML
    private TextField txtLoanAmount;

    @FXML
    private TextField txtLoanId;


    @FXML
    private JFXComboBox<String> cmbApplicationType;

    LoanBo loanBo= (LoanBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.LOAN);

    private final ObservableList<CustomerLoanTm> obList = FXCollections.observableArrayList();


    private void initializeComboBox() {
        ObservableList<String> applicationTypes = FXCollections.observableArrayList(
                "Approved",
                "Pending",
                "Disbursed"
        );
        cmbApplicationType.setItems(applicationTypes);
    }

    private void initializeComboBoxLoanType() {
        ObservableList<String> loanType = FXCollections.observableArrayList(
                "Housing Loan",
                "Educational Loan",
                "Personal Loans",
                "Business Loans",
                "Awrudu Loan",
                "Leasing"
        );
        cmbLoanType.setItems(loanType);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        cmbApplicationType.setValue(null);
        txtLoanAmount.clear();
        txtLoanId.clear();
        cmbLoanType.setValue(null);
        txtLoanTerm.clear();
        txtollateral.clear();
        txtpurpose.clear();
        txtCustomerId.clear();
        txtPercentage.clear();
        txtNic.clear();
        txtDate.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String loanId = txtLoanId.getText();

        if (!loanId.isEmpty()) {
            try {
                boolean isDeleted = loanBo.deleteLoan(loanId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Loan deleted successfully!").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete loan!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting loan: ").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Loan ID to delete!").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String loanId = txtLoanId.getText();
        String application = cmbApplicationType.getValue();
        String loanAmount = txtLoanAmount.getText();
        String loanType = String.valueOf(cmbLoanType.getValue());
        String loanTerm = txtLoanTerm.getText();
        String collateral = txtollateral.getText();
        String purpose = txtpurpose.getText();
        String customerId = txtCustomerId.getText();
        String percentage = txtPercentage.getText();
        String nic = txtNic.getText();
        String date= String.valueOf(txtDate.getValue());


        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setLoanId(loanId);
        loanDTO.setApplication(application);
        loanDTO.setLoanAmount(loanAmount);
        loanDTO.setLoanType(loanType);
        loanDTO.setLoanTerm(loanTerm);
        loanDTO.setCollateral(collateral);
        loanDTO.setPurpose(purpose);
        loanDTO.setCustomerId(customerId);
        loanDTO.setPercentage(Double.parseDouble(percentage));
        loanDTO.setNic(nic);
        loanDTO.setDate(date);

        try {
            DbConnection.getInstance().getConnection().setAutoCommit(false);
            if (isValied()) {
                boolean isLoanSaved = loanBo.saveLoan(loanDTO);
                if (isLoanSaved) {
                    CustomerLoanDTO customerLoanDTO = new CustomerLoanDTO(loanId, customerId);

                    List<CustomerLoanDTO> customerLoanDTOList = new ArrayList<>();
                    customerLoanDTOList.add(customerLoanDTO);

                    CustomerLoanRepo customerLoanRepo = new CustomerLoanRepo();
                    boolean isSaved = customerLoanRepo.saveCustomerLoan(customerLoanDTOList);

                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer loan details Transaction saved successfully.").show();
                        DbConnection.getInstance().getConnection().setAutoCommit(true);
                    }
                } else {
                    System.out.println("Failed to save customer loan details.");
                    DbConnection.getInstance().getConnection().rollback();
                }
                new Alert(Alert.AlertType.CONFIRMATION, "Loan saved successfully").show();
                initialize();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Loan saving failed").show();
                DbConnection.getInstance().getConnection().rollback();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving loan: "+ e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent event) {
        String loanId = txtLoanId.getText();
        String application = cmbApplicationType.getValue();
        String loanAmount = txtLoanAmount.getText();
        String loanType = String.valueOf(cmbLoanType.getValue());
        String loanTerm = txtLoanTerm.getText();
        String collateral = txtollateral.getText();
        String purpose = txtpurpose.getText();
        String customerId = txtCustomerId.getText();
        String percentage = txtPercentage.getText();
        String nic = txtNic.getText();
        LocalDate date = txtDate.getValue();

        LoanDTO loanDTO = new LoanDTO(loanId, customerId, application, loanAmount, loanType, loanTerm, collateral, purpose, Double.parseDouble(percentage), nic, date.toString());

        try {
            boolean isUpdated = loanBo.updateLoan(loanDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Loan updated successfully").show();
                initialize();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Loan update failed").show();

            }
        } catch(SQLException | ClassNotFoundException e){
                new Alert(Alert.AlertType.ERROR, "Error updating loan: " + e.getMessage()).show();
             }

    }
    public void initialize() throws ClassNotFoundException {
        setDate();
        initializeComboBoxLoanType();
        initializeComboBox();

        setCellValueFactory();
        loadAllLoans();
        txtLoanId.setText(generateNewId());
    }

    public void setCellValueFactory() {
        colLoanId.setCellValueFactory(new PropertyValueFactory<>("loan_id"));
        colApplication.setCellValueFactory(new PropertyValueFactory<>("application"));
        colLoanType.setCellValueFactory(new PropertyValueFactory<>("loan_type"));
        colLoanAmount.setCellValueFactory(new PropertyValueFactory<>("loan_amount"));
        colLoanTerm.setCellValueFactory(new PropertyValueFactory<>("loan_term"));
        colCollateral.setCellValueFactory(new PropertyValueFactory<>("collateral"));
        colPurpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        colPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));



    }

    public void loadAllLoans() throws ClassNotFoundException {
        try {
            List<LoanDTO> loanList = loanBo.getAllLoans();
            tblLoan.getItems().clear();
            for (LoanDTO loan : loanList) {
                LoanTm tm = new LoanTm(loan.getLoanId(), loan.getApplication(), loan.getLoanAmount(), loan.getLoanType(), loan.getLoanTerm(), loan.getCollateral(), loan.getPurpose(), loan.getCustomerId(), loan.getPercentage(), loan.getNic(),loan.getDate());
                tblLoan.getItems().add(tm);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading loans: " +e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic);
        String nic = txtNic.getText();


        LoanDTO loan = loanBo.searchByNic(nic);
        if (loan != null) {

            txtLoanId.setText(loan.getLoanId());
            txtCustomerId.setText(loan.getCustomerId());
            cmbApplicationType.setValue(loan.getApplication());
            txtLoanAmount.setText(loan.getLoanAmount());
            cmbLoanType.setValue(loan.getLoanType());
            txtLoanTerm.setText(loan.getLoanTerm());
            txtollateral.setText(loan.getCollateral());
            txtpurpose.setText(loan.getPurpose());
            txtPercentage.setText(String.valueOf(loan.getPercentage()));
            txtNic.setText(loan.getNic());
            txtDate.setValue(LocalDate.parse(loan.getDate()));


        } else {
            String customerId = loanBo.getCustomerId(nic);
            if (customerId != null) {
                txtCustomerId.setText(customerId);
                new Alert(Alert.AlertType.INFORMATION, "Your Customer ID is found").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not found for NIC: " + nic).show();
            }
        }





}


   /* private void setLoanData(Loan loan) {
        if (loan != null) {
            txtLoanId.setText(loan.getLoanId());
            cmbApplicationType.setValue(loan.getApplication());
            txtLoanAmount.setText(loan.getLoanAmount());
            cmbLoanType.setValue(loan.getLoanType());
            txtLoanTerm.setText(loan.getLoanTerm());
            txtollateral.setText(loan.getCollateral());
            txtpurpose.setText(loan.getPurpose());
            txtCustomerId.setText(loan.getCustomerId());
            txtPercentage.setText(String.valueOf(loan.getPercentage()));
            txtNic.setText(loan.getNic());
        } else {
            // Handle case where loan is null (not found or other error)
            System.out.println("Loan data is null.");
        }
    }
*/
    public void txtApplicationOnAction(ActionEvent event) {
    }

    public void txtLoanAmountOnAction(ActionEvent event) {
    }


    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard-form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) btnBack.getScene().getWindow(); // Use any control's scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    private String getLastCustomerId(){//limit quiry eken last eka gannawa
        List<LoanTm> tempCustomersList = new ArrayList<>(tblLoan.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getLoan_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return loanBo.generateNewLoanID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblLoan.getItems().isEmpty()) {
            return "L001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("L", "")) + 1;
            return String.format("L%03d", newCustomerId);
        }

    }

    public void txtollateralOnAction(ActionEvent event) {
    }

    public void txtpurposeOnAction(ActionEvent event) {
    }

    public void cmbLoanTypeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String type = (String) cmbLoanType.getValue();
        InterestRate loan = loanBo.searchByLoanType(type);
        if (loan != null) {

//            txtLoanId.setText(loan.getLoan_id());
//            txtCustomerId.setText(loan.getC_id());
//            cmbApplicationType.setValue(loan.getApplication());
//            txtLoanAmount.setText(loan.getLoan_amount());
//            cmbLoanType.setValue(loan.getLoan_type());
//            txtLoanTerm.setText(loan.getLoan_term());
//            txtollateral.setText(loan.getCollateral());
//            txtpurpose.setText(loan.getPurpose());
            txtPercentage.setText(String.valueOf(loan.getPercentage()));

        } else {
           // showAlert(Alert.AlertType.WARNING, "Loan Type not found for Loan: " + type);
        }


    }

    public void txtCustomerIdOnAction(ActionEvent event) throws SQLException {

    }
    public void txtNicOnKeyReleased(ActionEvent event) throws SQLException {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic);

    }

    public void txtTermOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.TERM, txtLoanTerm);
    }

    public void txtColatrellalOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME, txtollateral);
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.LOANID, txtLoanId);
    }

    public void txtAmountOnKeyReleased(KeyEvent keyEvent) {
         Regex.setTextColor(lk.ijse.gdse.Util.TextField.AMOUNT,txtLoanAmount);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.TERM, txtLoanTerm)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME, txtollateral)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.LOANID, txtLoanId)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.AMOUNT,txtLoanAmount)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.PERCENTAGE, txtPercentage)) return false;




        return true;
    }

    public void txtPercentageOnAction(ActionEvent event) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.PERCENTAGE, txtPercentage);
    }

    public void txtDateOnAction(ActionEvent event) {
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setValue(LocalDate.parse(String.valueOf(now)));
    }

    public void btnPrintOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/loan-report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void txtLoanTermOnAction(ActionEvent actionEvent) {

    }
}