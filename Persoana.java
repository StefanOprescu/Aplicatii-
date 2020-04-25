package me.model;

import java.io.Serializable;

abstract public class Persoana extends Entity<Integer> implements Serializable{
    private String nume;
    private int varsta;

    public Persoana(Integer id, String nume, int varsta) {
        super.setId(id);
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() { return nume; }

    public void setNume(String nume) { this.nume = nume; }

    public int getVarsta() { return varsta; }

    public void setVarsta(int varsta) { this.varsta = varsta; }

    @Override
    public String toString() {
        return nume+" "+varsta+" ani ";
    }
}
