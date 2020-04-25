package me.service;

import me.exceptii.ValidationException;
import me.model.*;

import java.util.ArrayList;
import java.util.List;

public interface IService {
     void login(Angajat angajat, IProbaObserver client) throws ServiceException;
     void logout(Angajat angajat, IProbaObserver client) throws ServiceException;
     void check(Angajat angajat) throws ServiceException;
     List<ProbaDTO> getProbeDTO() throws ServiceException;
     List<ParticipantDTO> getParticipantiDTOcuProbaP(int p) throws ServiceException;
     List<Participant> findParticipantiDupaNume(String nume) throws ServiceException;
     Angajat getAngajat(String username, String parola) throws ServiceException;
     void saveProbaParticipant(ArrayList<Integer> probe, Integer idParticipant) throws ServiceException, ValidationException;
     Participant saveParticipant(Integer id, String nume, int varsta) throws ServiceException, ValidationException;
}
