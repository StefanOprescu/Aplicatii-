package me;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.controller.ControllerFereastraPrincipala;
import me.controller.ControllerLogin;
import me.networking.ProbaServicesObjectProxy;
import me.service.IService;
import me.service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Mainn extends Application {

/*    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage window) throws Exception{
        FXMLLoader loaderLogare = new FXMLLoader(ControllerLogin.class.getResource("/logare.fxml"));
        Parent root = loaderLogare.load();
        Scene scene = new Scene(root);
        window.setTitle("Logare");
        window.setScene(scene);
        window.show();
    }*/
    private static int defaultChatPort = 55556;
    private static String defaultServer = "localhost";
    public void start(Stage window) throws Exception {
        Properties clientProps = new Properties();
        try {
            clientProps.load(new FileReader("D:\\Facultate\\Semestrul 4\\Medii de Programare si Proiectare\\Teme Lab\\Problema 3\\src\\main\\resources\\client.config"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties " + e);
            return;
        }
        String serverIp = clientProps.getProperty("server.host",defaultServer);
        int serverPort = defaultChatPort;
        try{
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }
        catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }
        IService server = new ProbaServicesObjectProxy(serverIp,serverPort);
        FXMLLoader loaderLogare = new FXMLLoader();
        loaderLogare.setLocation(getClass().getResource("/logare.fxml"));
        Parent root = loaderLogare.load();
        ControllerLogin ctrl = loaderLogare.<ControllerLogin>getController();
        ctrl.setServer(server);

        FXMLLoader loaderAngajat = new FXMLLoader();
        loaderAngajat.setLocation(getClass().getResource("/fereastraPrincipala.fxml"));
        Parent rootAngajat = loaderAngajat.load();
        ControllerFereastraPrincipala controllerFereastraPrincipala = loaderAngajat.<ControllerFereastraPrincipala>getController();
        controllerFereastraPrincipala.setServer(server);

        ctrl.setAngajatController(controllerFereastraPrincipala);
        ctrl.setParent(rootAngajat);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

}
