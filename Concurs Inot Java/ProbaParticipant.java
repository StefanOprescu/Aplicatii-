package me.model;

import java.io.Serializable;

public class ProbaParticipant extends Entity<String> implements Serializable {
    private Integer idProba;
    private Integer idParticipant;
    public ProbaParticipant(Integer idProba, Integer idParticipant){
        super.setId(idProba.toString() + "." + idParticipant.toString());
        this.idProba = idProba;
        this.idParticipant = idParticipant;
    }

    public Integer getIdProba() { return idProba; }

    public Integer getIdParticipant() { return idParticipant; }
}
