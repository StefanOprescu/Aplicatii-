package me.repository;

import me.model.Participant;
import me.model.Proba;
import me.model.ProbaParticipant;

import java.util.List;

public interface IRepoProbaParticipant extends Repository<String, ProbaParticipant> {
    int numarParticipantiProba(Integer p);
    List<ProbaParticipant> findProbaParticipantDupaProba(Integer p);
    List<ProbaParticipant> findProbaParticipantDupaParticipant(Integer p);
}
