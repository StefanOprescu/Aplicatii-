package me.server;

import me.service.IService;
import me.service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort = 55556;

    public static void main(String[] args) {
        Properties serverProps = new Properties();
        try{
            serverProps.load(new FileReader("D:\\Facultate\\Semestrul 4\\Medii de Programare si Proiectare\\Teme Lab\\Problema 3\\src\\main\\resources\\server.config"));
            System.out.println("Server properties set. ");

            serverProps.list(System.out);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        ApplicationContext context = new ClassPathXmlApplicationContext("injectariSpring.xml");
        IService service = (Service) context.getBean("service");

        int portServer = defaultPort;
        try{
            portServer = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        AbstractServer server = new ProbaObjectConcurrentServer(portServer,service);
        try{
            server.start();
        }catch (ServerException e){
            e.printStackTrace();
        }
    }
}
