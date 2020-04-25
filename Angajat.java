package me.model;

import java.io.Serializable;

public class Angajat extends Persoana implements Serializable {
    private String username;
    private String parola;

    public Angajat(Integer id, String nume, int varsta, String username, String parola) {
        super(id, nume, varsta);
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getParola() { return parola; }

    public void setParola(String parola) { this.parola = parola; }
}
