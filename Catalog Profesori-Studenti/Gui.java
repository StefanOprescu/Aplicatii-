package me.map.lab3.consola;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import me.map.lab3.controller.Controller;
import me.map.lab3.domain.Nota;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.elementeGui.AlertBox;
import me.map.lab3.elementeGui.AlertBoxPieChart;
import me.map.lab3.elementeGui.ConfirmBox;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.properties.EntityProperties;
import me.map.lab3.repos.GenericRepo;
import me.map.lab3.repos.RepoNote;
import me.map.lab3.repos.RepoStudenti;
import me.map.lab3.repos.RepoTeme;
import me.map.lab3.services.ServiceNote;
import me.map.lab3.services.ServiceStudenti;
import me.map.lab3.services.ServiceTeme;
import me.map.lab3.validator.ValidatorNota;
import me.map.lab3.validator.ValidatorStudent;
import me.map.lab3.validator.ValidatorTema;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;


public class Gui extends Application {
    private Controller controller;
    private BorderPane borderPaneEcranPrincipal;
    private Scene scene;
    private Stage window;
    private Button spreStudenti;
    private Button spreNote;
    private Button spreRapoarte;
    private Scene sceneStudenti;
    private Scene sceneRapoarte;
    private Scene sceneNote;
    private TableView<Student> studenti;
    private TableView<Nota> note;
    private TableView<Tema> teme;
    private ComboBox<Tema> comboBoxTema;
    private TextField punctajText;
    private TextArea textFeedback;
    private boolean rezultat;

    public Gui() {
        EntityProperties ep = new EntityProperties();
        Properties prop = ep.getProperty();
        RepoStudenti grs = new RepoStudenti(prop.getProperty("data.entities.studenti"), new ValidatorStudent());
        RepoTeme grt = new RepoTeme(prop.getProperty("data.entities.teme"), new ValidatorTema());
        RepoNote grn = new RepoNote(prop.getProperty("data.entities.note"), new ValidatorNota());
        ServiceStudenti serviceStudenti = new ServiceStudenti(grs);
        ServiceTeme serviceTeme = new ServiceTeme(grt);
        ServiceNote serviceNote = new ServiceNote(grn, "Note exportate\\","Fisiere PDF\\");
        this.controller = new Controller(serviceStudenti, serviceTeme, serviceNote);
    }

    private void fereastraNotaSiFeedback(Float notaMaxima) {
        Stage fereastra = new Stage();
        fereastra.initModality(Modality.APPLICATION_MODAL);
        fereastra.setTitle("Nota");
        fereastra.setMinWidth(250);
        Label label = new Label("Introduceti nota si feedback-ul!\n");

        Button adaugaButton = new Button("adauga");

        HBox punctajEtichetaSiText = new HBox();
        punctajEtichetaSiText.setAlignment(Pos.CENTER);
        Label punctajEticheta = new Label("nota:");
        punctajText = new TextField();
        punctajEtichetaSiText.getChildren().addAll(punctajEticheta, punctajText);
        punctajText.setText(notaMaxima.toString());
        if (notaMaxima == 10)
            textFeedback = new TextArea();
        else
            textFeedback = new TextArea("NOTA A FOST DIMINUATA CU " + (10 - notaMaxima) + " PUNCTE DIN CAUZA INTARZIERILOR!\n");

        adaugaButton.setOnAction(e ->
        {
            if (ConfirmBox.display("Confirmare", "Sigur doriti sa continuati?\n")) {
                rezultat = true;
                fereastra.close();
            }
        });

        VBox layout = new VBox(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, punctajEtichetaSiText, textFeedback, adaugaButton);
        Scene scene = new Scene(layout);
        fereastra.setScene(scene);
        fereastra.showAndWait();
    }


