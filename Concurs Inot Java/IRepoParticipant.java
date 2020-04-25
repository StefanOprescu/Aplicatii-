package me.repository;

import me.model.Participant;

import java.util.List;

public interface IRepoParticipant extends Repository<Integer, Participant> {
    List<Participant> findParticipantiDupaNume(String nume);
    List<Participant> findParticipantiDupaVarsta(int i);
}
