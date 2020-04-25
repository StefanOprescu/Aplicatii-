package me.networking;

import me.model.Angajat;

public class GetProbeDTORequest implements Request {
    private Angajat angajat;
    public Angajat getAngajat(){ return angajat; }
}