    private void initializeazaFereastraPrincipala() {
        window.setTitle("Main Menu");
        borderPaneEcranPrincipal = new BorderPane();
        scene = new Scene(borderPaneEcranPrincipal, 300, 250);
        spreNote = new Button("Spre Note");
        spreStudenti = new Button("Spre Studenti");
        spreRapoarte = new Button("Spre Rapoarte");
        VBox layoutStudenti = new VBox(5);                   //layout-ul studentilor din ecranul principal
        layoutStudenti.setAlignment(Pos.CENTER);
        VBox layoutRapoarte = new VBox(5);                       //layout-ul temelor din ecranul principal
        layoutRapoarte.setAlignment(Pos.CENTER);
        VBox layoutNote = new VBox(5);                       //layout-ul notelor din ecranul principal
        layoutNote.setAlignment(Pos.CENTER);
        borderPaneEcranPrincipal.setLeft(layoutStudenti);
        borderPaneEcranPrincipal.setCenter(layoutNote);
        borderPaneEcranPrincipal.setRight(layoutRapoarte);
        layoutStudenti.getChildren().addAll(spreStudenti);
        layoutRapoarte.getChildren().addAll(spreRapoarte);
        layoutNote.getChildren().addAll(spreNote);
    }

    private void incarcaStudenti() {
        studenti.getItems().clear();
        ArrayList<Student> l = new ArrayList<>();
        Iterator<Student> it = controller.getStudenti().iterator();
        it.forEachRemaining(l::add);
        ObservableList<Student> students = FXCollections.observableArrayList(l);
        studenti.setItems(students);
    }

    private void incarcaNote() {
        note.getItems().clear();
        ArrayList<Nota> l = new ArrayList<>();
        Iterator<Nota> it = controller.getNote().iterator();
        it.forEachRemaining(l::add);
        ObservableList<Nota> notes = FXCollections.observableArrayList(l);
        note.setItems(notes);
    }

    private void incarcaTemeComboBox() {
        comboBoxTema.getItems().clear();
        ArrayList<Tema> l = new ArrayList<>();
        Iterator<Tema> it = controller.getTeme().iterator();
        it.forEachRemaining(l::add);
        ObservableList<Tema> teme = FXCollections.observableArrayList(l);
        comboBoxTema.setItems(teme);
        comboBoxTema.getSelectionModel().select(controller.ceaMaiApropiataTemaDeOData());
    }


