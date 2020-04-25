package me.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import me.events.EvenimentSchimbare;
import me.events.TipEveniment;
import me.exceptii.ValidationException;
import me.model.*;
import me.observer.Observer;
import me.server.ServerException;
import me.service.IProbaObserver;
import me.service.IService;
import me.service.Service;
import me.service.ServiceException;
import me.utils.AlertBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class ControllerFereastraPrincipala implements Initializable, EventHandler, IProbaObserver {
    ObservableList<ProbaDTO> modelTabelProba = FXCollections.observableArrayList();
    ObservableList<ParticipantDTO> modelTabelParticipant = FXCollections.observableArrayList();
    private IService server;
    private Angajat user;

    ProbaDTO probaDTO;
    @FXML
    Button butonLogout,buttonCauta,butonAdauga;

    @FXML
    TableView tabelProbe, tabelParticipanti;

    @FXML
    TableColumn idProbaTabel,distantaTabel,stilTabel,participantiTabel;

    @FXML
    TableColumn coloanaNume, coloanaVarsta, coloanaListaProbe;

    @FXML
    TextField textFieldNume,textFieldVarsta;

    @Override
    public void handle(Event event) {
        if(event.getSource()==butonLogout){
            try {
               logout();
                ((Node) butonLogout).getScene().getWindow().hide();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        if(event.getSource()==buttonCauta){
            ProbaDTO p = (ProbaDTO) tabelProbe.getSelectionModel().getSelectedItem();
            incarcaListaParticipanti(p.getId());
        }
        if(event.getSource()==butonAdauga) {
            ObservableList<ProbaDTO> obs = tabelProbe.getSelectionModel().getSelectedItems();
            if (obs.size() == 0)
                AlertBox.display("Eroare", "Trebuie sa selectati cel putin o linie");
            else {
                probaDTO = obs.get(0);
                try {
                    int varsta = Integer.parseInt(textFieldVarsta.getText());
                    try {
                        Participant participant = server.saveParticipant(1, textFieldNume.getText(), varsta);
                        System.out.println(participant);
                        System.out.println(participant.getId());
                        ArrayList<Integer> probe = new ArrayList<>();
                        obs.forEach(x -> {
                                probe.add(x.getId());
                                //service.saveProbaParticipant(x.getId(), p.getId());
                        });
                        server.saveProbaParticipant(probe,participant.getId());
                        //incarcaListaProbe();
                        //service.notifyObservers(new EvenimentSchimbare(TipEveniment.PROBA));
                    } catch (Exception v) {
                        AlertBox.display("Eroare", v.getMessage());
                    }
                } catch (NumberFormatException e) {
                    AlertBox.display("Eroare", "Tip de data invalid pentru varsta!");
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        butonLogout.setOnAction(this);
        buttonCauta.setOnAction(this);
        butonAdauga.setOnAction(this);
        tabelProbe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        idProbaTabel.setCellValueFactory(new PropertyValueFactory<ProbaDTO,Integer>("id"));
        distantaTabel.setCellValueFactory(new PropertyValueFactory<ProbaDTO,Float>("distanta"));
        stilTabel.setCellValueFactory(new PropertyValueFactory<ProbaDTO, Stiluri>("stil"));
        participantiTabel.setCellValueFactory(new PropertyValueFactory<ProbaDTO,Integer>("nrParticipanti"));

        coloanaNume.setCellValueFactory(new PropertyValueFactory<ParticipantDTO,String>("nume"));
        coloanaVarsta.setCellValueFactory(new PropertyValueFactory<ParticipantDTO,Integer>("varsta"));
        coloanaListaProbe.setCellValueFactory(new PropertyValueFactory<ParticipantDTO,String>("listaProbe"));
    }

    public void setComponente(){
        //service.addObserver(this);
        incarcaListaProbe();
    }

    public void incarcaListaProbe(){
        modelTabelProba.clear();
        List<ProbaDTO> probe = new ArrayList<>();
        try {
            server.getProbeDTO().iterator().forEachRemaining(probe::add);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        modelTabelProba.setAll(probe);
        tabelProbe.setItems(modelTabelProba);
    }

    public void incarcaListaParticipanti(int p){
        modelTabelParticipant.clear();
        List<ParticipantDTO> participanti= null;
        try {
            participanti = server.getParticipantiDTOcuProbaP(p);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        modelTabelParticipant.setAll(participanti);
        tabelParticipanti.setItems(modelTabelParticipant);
    }

  /*  public void update(EvenimentSchimbare evenimentSchimbare){
        if(evenimentSchimbare.getTip().compareTo(TipEveniment.PROBA)==0){
            incarcaListaProbe();
            incarcaListaParticipanti(probaDTO.getId());
            textFieldNume.clear();
            textFieldVarsta.clear();
        }
    }
*/
    public void setServer(IService server){
        this.server = server;
    }

    public void setUser(Angajat crtUser){
        this.user = crtUser;
    }

    public void logout(){
        try {
            server.logout(user,this);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void participantInscris(List<Participant> participanti, List<ProbaDTO> probe) throws ServiceException{}
    public void commitParticipant(List<Participant> participant) throws ServiceException{}
    public void updateProbeDTO(List<ProbaDTO> probeDTO) throws ServiceException{
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                modelTabelProba.clear();
                modelTabelProba.setAll(probeDTO);
                tabelProbe.setItems(modelTabelProba);
            }
        });

    }
}
