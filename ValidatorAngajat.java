package me.validator;

import me.exceptii.ValidationException;
import me.model.Angajat;

public class ValidatorAngajat implements Validator<Angajat> {
    public void validate(Angajat a) throws ValidationException {
        String erori = "";
        if(a==null)
            throw new ValidationException("Obiectul nu exista!\n");
        if(a.getNume()==null  || a.getId()==null || a.getParola()==null || a.getUsername()==null)
            throw new ValidationException("Campurile nu trebuie sa fie null");
        if(a.getNume().equals(""))
            erori+="Numele nu poate fi gol!\n";
        if(a.getVarsta()<0)
            erori+="Varsta nu poate fi mai mica un numar negativ!\n";
        if(a.getUsername().equals(""))
            erori+="Username-ul nu poate fi gol!\n";
        if(a.getParola().equals(""))
            erori+="Parola nu poate fi goala!\n";
        if(!erori.equals(""))
            throw new ValidationException(erori);
    }
}