    private void creeazaLayoutStudenti() {
        HBox layoutPrincipalStudenti = new HBox(5);
        VBox layoutStangaStudenti = new VBox(5);
        VBox layoutDreaptaStudenti = new VBox(5);
        layoutPrincipalStudenti.getChildren().addAll(layoutStangaStudenti, layoutDreaptaStudenti);
        sceneStudenti = new Scene(layoutPrincipalStudenti, 800, 650);
        Button inapoi = new Button("_Inapoi");
        TableColumn<Student, Integer> coloanaId = new TableColumn<>("Id");
        coloanaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, String> coloanaNume = new TableColumn<>("Nume");
        coloanaNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        TableColumn<Student, String> coloanaPrenume = new TableColumn<>("Prenume");
        coloanaPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        TableColumn<Student, String> coloanaGrupa = new TableColumn<>("Grupa");
        coloanaGrupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        TableColumn<Student, String> coloanaEmail = new TableColumn<>("Email");
        coloanaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Student, String> coloanaProfesor = new TableColumn<>("Profesor");
        coloanaProfesor.setCellValueFactory(new PropertyValueFactory<>("cadruDidacticIndrumator"));
        studenti = new TableView<>();
        studenti.getColumns().addAll(coloanaId, coloanaNume, coloanaPrenume, coloanaGrupa, coloanaEmail, coloanaProfesor);
        incarcaStudenti();
        layoutStangaStudenti.getChildren().addAll(studenti, inapoi);
        layoutDreaptaStudenti.setAlignment(Pos.CENTER);
        layoutPrincipalStudenti.setAlignment(Pos.CENTER);
        layoutStangaStudenti.setAlignment(Pos.CENTER);
        inapoi.setOnAction(e -> window.setScene(scene));
        VBox textSiEticheta = new VBox(5);
        HBox idEtichetaSiText = new HBox();
        textSiEticheta.setAlignment(Pos.CENTER);
        Label idEticheta = new Label("id:");
        TextField idText = new TextField();
        idEtichetaSiText.setAlignment(Pos.CENTER);
        idEtichetaSiText.getChildren().addAll(idEticheta, idText);
        HBox numeEtichetaSiText = new HBox();
        numeEtichetaSiText.setAlignment(Pos.CENTER);
        Label numeEticheta = new Label("nume:");
        TextField numeText = new TextField();
        numeEtichetaSiText.getChildren().addAll(numeEticheta, numeText);

        HBox prenumeEtichetaSiText = new HBox();
        prenumeEtichetaSiText.setAlignment(Pos.CENTER);
        Label prenumeEticheta = new Label("prenume:");
        TextField prenumeText = new TextField();
        prenumeEtichetaSiText.getChildren().addAll(prenumeEticheta, prenumeText);

        HBox grupaEtichetaSiText = new HBox();
        grupaEtichetaSiText.setAlignment(Pos.CENTER);
        Label grupaEticheta = new Label("grupa:");
        TextField grupaText = new TextField();
        grupaEtichetaSiText.getChildren().addAll(grupaEticheta, grupaText);

        HBox emailEtichetaSiText = new HBox();
        emailEtichetaSiText.setAlignment(Pos.CENTER);
        Label emailEticheta = new Label("email:");
        TextField emailText = new TextField();
        emailEtichetaSiText.getChildren().addAll(emailEticheta, emailText);

        HBox profEtichetaSiText = new HBox();
        profEtichetaSiText.setAlignment(Pos.CENTER);
        Label profEticheta = new Label("profesor:");
        TextField profText = new TextField();
        profEtichetaSiText.getChildren().addAll(profEticheta, profText);

        textSiEticheta.getChildren().addAll(idEtichetaSiText, numeEtichetaSiText, prenumeEtichetaSiText, grupaEtichetaSiText, emailEtichetaSiText, profEtichetaSiText);


        Button adauga = new Button("adauga");
        Button modifica = new Button("modifica");
        Button sterge = new Button("sterge");
        layoutDreaptaStudenti.getChildren().addAll(textSiEticheta, adauga, modifica, sterge);

        adauga.setOnAction(e -> {
            try {
                Integer id = Integer.parseInt(idText.getText());
                try {

                    if (controller.adaugaStudent(id, numeText.getText(), prenumeText.getText(), grupaText.getText(), emailText.getText(), profText.getText()) == null) {
                        AlertBox.display("Informatie", "Studentul a fost salvat cu succes!");
                        incarcaStudenti();
                    } else
                        AlertBox.display("Informatie", "Studentul exista deja!");
                } catch (ValidationException eroare) {
                    AlertBox.display("Eroare", eroare.getMessage());
                }
            } catch (NumberFormatException p) {
                AlertBox.display("Eroare", "Id invalid!");
            }
        });

        modifica.setOnAction(e -> {
            try {
                Integer id = Integer.parseInt(idText.getText());
                try {

                    if (controller.modificaStudent(id, numeText.getText(), prenumeText.getText(), grupaText.getText(), emailText.getText(), profText.getText()) == null) {
                        AlertBox.display("Informatie", "Studentul a fost modificat cu succes!");
                        incarcaStudenti();
                        controller.modificaStudentNote(id, numeText.getText(), prenumeText.getText(), grupaText.getText(), emailText.getText(), profText.getText());
                        incarcaNote();
                    } else
                        AlertBox.display("Informatie", "Studentul nu exista!");
                } catch (ValidationException eroare) {
                    AlertBox.display("Eroare", eroare.getMessage());
                }
            } catch (NumberFormatException p) {
                AlertBox.display("Eroare", "Id invalid!");
            }
        });

        sterge.setOnAction(e -> {
            try {
                Integer id = Integer.parseInt(idText.getText());
                Student s = controller.stergeStudent(id);
                if (s != null) {
                    AlertBox.display("Informatie", "Studentul " + s.toString() + " a fost sters cu succes!");
                    incarcaStudenti();
                } else
                    AlertBox.display("Informatie", "Studentul nu exista!");
            } catch (NumberFormatException p) {
                AlertBox.display("Eroare", "Id invalid!");
            }
        });


    }



