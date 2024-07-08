package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.CustomerBo;
import lk.ijse.gdse.db.DbConnection;
import lk.ijse.gdse.model.CustomerDTO;
import lk.ijse.gdse.model.tm.CustomerTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerFormController {

    public JFXButton btnBack;
    public TextField txtNic;
    public TableColumn<CustomerTm, String> colNic;
    public DatePicker txtBirth;
    public DatePicker txtRegistration;
    public JFXButton btnPrintBill;
    public JFXButton btnClear;
    public Label imgPhotoOnAction;
    public Rectangle imgPhoto;

    @FXML
    private TableColumn<CustomerTm, String> colAddress;
    @FXML
    private TableColumn<CustomerTm, String> colAge;
    @FXML
    private TableColumn<CustomerTm, String> colBirth;
    @FXML
    private TableColumn<CustomerTm, String> colContact;
    @FXML
    private TableColumn<CustomerTm, String> colEmail;
    @FXML
    private TableColumn<CustomerTm, String> colId;
    @FXML
    private TableColumn<CustomerTm, String> colIncome;
    @FXML
    private TableColumn<CustomerTm, String> colName;
    @FXML
    private TableColumn<CustomerTm, String> colRegistration;
    @FXML
    private TableView<CustomerTm> tblCustomer;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtIncome;
    @FXML

    private TextField txtName;

    CustomerBo customerBo = (CustomerBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.CUSTOMER);

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtContact.clear();
        txtAddress.clear();
        txtAge.clear();
        txtBirth.setValue(null);
        txtNic.clear();
        txtRegistration.setValue(null);
        txtIncome.clear();
        txtId.clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        if (!id.isEmpty()) {
            try {
                boolean isDeleted = customerBo.deleteCustomer(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted successfully!").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Customer!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Error deleting Customer: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Customer ID to delete!").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String age = txtAge.getText();
        String birth = String.valueOf(txtBirth.getValue());
        String nic = txtNic.getText();
        String registration = String.valueOf(txtRegistration.getValue());
        String income = txtIncome.getText();

        CustomerDTO customerDTO = new CustomerDTO(id, name, email, contact, address, age, birth, nic, registration, income);

        try {
            if (isValid()) {
                boolean isSaved = customerBo.saveCustomer(customerDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Customer saving failed").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving Customer: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();
        String age = txtAge.getText();
        String birth = String.valueOf(txtBirth.getValue());
        String nic = txtNic.getText();
        String registration = String.valueOf(txtRegistration.getValue());
        String income = txtIncome.getText();

        CustomerDTO customerDTO = new CustomerDTO(id, name, email, contact, address, age, birth, nic, registration, income);

        try {
            if (isValid()) {
                boolean isUpdated = customerBo.updateCustomer(customerDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer updated").show();
                    initialize();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Customer update failed").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating Customer: " + e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String nic = txtNic.getText();

        if (nic == null || nic.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "NIC field cannot be empty!").show();
            return;
        }

        try {
            CustomerDTO customerDTO = customerBo.searchByNicCustomer(nic);

            if (customerDTO != null) {
                setCustomerData(customerDTO);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "No customer found for NIC: " + nic).show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Error searching customer: " + e.getMessage()).show();
        }
    }


    private void setCustomerData(CustomerDTO customerDTO) {
        if (customerDTO != null) {
            txtId.setText(customerDTO.getCId());
            txtName.setText(customerDTO.getCName());
            txtEmail.setText(customerDTO.getCEmail());
            txtContact.setText(customerDTO.getCContact());
            txtAddress.setText(customerDTO.getCAddress());
            txtAge.setText(customerDTO.getCAge());
            txtBirth.setValue(LocalDate.parse(customerDTO.getDateOfBirth()));

            txtRegistration.setValue(LocalDate.parse(customerDTO.getRegistrationDate()));
            txtIncome.setText(customerDTO.getAnnualIncome());
            txtNic.setText(customerDTO.getNic());
        } else {
            System.out.println("Customer data is null.");
        }
    }

    public void initialize() throws SQLException, ClassNotFoundException {
        txtId.setText(generateNewId());
        setDate();
        setCellValueFactory();
        loadAllCustomers();
        addTableSelectionListener();
    }

    public void setCellValueFactory() throws SQLException {
        colId.setCellValueFactory(new PropertyValueFactory<>("c_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("c_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("c_email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("c_contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("c_address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("c_age"));
        colBirth.setCellValueFactory(new PropertyValueFactory<>("date_of_birth"));
        colRegistration.setCellValueFactory(new PropertyValueFactory<>("registration_date"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colIncome.setCellValueFactory(new PropertyValueFactory<>("annual_income"));

    }

    private void loadAllCustomers() throws ClassNotFoundException {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDTO> customerDTOList = customerBo.getAllCustomers();
            for (CustomerDTO customerDTO : customerDTOList) {
                CustomerTm tm = new CustomerTm(
                        customerDTO.getCId(),
                        customerDTO.getCName(),
                        customerDTO.getCEmail(),
                        customerDTO.getCContact(),
                        customerDTO.getCAddress(),
                        customerDTO.getCAge(),
                        customerDTO.getDateOfBirth(),

                        customerDTO.getRegistrationDate(),
                        customerDTO.getAnnualIncome(),
                        customerDTO.getNic()

                        );
                obList.add(tm);
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtRegistrationOnAction(ActionEvent event) {
    }

    public void txtIncomeOnAction(ActionEvent event) {
    }

    public void txtInquiryIdOnAction(ActionEvent event) {
    }

    public void txtNicOnAction(ActionEvent event) {
    }

    public void txtAddressOnAction(ActionEvent event) {
    }

    public void txtBirthOnAction(ActionEvent event) {
    }

    private String getLastCustomerId() {
        List<CustomerTm> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getC_id();
    }

    private String generateNewId() {
        try {
            return customerBo.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblCustomer.getItems().isEmpty()) {
            return "C001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        }
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtRegistration.setValue(LocalDate.parse(String.valueOf(now)));
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME, txtName);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.EMAIL, txtEmail);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.CONTACT, txtContact);
    }

    public void txtAgeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.AGE, txtAge);
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID, txtId);
    }

    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic);
    }

    public boolean isValid() {
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.ID, txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME, txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.EMAIL, txtEmail)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.AGE, txtAge)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC, txtNic)) return false;
        return true;
    }

    public void txtRegisterDateOnKeyReleased(KeyEvent keyEvent) {
    }

    public void btnPrintBillOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/customer-report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }

    private void addTableSelectionListener() {
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CustomerTm selectedCustomer = newValue;
                txtId.setText(selectedCustomer.getC_id());
                txtName.setText(selectedCustomer.getC_name());
                txtEmail.setText(selectedCustomer.getC_email());
                txtContact.setText(selectedCustomer.getC_contact());
                txtAddress.setText(selectedCustomer.getC_address());
                txtAge.setText(selectedCustomer.getC_age());
                txtBirth.setValue(LocalDate.parse(selectedCustomer.getDate_of_birth()));
                txtRegistration.setValue(LocalDate.parse(selectedCustomer.getRegistration_date()));
                txtIncome.setText(selectedCustomer.getAnnual_income());
                txtNic.setText(selectedCustomer.getNic());

            }
        });
    }
    public void txtEmailOnAction(ActionEvent event) {
    }

    public void txtContactOnAction(ActionEvent actionEvent) {
        
    }

    public void txtAgeOnAction(ActionEvent actionEvent) {
    }
}
