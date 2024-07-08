package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.InterestRateBo;
import lk.ijse.gdse.model.InterestRateDTO;
import lk.ijse.gdse.model.tm.CustomerTm;
import lk.ijse.gdse.model.tm.InterestRateTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InterestRateFormController {


    public TableView tblInterestRate;
    public JFXButton btnBack;

    public TableColumn colLoanType;
    public JFXComboBox <String>cmbLoanType;


    InterestRateBo interestRateBo= (InterestRateBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.INTERESTRATE);
    @FXML
    private TableColumn<InterestRateDTO, String> colPercentage;

    @FXML
    private TableColumn<InterestRateDTO, String> colRateId;


    @FXML
    private TextField txtPercentage;

    @FXML
    private TextField txtRateId;

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
        txtRateId.clear();
        cmbLoanType.setValue(null);
        txtPercentage.clear();



    }
    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String rateId = txtRateId.getText();

        if (!rateId.isEmpty()) {
            try {
                boolean isDeleted = interestRateBo.deleteInterestRate(rateId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Interest Rate deleted successfully!").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Interest Rate!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting Interest Rate: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter an Interest Rate ID to delete!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String rateId = txtRateId.getText();
        String loanType = String.valueOf(cmbLoanType.getValue());
        String percentage = txtPercentage.getText();

        InterestRateDTO interestRateDTO = new InterestRateDTO(rateId, loanType, percentage);
        try {if (isValied()){
            boolean isUpdated = interestRateBo.updateInterestRate(interestRateDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Interest Rate updated").show();
                initialize();
                clearFields();
            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Interest Rate update failed").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating Interest Rate: " + e.getMessage()).show();
        }
    }
    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String loanType = cmbLoanType.getValue();


        try {
            InterestRateDTO interestRateDTO = interestRateBo.searchByNicCustomer(loanType);
            if (interestRateDTO != null) {
                txtRateId.setText(interestRateDTO.getRateId());
                txtPercentage.setText(interestRateDTO.getPercentage());

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Interest Rate not found!").show();
                initialize();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error searching Interest Rate: " + e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String rateId = txtRateId.getText();
        String loanType = String.valueOf(cmbLoanType.getValue());
        String percentage = txtPercentage.getText();


        InterestRateDTO interestRateDTO = new InterestRateDTO(rateId, loanType, percentage);

        try {if (isValied()){
            boolean isSaved = interestRateBo.saveInterestRate(interestRateDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Interest Rate saved successfully").show();

                initialize();
                clearFields();
            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to save Interest Rate").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving Interest Rate: " + e.getMessage()).show();
        }
    }
    public void initialize() throws ClassNotFoundException {
        initializeComboBoxLoanType();
        //getInquiryIds();
        generateNewId();
        setCellValueFactory();
        loadInterestRates();
        txtRateId.setText(generateNewId());

    }
    public void setCellValueFactory() {
        colRateId.setCellValueFactory(new PropertyValueFactory<>("rate_id"));
        colLoanType.setCellValueFactory(new PropertyValueFactory<>("loan_type"));
        colPercentage.setCellValueFactory(new PropertyValueFactory<>("percentage"));

    }

    private void loadInterestRates() throws ClassNotFoundException {
        ObservableList<InterestRateTm> obList = FXCollections.observableArrayList();

        try {
            List<InterestRateDTO> interestRateDTOList = interestRateBo.getAllInterestRates();
            for (InterestRateDTO interestRateDTO : interestRateDTOList) {
                InterestRateTm tm = new InterestRateTm(
                        interestRateDTO.getRateId(),
                        interestRateDTO.getLoanType(),
                        interestRateDTO.getPercentage()
                );

                obList.add(tm);
            }

            tblInterestRate.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtPercentageOnAction(ActionEvent event) {
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
        List<CustomerTm> tempCustomersList = new ArrayList<>(tblInterestRate.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getC_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return interestRateBo.generateNewInterestRateID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblInterestRate.getItems().isEmpty()) {
            return "INR001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("INR", "")) + 1;
            return String.format("INR%03d", newCustomerId);
        }

    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }
   /* private void getInquiryIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbInquiryId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

*/
    public void cmbLoanTypeOnAction(ActionEvent event) {
    }

    public void txtRateIdOnAction(ActionEvent event) {
    }
    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.INTERESTRATEID,txtRateId);
    }

    public void txtPercentageOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.PERCENTAGE,txtPercentage);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.INTERESTRATEID,txtRateId)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.PERCENTAGE,txtPercentage)) return false;

        return true;
    }
}