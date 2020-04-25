package me.model;

import java.io.Serializable;

public class Participant extends Persoana implements Serializable {
    public Participant(Integer id, String nume, int varsta){
        super(id,nume,varsta);
    }
}
