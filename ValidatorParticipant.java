package me.validator;

import me.exceptii.ValidationException;
import me.model.Participant;

public class ValidatorParticipant implements Validator<Participant> {
    public void validate(Participant p) throws ValidationException{
        String erori = "";
        if(p==null)
            throw new ValidationException("Obiectul nu exista!\n");
        if(p.getNume()==null  || p.getId()==null)
            throw new ValidationException("Campurile nu trebuie sa fie null");
        if(p.getNume().equals(""))
            erori+="Numele nu poate fi gol!\n";
        if(p.getVarsta()<0)
            erori+="Varsta nu poate fi mai mica un numar negativ!\n";
        if(!erori.equals(""))
            throw new ValidationException(erori);
    }
}
