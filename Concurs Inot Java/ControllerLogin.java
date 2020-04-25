package me.controller;

import javafx.scene.Node;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import me.model.Angajat;
import me.repository.RepoAngajat;
import me.repository.RepoParticipant;
import me.repository.RepoProba;
import me.repository.RepoProbaParticipant;
import me.service.IService;
import me.service.Service;
import me.service.ServiceException;
import me.utils.AlertBox;
import me.validator.ValidatorAngajat;
import me.validator.ValidatorParticipant;
import me.validator.ValidatorProba;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable, EventHandler {
    private IService server;
    private ControllerFereastraPrincipala angajatCtrl;
    private Angajat crtUser;
    Parent mainParent;

    @FXML
    TextField textFieldUsername;
    @FXML
    PasswordField textFieldParola;
    @FXML
    Button butonLogin;

    @Override
    public void handle(Event event) {
        if(event.getSource()==butonLogin){
                if(textFieldUsername.getText().equals("") || textFieldParola.getText().equals(""))
                    AlertBox.display("Eroare","Username sau parola incorecte!\n");
                else
                {
                    crtUser = new Angajat (1,"",20,textFieldUsername.getText(),textFieldParola.getText());
                    angajatCtrl.setUser(crtUser);

                    try{
                        server.login(crtUser,angajatCtrl);
                        server.check(crtUser);
                        Stage stage = new Stage();
                        stage.setTitle("Fereastra" + crtUser.getNume());
                        stage.setScene(new Scene(mainParent));
                        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                            @Override
                            public void handle(WindowEvent event) {
                                angajatCtrl.logout();
                                System.exit(0);
                            }
                        });
                        stage.show();
                        Stage windowLogare = (Stage)((Node)butonLogin).getScene().getWindow();
                        windowLogare.hide();
                        angajatCtrl.setComponente();

                    }
                    catch(ServiceException e){
                        AlertBox.display("eroare",e.getMessage());
                        textFieldParola.clear();
                        textFieldUsername.clear();
                    }
                }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        butonLogin.setOnAction(this);
    }

    public void setServer(IService server){
        this.server = server;
    }

    public void setAngajatController(ControllerFereastraPrincipala controllerFereastraPrincipala){
        this.angajatCtrl = controllerFereastraPrincipala;
    }

    public void setParent(Parent root){
        this.mainParent = root;
    }
}
