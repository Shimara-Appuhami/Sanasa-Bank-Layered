//package lk.ijse.gdse.controller;
//
//import com.jfoenix.controls.JFXButton;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import lk.ijse.gdse.bo.BOFactory;
//import lk.ijse.gdse.bo.custom.UserBo;
//import lk.ijse.gdse.dao.custom.UserDAO;
//
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//public class ChangePasswordFormController {
//
//    @FXML
//    private JFXButton btnChange;
//
//    @FXML
//    private TextField txtNewPassword;
//
//
//    UserBo userBo= (UserBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.USER);
//
//    @FXML
//    void btnChangeOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
//    changePassword();
//
//
//    }
//    void changePassword() throws IOException, SQLException, ClassNotFoundException {
//        String newPassword = txtNewPassword.getText();
//        String nic=txtNic.getText();
//
//        boolean updated = userBo.updatePasswordByNIC(nic, newPassword);
//        if (updated) {
//            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").showAndWait();
//            navigateToLoginPage();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Failed to update password!").show();
//        }
//    }
//    private void navigateToLoginPage() throws IOException {
//        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login-form.fxml"));
//
//        Scene scene = new Scene(rootNode);
//
//        Stage stage = (Stage) btnChange.getScene().getWindow(); // Use any control's scene
//        stage.setScene(scene);
//        stage.centerOnScreen();
//        stage.setTitle("login Form");
//    }
//
//   /* private boolean isValidPassword(String password) {
//        // Add your validation logic here, such as checking length, complexity, etc.
//        return password != null && !password.isEmpty() && password.length() >= 8;}*/
//
//
//
//    public void txtNewPasswordOnAction(ActionEvent event) throws SQLException {
//       // btnChangeOnAction(event); // Trigger the change action when Enter is pressed in the text field
//    }
//}
