package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.gdse.bo.custom.EmployeeBo;
import lk.ijse.gdse.model.EmployeeDTO;
import lk.ijse.gdse.model.tm.CustomerTm;
import lk.ijse.gdse.model.tm.EmployeeTm;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EmployeeFormController {

    public JFXButton btnBack;

    public TextField txtPosition;

    @FXML
    private TableColumn<EmployeeDTO, String> colAddress;

    @FXML
    private TableColumn<EmployeeDTO, String> colContact;

    @FXML
    private TableColumn<EmployeeDTO, String> colId;

    @FXML
    private TableColumn<EmployeeDTO, String> colPosition;

    @FXML
    private TableColumn<EmployeeDTO, String> colName;

    @FXML
    private TableColumn<EmployeeDTO, String> colSalary;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmployeeId;


    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;
    EmployeeBo employeeBo= (EmployeeBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.EMPLOYEE);
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        generateNewId();
        setCellValueFactories();
        loadEmployees();
        txtEmployeeId.setText(generateNewId());
        addTableSelectionListener();

    }

    private void setCellValueFactories() {
        colId.setCellValueFactory(new PropertyValueFactory<>("e_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("e_name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("e_contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("e_address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("e_salary"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
    }

    private void loadEmployees() throws SQLException, ClassNotFoundException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDTO> employeeDTOList = employeeBo.getAllEmployees();
            for (EmployeeDTO employeeDTO : employeeDTOList) {
                EmployeeTm tm = new EmployeeTm(
                        employeeDTO.getEmployeeId(),
                        employeeDTO.getEmployeeName(),
                        employeeDTO.getEmployeeContact(),
                        employeeDTO.getEmployeeAddress(),
                        employeeDTO.getEmployeeSalary(),
                        employeeDTO.getPosition()

                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addTableSelectionListener() {
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                EmployeeTm selectedEmployee = newValue;
                txtEmployeeId.setText(selectedEmployee.getE_id());
                txtName.setText(selectedEmployee.getE_name());
                txtAddress.setText(selectedEmployee.getE_address());
                txtContact.setText(selectedEmployee.getE_contact());
                txtSalary.setText(selectedEmployee.getE_salary());
                txtPosition.setText(selectedEmployee.getPosition());

            }
        });
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String eId = txtEmployeeId.getText();
        String eName = txtName.getText();
        String eContact = txtContact.getText();
        String eAddress = txtAddress.getText();
        String eSalary = txtSalary.getText();
        String position = txtPosition.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(eId, eName, eContact, eAddress, eSalary, position);
        try {if (isValied()){
            boolean isSaved = employeeBo.saveEmployee(employeeDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved successfully").show();
                initialize();
                clearFields();

            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to save employee").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving employee: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String eId = txtEmployeeId.getText();
        String eName = txtName.getText();
        String eContact = txtContact.getText();
        String eAddress = txtAddress.getText();
        String eSalary = txtSalary.getText();
        String position = txtPosition.getText();

        EmployeeDTO employeeDTO = new EmployeeDTO(eId, eName, eContact, eAddress, eSalary, position);
        try {if (isValied()){
            boolean isUpdated = employeeBo.updateEmployee(employeeDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated successfully").show();

                initialize();
                clearFields();

            }
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to update employee").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating employee: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String eId = txtEmployeeId.getText();
        try {
            boolean isDeleted = employeeBo.deleteEmployee(eId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted successfully").show();
                initialize();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to delete employee").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error deleting employee: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEmployeeId.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtPosition.clear();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtEmployeeId.getText();

        try {
            EmployeeDTO employeeDTO = employeeBo.searchByIdEmployee(id);
            setEmployeeData(employeeDTO);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error searching Employee: " + e.getMessage()).show();
        }
    }

    private void setEmployeeData(EmployeeDTO employeeDTO) {
        if (employeeDTO != null) {
            txtEmployeeId.setText(employeeDTO.getEmployeeId());
            txtName.setText(employeeDTO.getEmployeeName());
            txtContact.setText(employeeDTO.getEmployeeContact());
            txtAddress.setText(employeeDTO.getEmployeeAddress());
            txtSalary.setText(employeeDTO.getEmployeeSalary());
            txtPosition.setText(employeeDTO.getPosition());
        } else {
            System.out.println("Employee data is null.");
        }
    }

    public void txtNameOnAction(ActionEvent event) {
    }

    public void txtContactOnAction(ActionEvent event) {
    }

    public void txtAddressOnAction(ActionEvent event) {
    }

    public void txtSalaryOnAction(ActionEvent event) {
    }



    public void btnBackOnAction(ActionEvent event) throws IOException {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard-form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    private String getLastCustomerId(){//limit quiry eken last eka gannawa
        List<EmployeeTm> tempCustomersList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getE_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return employeeBo.generateNewEmployeeID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblEmployee.getItems().isEmpty()) {
            return "E001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E%03d", newCustomerId);
        }

    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }

    public void txtSalaryOnKeyReleased(KeyEvent keyEvent) {
         Regex.setTextColor(lk.ijse.gdse.Util.TextField.AMOUNT,txtSalary);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.CONTACT,txtContact);
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME,txtName);
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {

        Regex.setTextColor(lk.ijse.gdse.Util.TextField.EMPLOYEEID,txtEmployeeId);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.AMOUNT,txtSalary)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.CONTACT,txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NAME,txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.EMPLOYEEID,txtEmployeeId)) return false;


        return true;
    }

    public void txtPositionOnAction(ActionEvent event) {
    }
}