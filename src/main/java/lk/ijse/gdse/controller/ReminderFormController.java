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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.Util.Regex;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.ReminderBo;
import lk.ijse.gdse.model.ReminderDTO;
import lk.ijse.gdse.model.tm.PaymentTm;
import lk.ijse.gdse.model.tm.ReminderTm;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReminderFormController {


    public JFXComboBox<String> cmbType;
    public JFXComboBox<String> cmbStatus;
    public TextField txtLoanType;
    public TextField txtNic;
    public TableColumn colNic;
    public TableColumn colLoanType;
    public DatePicker txtDate;
    public TextField txtEmail;
    @FXML
    private JFXButton btnBack;

    @FXML
    private TableColumn<ReminderDTO, String> colDate;

    @FXML
    private TableColumn<ReminderDTO, String> colInvoiceNo;

    @FXML
    private TableColumn<ReminderDTO, String> colMessage;

    @FXML
    private TableColumn<ReminderDTO, String> colReminderId;

    @FXML
    private TableColumn<ReminderDTO, String> colStatus;

    @FXML
    private TableColumn<ReminderDTO, String> colType;

    @FXML
    private TableView<ReminderTm> tblReminder;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtReminderId;
    ReminderBo reminderBo= (ReminderBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.REMINDER);


    private void initializeComboBoxType() {
        ObservableList<String> type = FXCollections.observableArrayList(
                "Loan payment reminder",
                "overdue notice"

        );
        cmbType.setItems(type);
    }
    private void initializeComboBoxStatus() {
        ObservableList<String> status = FXCollections.observableArrayList(
                "Email",
                "Letter"

        );
        cmbStatus.setItems(status);
    }
    @FXML
    void initialize() throws ClassNotFoundException {
        initializeComboBoxStatus();
        initializeComboBoxType();
        //getPaymentIds();
        generateNewId();
        setDate();
        setCellValueFactory();
        loadAllReminders();
        txtReminderId.setText(generateNewId());
    }

    private void setCellValueFactory() {


        colReminderId.setCellValueFactory(new PropertyValueFactory<>("r_id"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colLoanType.setCellValueFactory(new PropertyValueFactory<>("loan_type"));
        colMessage.setCellValueFactory(new PropertyValueFactory<>("r_message"));
        colType.setCellValueFactory(new PropertyValueFactory<>("r_type"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("r_date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("r_status"));

    }

    private void loadAllReminders() throws ClassNotFoundException {
        ObservableList<ReminderTm> obList = FXCollections.observableArrayList();

        try {
            List<ReminderDTO> reminderDTOList = reminderBo.getAllReminders();
            for (ReminderDTO reminderDTO : reminderDTOList) {
                ReminderTm tm = new ReminderTm(
                        reminderDTO.getRId(),
                        reminderDTO.getNic(),
                        reminderDTO.getLoanType(),
                        reminderDTO.getRMessage(),
                        reminderDTO.getRType(),
                        reminderDTO.getRDate(),
                        reminderDTO.getRStatus()
                );

                obList.add(tm);
            }

            tblReminder.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtNicOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String nic = txtNic.getText();


        ReminderDTO reminderDTO = reminderBo.searchByNic(nic);
        if (reminderDTO != null) {
//              txtReminderId.setText(reminder.getRId());
//            txtNic.setText(reminder.getNic());
            txtLoanType.setText(reminderDTO.getLoanType());
//            txtMessage.setText(reminder.getRMessage());
//            cmbType.setValue(reminder.getRType());
//            txtDate.setValue(LocalDate.parse(reminder.getRDate()));
//            cmbStatus.setValue(reminder.getRStatus());
            txtEmail.setText(reminderDTO.getEmail());

        } else {
            String type = reminderBo.getLoanType(nic);
            String email=reminderBo.getEmail(nic);

            if (type != null) {
                txtLoanType.setText(type);
                System.out.println("type ok");
                txtEmail.setText(email);
                System.out.println("email ok");

                new Alert(Alert.AlertType.INFORMATION, "Loan Type and email found").show();
            } else {
                txtLoanType.setText(null);

                new Alert(Alert.AlertType.ERROR, "Loan Type not found for NIC: " + nic).show();
            }
        }
    }
    private void setCustomerData(ReminderDTO reminderDTO) {
        if (reminderDTO != null) {
            txtReminderId.setText(reminderDTO.getRId());
            txtNic.setText(reminderDTO.getNic());
            cmbType.setValue(reminderDTO.getLoanType());
            txtMessage.setText(reminderDTO.getRMessage());
            cmbType.setValue(reminderDTO.getRType());
            txtDate.setValue(LocalDate.parse(reminderDTO.getRDate()));
            cmbStatus.setValue(reminderDTO.getRStatus());

        } else {
            System.out.println("Reminder data is null.");
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtReminderId.getText();
        String nic = txtNic.getText();
        String loanType = txtLoanType.getText();
        String message = txtMessage.getText();
        String type = String.valueOf(cmbType.getValue());
        String date = String.valueOf(txtDate.getValue());
        String status = cmbStatus.getValue();



        ReminderDTO reminderDTO = new ReminderDTO(id,nic,loanType, message, type, date, status);

        try {if (isValied()){
            boolean isSaved = reminderBo.saveReminder(reminderDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reminder saved successfully").show();
                initialize();
                clearFields();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Failed to save reminder").show();
        }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving reminder: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtReminderId.getText();
        String nic = txtNic.getText();
        String loanType = txtLoanType.getText();
        String message = txtMessage.getText();
        String type = String.valueOf(cmbType.getValue());
        String date = String.valueOf(txtDate.getValue());
        String status = cmbStatus.getValue();


        ReminderDTO reminderDTO = new ReminderDTO(id,nic,loanType, message, type, date, status);

        try {if (isValied()){
            boolean isUpdated = reminderBo.updateReminder(reminderDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reminder updated successfully").show();
                initialize();
                clearFields();
            }  } else {
            new Alert(Alert.AlertType.WARNING, "Failed to update reminder").show();
        }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating reminder: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtReminderId.getText();

        try {
            boolean isDeleted = reminderBo.deleteReminder(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Reminder deleted successfully").show();
                initialize();
                clearFields();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to delete reminder").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error deleting reminder: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtReminderId.clear();
        txtNic.clear();
        txtLoanType.clear();
        txtMessage.clear();
        cmbType.setValue(null);
        txtDate.setValue(null);
        cmbStatus.setValue(null);
    }


    public void txtTypeOnAction(ActionEvent event) {
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard-form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) btnBack.getScene().getWindow(); // Use any control's scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }


    public void txtDateOnAction(ActionEvent event) {
    }


    public void txtStatusOnAction(ActionEvent event) {
    }

    private String getLastCustomerId(){//limit quiry eken last eka gannawa
        List<ReminderTm> tempCustomersList = new ArrayList<>(tblReminder.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getR_id();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return reminderBo.generateNewReminderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblReminder.getItems().isEmpty()) {
            return "R001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("R", "")) + 1;
            return String.format("R%03d", newCustomerId);
        }

    }

    private void showAlert(Alert.AlertType alertType, String message) {
        new Alert(alertType, message).show();
    }
    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setValue(LocalDate.parse(String.valueOf(now)));
    }
   /* private void getPaymentIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = ReminderRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbInvoiceNo.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    public void txtLoanTypeOnAction(ActionEvent event) {
    }



    @FXML
    void btnSendOnAction(ActionEvent event) {
        try {
            String recipientEmail = txtEmail.getText();
            String subject = cmbType.getValue();
            String body = txtMessage.getText();
            if (cmbStatus.getValue() != null && cmbStatus.getValue().equals("Email")) {
                String encodedEmailBody = URLEncoder.encode(body, "UTF-8");
                String encodedSubject = URLEncoder.encode(subject, "UTF-8");
                String encodedRecipientEmail = URLEncoder.encode(recipientEmail, "UTF-8");

                String url = "https://mail.google.com/mail/?view=cm&fs=1&to=" + encodedRecipientEmail +
                        "&body=" + encodedEmailBody +
                        "&su=" + encodedSubject;

                Desktop.getDesktop().browse(new URI(url));
            } else {
                new Alert(Alert.AlertType.WARNING, "Please select 'Email' as the status to send an email.").show();
            }
        } catch (IOException | URISyntaxException e) {
            System.out.println("An error occurred: " + e.getLocalizedMessage());
        }
    }


    public void txtNicOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNic);
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse.Util.TextField.REMINDERID,txtReminderId);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.NIC,txtNic)) return false;
        if (!Regex.setTextColor(lk.ijse.gdse.Util.TextField.REMINDERID,txtReminderId)) return false;

        return true;
    }

    public void txtMessageOnAction(ActionEvent event) {

    }

    public void txtSearchOnAction(ActionEvent event) {
    }

    public void txtEmailOnAction(ActionEvent event){
    }
}