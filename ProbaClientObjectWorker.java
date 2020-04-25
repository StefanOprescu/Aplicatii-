package me.networking;

import me.exceptii.ValidationException;
import me.model.Angajat;
import me.model.Participant;
import me.model.ParticipantDTO;
import me.model.ProbaDTO;
import me.server.ServerException;
import me.service.IProbaObserver;
import me.service.IService;
import me.service.ServiceException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ProbaClientObjectWorker implements Runnable, IProbaObserver {
    private IService server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ProbaClientObjectWorker(IService server,Socket connection){
        this.server = server;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(connected){
            try{
                Object request = input.readObject();
                System.out.println(request);
                Object response = handleRequest((Request)request);
                if(response!=null){
                    sendResponse((Response) response);
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try{
            input.close();
            output.close();
            connection.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response " +response);
        output.writeObject(response);
        output.flush();
    }

    private Response handleRequest(Request request){
        Response response = null;
        if(request instanceof LoginRequest){
            LoginRequest logReq = (LoginRequest)request;
            Angajat angajat = logReq.getUser();
            try{
                server.login(angajat,this);
                return new OkResponse();
            }catch (ServiceException e){
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof LogoutRequest){
            LogoutRequest logRq = (LogoutRequest)request;
            Angajat angajat = logRq.getUser();
            try{
                server.logout(angajat,this);
                connected = false;
                return new OkResponse();
            }
            catch (ServiceException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetAngajatRequest){
            System.out.println("hi");
            GetAngajatRequest gar = (GetAngajatRequest)request;
            try{
                Angajat a = server.getAngajat(gar.getUsername(),gar.getParola());
                return new GetAngajatResponse(a);
            }
            catch(ServiceException se){
                connected=false;
                return new ErrorResponse(se.getMessage());
            }
        }
        if(request instanceof GetProbeDTORequest){
            System.out.println("Request probe DTO");
            try {
                List<ProbaDTO> probaDTO = server.getProbeDTO();
                return new GetProbeDTOResponse(probaDTO);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof GetParticipantiDTOCuProbaPRequest){
            System.out.println("Request participanti DTO cu proba p");
            GetParticipantiDTOCuProbaPRequest request1 = (GetParticipantiDTOCuProbaPRequest)request;
            try {
                List<ParticipantDTO> participanti = server.getParticipantiDTOcuProbaP(request1.getProba());
                return new GetParticipantiDTOCuProbaPResponse(participanti);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof SaveParticipantRequest){
            System.out.println("Request save participant");
            SaveParticipantRequest request1 = (SaveParticipantRequest)request;
            try {
                Participant p = server.saveParticipant(request1.getId(),request1.getNume(),request1.getVarsta());
                return new SaveParticipantResponse(p);
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            } catch (ValidationException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if(request instanceof SaveProbeParticipantRequest){
            System.out.println("Request save probe participant");
            SaveProbeParticipantRequest request1 = (SaveProbeParticipantRequest)request;
            try {
                server.saveProbaParticipant(request1.getProbe(),request1.getIdParticipant());
            } catch (ServiceException e) {
                return new ErrorResponse(e.getMessage());
            } catch (ValidationException e) {
                return new ErrorResponse(e.getMessage());
            }
            return new SaveProbeParticipantResponse();
        }
        return response;
    }

    public void participantInscris(List<Participant> participanti, List<ProbaDTO> probe) throws ServiceException{
    }

    public void commitParticipant(List<Participant> participant) throws ServiceException{

    }

    public void updateProbeDTO(List<ProbaDTO> probeDTO) throws ServiceException{
        try {
            sendResponse(new UpdateProbeDTOResponse(probeDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
