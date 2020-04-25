package me.networking;

public class SaveParticipantRequest implements Request {
    private int id;
    private String nume;
    private int varsta;

    public SaveParticipantRequest(int id, String nume, int varsta) {
        this.id = id;
        this.nume = nume;
        this.varsta = varsta;
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
}
