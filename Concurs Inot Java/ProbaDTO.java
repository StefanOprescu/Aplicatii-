package me.model;

import java.io.Serializable;

public class ProbaDTO implements Serializable {
    private Integer id;
    private float distanta;
    private Stiluri stil;
    private int nrParticipanti;

    public ProbaDTO(Integer id, float distanta, Stiluri stil, int nrParticipanti) {
        this.id=id;
        this.distanta = distanta;
        this.stil = stil;
        this.nrParticipanti=nrParticipanti;
    }

    public Integer getId() {
        return id;
    }

    public float getDistanta() {
        return distanta;
    }

    public Stiluri getStil() {
        return stil;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }
}
