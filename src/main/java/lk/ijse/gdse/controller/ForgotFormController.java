package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.UserBo;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotFormController {


    @FXML
    private JFXButton btnLogin;

    @FXML
    private TextField txtNic;

    @FXML
    private JFXButton btnChange;

    @FXML
    private TextField txtNewPassword;


     UserBo userBo = (UserBo) BOFactory.getBoFactory().getBo(BOFactory.BoTypes.USER);

    private void checkCredential(String nic) throws SQLException, IOException, ClassNotFoundException {
        boolean isNicExist = userBo.checkCredential(nic);
        if (isNicExist) {
            String newPassword = txtNewPassword.getText();
            boolean updated = userBo.updatePasswordByNIC(nic, newPassword);
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
                navigateToLoginPage(); // Navigate after successful password update
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update password!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Sorry! NIC is incorrect!").show();
        }
    }

    @FXML
    void btnChangeOnAction(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        checkCredential(txtNic.getText());
    }

    private void navigateToLoginPage() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/login-form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) btnChange.getScene().getWindow(); // Use any control's scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }

    public void txtNicOnAction(ActionEvent actionEvent) {
        // Handle action if needed
    }

    public void txtNewPasswordOnAction(ActionEvent actionEvent) {
        // Handle action if needed
    }
}
