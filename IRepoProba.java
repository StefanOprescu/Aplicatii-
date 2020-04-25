package me.repository;

import me.model.Proba;
import me.model.Stiluri;

import java.util.List;

public interface IRepoProba extends Repository<Integer, Proba> {
    List<Proba> findProbeDupaStil(Stiluri stil);
    List<Proba> findProbeDupaDistanta(float distanta);
}
