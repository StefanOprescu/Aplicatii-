package me.repository;

import me.model.Angajat;

import java.util.List;

public interface IRepoAngajat extends Repository<Integer,Angajat>{
    List<Angajat> findAngajatiNume(String nume);
    Angajat getUsernameParola(String username, String parola);
}
