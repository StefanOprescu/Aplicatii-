package me.map.lab3;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import javafx.application.Application;
import me.map.lab3.consola.Gui;
import me.map.lab3.consola.UI;
import me.map.lab3.controller.Controller;
import me.map.lab3.domain.Tema;
import me.map.lab3.properties.EntityProperties;
import me.map.lab3.repos.RepoNote;
import me.map.lab3.repos.RepoStudenti;
import me.map.lab3.repos.RepoTeme;
import me.map.lab3.services.ServiceNote;
import me.map.lab3.services.ServiceStudenti;
import me.map.lab3.services.ServiceTeme;
import me.map.lab3.teste.TestAnUniversitar;
import me.map.lab3.teste.TestNote;
import me.map.lab3.teste.TestStudenti;
import me.map.lab3.validator.ValidatorNota;
import me.map.lab3.validator.ValidatorStudent;
import me.map.lab3.validator.ValidatorTema;
import me.map.lab3.teste.TestTeme;
import me.map.lab3.writerPDF.PDFGenerator;
import sun.security.krb5.Config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        //"D:\\Facultate\\Semestrul 3\\Metode Avansate de Programare\\Laborator\\Laborator 3\\Note exportate\\"
        //TestStudenti ts = new TestStudenti();
        //ts.testAll();
        //TestTeme tt = new TestTeme();
        //tt.testAll();
        //TestAnUniversitar tau = new TestAnUniversitar();
        //tau.testAll();
        //TestNote tn = new TestNote();
        //tn.testAll();


        RepoStudenti grs = new RepoStudenti("XML\\Studenti.xml",new ValidatorStudent());
        RepoTeme grt = new RepoTeme("XML\\Teme.xml",new ValidatorTema());
        RepoNote grn = new RepoNote("XML\\Note.xml",new ValidatorNota());
        ServiceStudenti serviceStudenti = new ServiceStudenti(grs);
        ServiceTeme serviceTeme = new ServiceTeme(grt);
        ServiceNote serviceNote = new ServiceNote(grn,"Note exportate\\","Fisier PDF\\");
        Controller controller = new Controller(serviceStudenti,serviceTeme,serviceNote);
        //controller.stergeNota(5,9);
        //UI ui = new UI(controller);
        //ui.run();

        Gui g = new Gui();
        g.run(args);
        //EntityProperties ep = new EntityProperties();
        //Properties prop = ep.getProperty();
        //System.out.println(prop.getProperty("data.entities.studenti"));
        //System.out.println(prop.getProperty("data.entities.teme"));
        //System.out.println(prop.getProperty("data.entities.note"));
        //PDFGenerator.writeToPDF("Fisiere PDF\\bla.pdf","asd");
    }

}