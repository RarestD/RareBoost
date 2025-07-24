package rare.creations.RareBoost.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Controller
public class LoginScreenController implements Initializable {

    private ConfigurableApplicationContext applicationContext;

    public LoginScreenController(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    Label info;

    @FXML
    MFXTextField usernameFields;

    @FXML
    MFXPasswordField passwordFields;

    @FXML
    MFXButton login;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setInfo(String text){
        info.setText(text);
        info.setOpacity(1);
    }

    private void setInfoDis(){
        info.setOpacity(0);
    }

    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        String username = usernameFields.getText();
        String password = passwordFields.getText();
        if ((username.isBlank() || username.isEmpty()) && (!password.isEmpty() || !password.isBlank())){
            System.out.println("Username Field have to be filled out");
            setInfo("Username Field have to be filled out");
        } else if ((!username.isBlank() || !username.isEmpty()) && (password.isEmpty() || password.isBlank())) {
            System.out.println("Password Field have to be filled out");
            setInfo("Password Field have to be filled out");
        }else if ((username.isBlank() || username.isEmpty()) && (password.isEmpty() || password.isBlank())){
            System.out.println("Username Field & Password Field have to be filled out");
            setInfo("Username Field & Password Field have to be filled out");
        }else if ((!username.isBlank() || !username.isEmpty()) && (!password.isEmpty() || !password.isBlank())){
            String isSuccess = "Success"; // cause im not implementing LOGIN after all
            if (isSuccess.equals("Success")){
                setInfoDis();
                Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RareBoost.fxml"));
                fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
                Parent parent = fxmlLoader.load();
                stage.setScene(new Scene(parent));
            }else {
                System.out.println("not Success Login");
                setInfo(isSuccess);
            }
        }else {
            System.out.println("You Should'nt be here");
        }
    }
}
