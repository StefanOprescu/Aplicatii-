package me.networking;

import me.exceptii.ValidationException;
import me.model.*;
import me.service.IProbaObserver;
import me.service.IService;
import me.service.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProbaServicesObjectProxy implements IService {
    private String host;
    private int port;
    private IProbaObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ProbaServicesObjectProxy(String host, int port){
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingDeque<>();
    }

    private void closeConnection(){
        finished = true;
        try{
            input.close();
            output.close();
            connection.close();
            client = null;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void sendRequest(Request request) throws ServiceException{
        try{
            output.writeObject(request);
            output.flush();
        }
        catch (IOException e){
            throw new ServiceException("Error sending object "+ e);
        }
    }

    private Response readResponse() throws ServiceException{
        Response response = null;
        try{
            response = qresponses.take();
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        return response;
    }

    private void initializeConnection() throws ServiceException {
        try{
            connection = new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse response){
        if(response instanceof UpdateProbeDTOResponse){
            UpdateProbeDTOResponse r = (UpdateProbeDTOResponse)response;
            try {
                client.updateProbeDTO(r.getProbeDTO());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable {
        public void run(){
            while(!finished){
                try{
                    Object response = input.readObject();
                    System.out.println("response received "+ response);
                    if(response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    try {
                        qresponses.put((Response) response);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                catch(IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void login(Angajat angajat, IProbaObserver client) throws ServiceException{
        initializeConnection();
        sendRequest(new LoginRequest(angajat));
        Response response = readResponse();
        if(response instanceof OkResponse){
            this.client = client;
            return;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ServiceException(err.getMessage());
        }
    }

    public void logout(Angajat angajat, IProbaObserver client) throws ServiceException{
        sendRequest(new LogoutRequest(angajat));
        Response response = readResponse();
        closeConnection();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new ServiceException(err.getMessage());
        }
    }

    public void check(Angajat angajat) throws ServiceException{}
    public List<ProbaDTO> getProbeDTO() throws ServiceException{
        System.out.println("Getting probe DTO");
        sendRequest(new GetProbeDTORequest());
        Response response = readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new ServiceException(err.getMessage());
        }
        GetProbeDTOResponse gpr = (GetProbeDTOResponse) response;
        return gpr.getProbeDTO();
    }
    public List<ParticipantDTO> getParticipantiDTOcuProbaP(int p) throws ServiceException {
        System.out.println("Getting Participanti DTO cu Proba "+p);
        //initializeConnection();
        sendRequest(new GetParticipantiDTOCuProbaPRequest(p));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new ServiceException(err.getMessage());
        }
        GetParticipantiDTOCuProbaPResponse gppr = (GetParticipantiDTOCuProbaPResponse)response;
        return gppr.getpDTO();
    }
    public List<Participant> findParticipantiDupaNume(String nume) throws ServiceException {return null;}
    public Angajat getAngajat(String username, String parola) throws ServiceException{
        System.out.println("hi");
        //initializeConnection();
        sendRequest(new GetAngajatRequest(username,parola));
        Response response = readResponse();
        if(response instanceof OkResponse){
            GetAngajatResponse a = (GetAngajatResponse)response;
            return a.getAngajat();
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new ServiceException(err.getMessage());
        }
        return null;
    }
    public void saveProbaParticipant(ArrayList<Integer> probe, Integer idParticipant) throws ServiceException, ValidationException{
        System.out.println("Se salveaza " + probe + " " + idParticipant);
        //initializeConnection();
        sendRequest(new SaveProbeParticipantRequest(probe,idParticipant));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse)response;
            throw new ServiceException(err.getMessage());
        }
        System.out.println("s-a adaugat");
    }
    public Participant saveParticipant(Integer id, String nume, int varsta) throws ServiceException, ValidationException{
        System.out.println("Se salveaza participantul "+ id +" "+ nume);
        //initializeConnection();
        sendRequest(new SaveParticipantRequest(id,nume,varsta));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new ServiceException(err.getMessage());
        }
        SaveParticipantResponse svp = (SaveParticipantResponse)response;
        return svp.getParticipant();
    }
}
