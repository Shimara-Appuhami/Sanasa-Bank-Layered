package lk.ijse.gdse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionFormController {

    public JFXButton btnLoginCashier;

    public JFXButton btnLoginManager;

    public void btnLoginCashierOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/loginCashier-form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) btnLoginCashier.getScene().getWindow(); // Use any control's scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }

    public void btnLoginManagerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login-form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) btnLoginManager.getScene().getWindow(); // Use any control's scene
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }
}
