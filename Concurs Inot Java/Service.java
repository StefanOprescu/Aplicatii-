package me.service;

import me.events.EvenimentSchimbare;
import me.exceptii.ValidationException;
import me.model.*;
import me.observer.Observer;
import me.repository.IRepoAngajat;
import me.repository.IRepoParticipant;
import me.repository.IRepoProba;
import me.repository.IRepoProbaParticipant;
import me.validator.Validator;
import me.observer.Observable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService{
    private final int defaultThreadNo = 10;
    private IRepoProba repoProba;
    private IRepoParticipant repoParticipant;
    private IRepoAngajat repoAngajat;
    private IRepoProbaParticipant repoProbaParticipant;
    private Validator<Proba> validatorProba;
    private Validator<Angajat> validatorAngajat;
    private Validator<Participant> validatorParticipant;
    //private List<Observer<EvenimentSchimbare>>  observers;
    private Map<String,IProbaObserver> clientiLogati;

    public Service(IRepoProba repoProba, IRepoParticipant repoParticipant,
                   IRepoAngajat repoAngajat, IRepoProbaParticipant repoProbaParticipant,
                   Validator<Proba> validatorProba, Validator<Angajat> validatorAngajat,
                   Validator<Participant> validatorParticipant) {
        this.repoProba = repoProba;
        this.repoParticipant = repoParticipant;
        this.repoAngajat = repoAngajat;
        this.repoProbaParticipant = repoProbaParticipant;
        this.validatorProba = validatorProba;
        this.validatorAngajat = validatorAngajat;
        this.validatorParticipant = validatorParticipant;
        this.clientiLogati = new ConcurrentHashMap<>();
       // observers = new ArrayList<>();
    }
    /**
       REPO PROBE
     */
    public Proba getProbaID(Integer id){ return repoProba.get(id); }

    public Proba saveProba(Integer id, float distanta, String stil) throws ValidationException, ServiceException {
        Proba p = new Proba(id,distanta, Stiluri.valueOf(stil));
        validatorProba.validate(p);
        return repoProba.save(p);
    }

    public Iterable<Proba> getAllProbe(){
        return repoProba.findAll();
    }

    /**
       REPO PARTICIPANTI
     */
    public Participant getParticipantID(Integer id){ return repoParticipant.get(id); }

    public synchronized Participant saveParticipant(Integer id, String nume, int varsta) throws ValidationException, ServiceException{
        Participant p = new Participant(id,nume,varsta);
        validatorParticipant.validate(p);
        return repoParticipant.save(p);
    }

    public synchronized List<Participant> findParticipantiDupaNume(String nume) throws ServiceException{
        return repoParticipant.findParticipantiDupaNume(nume);
    }

    /**
       REPO ANGAJATI
     */
    public Angajat saveAngajat(Integer id, String nume, int varsta, String username, String parola) throws ValidationException{
        Angajat a = new Angajat(id,nume,varsta,username,parola);
        validatorAngajat.validate(a);
        return repoAngajat.save(a);
    }

    public synchronized Angajat getAngajat(String username, String parola) throws ServiceException{
        return repoAngajat.getUsernameParola(username,parola);
    }

    /**
       REPO PROBE-PARTICIPANTI
     */
    public synchronized void saveProbaParticipant(ArrayList<Integer> probe, Integer idParticipant) throws ServiceException{
        probe.forEach(x->{
            repoProbaParticipant.save(new ProbaParticipant(x,idParticipant));
        });
        notifyAngajatiAdaugare();
    }

    public List<ProbaParticipant> listaProbeParticipantiDupaParticipant(Integer idParticipant){
        return repoProbaParticipant.findProbaParticipantDupaParticipant(idParticipant);
    }

    public List<ProbaParticipant> listaProbeParticipantiDupaProba(Integer idProba){
        return repoProbaParticipant.findProbaParticipantDupaProba(idProba);
    }

    public synchronized List<ProbaDTO> getProbeDTO() throws ServiceException{
        List<ProbaDTO> probeDTO = new ArrayList<>();
        ArrayList<Proba> probe = new ArrayList<>();
        repoProba.findAll().forEach(probe::add);
        probe.forEach(x->{
            probeDTO.add(new ProbaDTO(x.getId(),x.getDistanta(),x.getStil(),repoProbaParticipant.findProbaParticipantDupaProba(x.getId()).size()));
        });
        return probeDTO;
    }

    public synchronized List<ParticipantDTO> getParticipantiDTOcuProbaP(int p) throws ServiceException{
        List<ParticipantDTO> participantiDTO = new ArrayList<>();
        ArrayList<Participant> participanti = new ArrayList<>();
        repoProbaParticipant.findProbaParticipantDupaProba(p).forEach(x->{
            participanti.add(repoParticipant.get(x.getIdParticipant()));
        });
        participanti.forEach(x->{
            String listaProbe = "";
            for (ProbaParticipant probaParticipant : repoProbaParticipant.findProbaParticipantDupaParticipant(x.getId())) {
                listaProbe+=repoProba.get(probaParticipant.getIdProba());
                listaProbe+=", ";
            }
            participantiDTO.add(new ParticipantDTO(x.getId(),x.getNume(),x.getVarsta(),listaProbe));
        });
        return participantiDTO;
    }

    private void afiseazaClienti(){
        clientiLogati.forEach((x,y)->
                System.out.println(x));
    }

    public synchronized void login(Angajat angajat, IProbaObserver client) throws ServiceException{
        if(angajat!=null){
            if(clientiLogati.get(angajat.getUsername())!=null){
                throw new ServiceException("User deja logat!\n");
            }
            clientiLogati.put(angajat.getUsername(),client);
            //System.out.println("AICIIIIII!!!");
            //afiseazaClienti();
        }
        else throw new ServiceException("Autentificare esuata!\n");
    }

    public synchronized void logout(Angajat angajat, IProbaObserver client) throws ServiceException{
        IProbaObserver localClient = clientiLogati.remove(angajat.getUsername());
        if(localClient==null)
            throw new ServiceException("Angajatul" + angajat.getUsername()+ " nu e logat");

    }
    public synchronized void check(Angajat angajat) throws ServiceException{
        List<Angajat> angajatList = (List<Angajat>) repoAngajat.findAll();
        boolean gasit = false;
        for(Angajat a : angajatList){
            if(a.getUsername().equals(angajat.getUsername()) && a.getParola().equals(angajat.getParola())) {
                gasit = true;
                break;
            }
        }
        if(!gasit)
            throw new ServiceException("Datele introduse nu sunt inregistrate!");
    }

    private synchronized void notifyAngajatiAdaugare(){
        Iterator<Angajat> angajatList = repoAngajat.findAll().iterator();
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadNo);
        angajatList.forEachRemaining(x-> {
                    IProbaObserver client = clientiLogati.get(x.getUsername());
                    System.out.println(x.getUsername() + " " + client);
                    if (client != null) {
                        System.out.println("am intrat in client not null");
                        System.out.println("se notifica " + client.toString());
                        executor.execute(() -> {
                            try {
                                System.out.println("se notifica " + x.getUsername());
                                List<ProbaDTO> probeDTO = getProbeDTO();
                                client.updateProbeDTO(probeDTO);
                            } catch (ServiceException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
        executor.shutdown();
    }

}
