package me.model;

import java.io.Serializable;

public class ParticipantDTO implements Serializable {
    private int id;
    private String nume;
    private int varsta;
    private String listaProbe;

    public ParticipantDTO(int id, String nume, int varsta, String listaProbe) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
        this.listaProbe = listaProbe;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getListaProbe() {
        return listaProbe;
    }
}