    private void creeazaLayoutNote() {
        HBox layoutPrincipal = new HBox(10);
        VBox layoutStanga = new VBox(5);
        comboBoxTema = new ComboBox<>();
        incarcaTemeComboBox();
        VBox layoutDreapta = new VBox(5);
        Button inapoi = new Button("_Inapoi");
        layoutDreapta.setAlignment(Pos.CENTER);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutStanga.setAlignment(Pos.CENTER);
        layoutPrincipal.getChildren().addAll(layoutStanga, layoutDreapta);
        sceneNote = new Scene(layoutPrincipal, 650, 300);

        inapoi.setOnAction(e -> window.setScene(scene));

        HBox temeComboBoxLayout = new HBox(3);
        temeComboBoxLayout.setAlignment(Pos.CENTER);
        Label temeComboxBoxEticheta = new Label("Teme");
        temeComboBoxLayout.getChildren().addAll(temeComboxBoxEticheta, comboBoxTema);

        HBox numeEtichetaSiText = new HBox();
        numeEtichetaSiText.setAlignment(Pos.CENTER);
        Label numeEticheta = new Label("nume:");
        TextField numeText = new TextField();
        numeEtichetaSiText.getChildren().addAll(numeEticheta, numeText);

        HBox prenumeEtichetaSiText = new HBox();
        prenumeEtichetaSiText.setAlignment(Pos.CENTER);
        Label prenumeEticheta = new Label("prenume:");
        TextField prenumeText = new TextField();
        prenumeEtichetaSiText.getChildren().addAll(prenumeEticheta, prenumeText);


        HBox motivatEtichetaSiText = new HBox();
        motivatEtichetaSiText.setAlignment(Pos.CENTER);
        Label motivatEticheta = new Label("scutire:");
        TextField motivatText = new TextField();
        motivatEtichetaSiText.getChildren().addAll(motivatEticheta, motivatText);
        motivatText.setText("0");

        Button adaugaNota = new Button("adauga nota");

        note = new TableView<>();
        TableColumn coloanaNume = new TableColumn<>("Nume");
        coloanaNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        TableColumn coloanaPrenume = new TableColumn<>("Prenume");
        coloanaPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        TableColumn coloanaGrupa = new TableColumn<>("Grupa");
        coloanaGrupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        TableColumn coloanaDescriere = new TableColumn<>("Descriere");
        coloanaDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        TableColumn coloanaNota = new TableColumn<>("Nota");
        coloanaNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        note.getColumns().addAll(coloanaNume, coloanaPrenume, coloanaGrupa, coloanaDescriere, coloanaNota);
        incarcaNote();
        layoutStanga.getChildren().addAll(note, inapoi);
        Button uitat = new Button("adauga nota uitata");
        adaugaNota.setOnAction(e -> {
            Tema t = comboBoxTema.getValue();
            Student s = controller.cautaStudentNume(numeText.getText(), prenumeText.getText());
            if (s == null) {
                AlertBox.display("Eroare", "Studentul nu exista\n");
            } else {
                try {
                    int motivat;
                    motivat = Integer.parseInt(motivatText.getText());
                    try {
                        if (controller.adaugaNotaMotivat(s.getId(), t.getId(), motivat) == null) {
                            if (controller.maiPoateFiPredata(s.getId(), t.getId())) {
                                Float notaMaxima = controller.getNotaMaxima(s.getId(), t.getId());
                                AlertBox.display("Nota Maxima", "Nota maxima pe care studentul o poate lua este " + notaMaxima);
                                fereastraNotaSiFeedback(notaMaxima);
                                if (rezultat) {
                                    try {
                                        float nota = Float.parseFloat(punctajText.getText());
                                        try {
                                            //cand merge, aici sunt
                                            if (controller.modificaNota(s.getId(), t.getId(), nota, motivat, textFeedback.getText()) == null) {
                                                AlertBox.display("Nota", "Nota a fost adaugata cu succes!\n");
                                                incarcaNote();
                                                rezultat = false;
                                            } else
                                                AlertBox.display("Eroare", "Nota nu poate fi modificata!\n");
                                        } catch (ValidationException ve) {
                                            AlertBox.display("Eroare", ve.getMessage());
                                        }
                                    } catch (NumberFormatException nfe) {
                                        AlertBox.display("Eroare", "Tip de data pentru nota invalid!\n");
                                        controller.stergeNota(s.getId(), t.getId());
                                    }
                                } else {
                                    controller.stergeNota(s.getId(), t.getId());
                                }
                            } else {
                                AlertBox.display("Eroare", "Laboratorul nu mai poate fi predat!\n");
                            }
                        } else {
                            AlertBox.display("Eroare", "Nota exista deja!\n");
                        }
                    } catch (ValidationException v) {
                        AlertBox.display("Eroare", v.getMessage());
                    }

                } catch (NumberFormatException nfe) {
                    AlertBox.display("Eroare", "Tip de data pentru motivat invalid!\n");
                }
            }
        });
        uitat.setOnAction(e -> {
            Tema t = comboBoxTema.getValue();
            Student s = controller.cautaStudentNume(numeText.getText(), prenumeText.getText());
            if (s == null) {
                AlertBox.display("Eroare", "Studentul nu exista\n");
            } else {
                try {
                    if (controller.adaugaNotaUitat(s.getId(), t.getId()) == null) {
                        fereastraNotaSiFeedback((float) 10);
                        if (rezultat) {
                            try {
                                float nota = Float.parseFloat(punctajText.getText());
                                try {
                                    //cand merge, aici sunt
                                    if (controller.modificaNotaUitat(s.getId(), t.getId(), nota, textFeedback.getText()) == null) {
                                        AlertBox.display("Nota", "Nota a fost adaugata cu succes!\n");
                                        incarcaNote();
                                        rezultat = true;
                                    } else
                                        AlertBox.display("Eroare", "Nota nu poate fi modificata!\n");
                                } catch (ValidationException ve) {
                                    AlertBox.display("Eroare", ve.getMessage());
                                }
                            } catch (NumberFormatException nfe) {
                                AlertBox.display("Eroare", "Tip de data pentru nota invalid!\n");
                                controller.stergeNota(s.getId(), t.getId());
                            }
                        } else {
                            controller.stergeNota(s.getId(),t.getId());
                        }
                    } else {
                        AlertBox.display("Eroare", "Nota exista deja!\n");
                    }
                } catch (ValidationException v) {
                    AlertBox.display("Eroare", v.getMessage());
                }
            }
        });
        layoutDreapta.getChildren().addAll(temeComboBoxLayout, numeEtichetaSiText, prenumeEtichetaSiText, motivatEtichetaSiText, adaugaNota, uitat);
    }

