package me.networking;

import me.model.Angajat;

public class GetAngajatResponse implements Response {
    private Angajat angajat;
    public GetAngajatResponse(Angajat a){
        angajat = a;
    }
    public Angajat getAngajat(){
        return angajat;
    }
}
