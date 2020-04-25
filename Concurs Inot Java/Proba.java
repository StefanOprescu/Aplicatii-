package me.model;

import java.io.Serializable;

public class Proba extends Entity<Integer> implements Serializable {
    private float distanta;
    private Stiluri stil;

    public Proba(Integer id, float distanta, Stiluri stil) {
        super.setId(id);
        this.distanta = distanta;
        this.stil = stil;
    }

    public float getDistanta() {
        return distanta;
    }

    public void setDistanta(float distanta) {
        this.distanta = distanta;
    }

    public Stiluri getStil() {
        return stil;
    }

    public void setStil(Stiluri stil) {
        this.stil = stil;
    }

    @Override
    public String toString() {
        return "proba " + distanta + "m "+ stil;
    }
}