    private void creeazaLayoutRapoarte() {
        HBox layoutPrincipal = new HBox(5);
        VBox layoutStanga = new VBox(5);
        VBox layoutDreapta = new VBox(5);
        Button inapoi = new Button("_Inapoi");
        layoutPrincipal.getChildren().addAll(layoutStanga, layoutDreapta);
        layoutDreapta.setAlignment(Pos.CENTER);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutStanga.setAlignment(Pos.CENTER);
        sceneRapoarte = new Scene(layoutPrincipal, 400, 300);
        layoutStanga.getChildren().addAll(inapoi);
        inapoi.setOnAction(e -> window.setScene(scene));

        Label etichetaFiecareStudentNota = new Label("Raport cu media notelor fiecarui student");
        Label etichetaCeleMaiGreleTeme = new Label("Raport cu cele mai grele teme");
        Label etichetaStudentiAdmisiExamen = new Label("Raport cu studentii care pot intra in examen");
        Label etichetaStudentiCareAuPredatLaTimp = new Label("Raport cu studentii care au predat la timp");
        Button buttonFiecareStudentNota = new Button("Afisare");
        Button buttonCeleMaiGreleTeme = new Button ("Afisare");
        Button buttonStudentiAdmisiExamen = new Button("Afisare");
        Button buttonStudentiCareAuPredatLaTimp = new Button("Afisare");
        layoutDreapta.getChildren().addAll(etichetaFiecareStudentNota,buttonFiecareStudentNota,etichetaCeleMaiGreleTeme,buttonCeleMaiGreleTeme,etichetaStudentiAdmisiExamen,buttonStudentiAdmisiExamen,etichetaStudentiCareAuPredatLaTimp,buttonStudentiCareAuPredatLaTimp);


        buttonFiecareStudentNota.setOnAction(e->{
            Vector<Pair<Student,Float>> elemente =controller.notaFiecareStudent();
            StringBuilder mesaj = new StringBuilder("Lista studentilor cu media de laborator\n\n");
            ArrayList<PieChart.Data> l = new ArrayList<>();
            for(Pair<Student,Float> p : elemente){
                mesaj.append(p.getKey().getNume()+" "+p.getKey().getPrenume()+" are media la laboratoare "+p.getValue().toString()+"\n");
                l.add(new PieChart.Data(p.getKey().getNume()+" "+p.getKey().getPrenume(),(double)p.getValue()));
            }
            controller.sprePDF("media notelor studentilor",mesaj.toString());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(l);
            AlertBoxPieChart.display("Media fiecarui student",pieChartData,mesaj.toString(),"");
        });
        buttonCeleMaiGreleTeme.setOnAction(e->{
            Vector<Pair<Tema,Float>> elemente = controller.ceaMaiGreaTema();
            StringBuilder mesaj = new StringBuilder("Lista celor mai grele teme\n\n");
            ArrayList<PieChart.Data> l = new ArrayList<>();
            for(Pair<Tema,Float> p : elemente){
                mesaj.append(p.getKey().getDescriere()+ " cu media "+p.getValue().toString()+"\n");
            }
            int nrTeme = controller.getSizeTeme();
            l.add(new PieChart.Data("Teme mai usoare",(float)100 - (float)elemente.size()/nrTeme*100));
            l.add(new PieChart.Data("Teme grele",(float)elemente.size()/nrTeme*100));
            controller.sprePDF("teme grele",mesaj.toString());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(l);
            AlertBoxPieChart.display("Cele mai grele teme",pieChartData,mesaj.toString(),"%");

        });
        buttonStudentiAdmisiExamen.setOnAction(e->{
            Vector<Student> elemente = controller.studentiCarePotIntraInExamen();
            StringBuilder mesaj = new StringBuilder("Lista studentilor care pot intra in examen\n\n");
            ArrayList<PieChart.Data> l = new ArrayList<>();
            for(Student s : elemente){
                mesaj.append(s.getNume()+" "+s.getPrenume()+" "+s.getGrupa()+"\n");

            }
            int nrStudenti = controller.getSizeStudenti();
            l.add(new PieChart.Data("Neintrati",nrStudenti-elemente.size()));
            l.add(new PieChart.Data("Intrati",elemente.size()));
            controller.sprePDF("studenti examen",mesaj.toString());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(l);
            AlertBoxPieChart.display("Studenti eligibili pentru examen",pieChartData,mesaj.toString(),"");

        });
        buttonStudentiCareAuPredatLaTimp.setOnAction(e->{
            Vector<Student> elemente = controller.studentiCareAuPredatLaTimp();
            StringBuilder mesaj = new StringBuilder("Lista studentilor care au predat la timp\n\n");
            ArrayList<PieChart.Data> l = new ArrayList<>();
            for(Student s : elemente){
                mesaj.append(s.getNume()+" "+s.getPrenume()+" "+s.getGrupa()+"\n");
            }
            int nrStudenti = controller.getSizeStudenti();
            l.add(new PieChart.Data("N-au predat la timp",nrStudenti-elemente.size()));
            l.add(new PieChart.Data("Au predat la timp",elemente.size()));
            controller.sprePDF("predat la timp",mesaj.toString());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(l);
            AlertBoxPieChart.display("Studenti care au predat la timp",pieChartData,mesaj.toString(),"");
        });
    }

    private void arataLayoutStudenti() {
        window.setScene(sceneStudenti);
    }

    private void arataLayoutTeme() {
        window.setScene(sceneRapoarte);
    }

    private void arataLayoutNote() {
        window.setScene(sceneNote);
    }

    private void conecteazaButoane() {
        spreStudenti.setOnAction(e -> {
            arataLayoutStudenti();
        });
        spreRapoarte.setOnAction(e -> {
            arataLayoutTeme();
        });
        spreNote.setOnAction(e -> {
            arataLayoutNote();
        });
    }


    public void start(Stage stage) {
        window = stage;
        initializeazaFereastraPrincipala();
        creeazaLayoutStudenti();
        creeazaLayoutNote();
        creeazaLayoutRapoarte();
        window.setScene(scene);
        window.show();
        conecteazaButoane();
    }

    public void run(String[] args) {
        launch(args);
    }
}
