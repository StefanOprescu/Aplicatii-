package me.service;

import me.model.Participant;
import me.model.ProbaDTO;

import java.util.List;

public interface IProbaObserver {
    void participantInscris(List<Participant> participanti, List<ProbaDTO> probe) throws ServiceException;
    void commitParticipant(List<Participant> participant) throws ServiceException;
    void updateProbeDTO(List<ProbaDTO> probeDTO) throws ServiceException;
}
