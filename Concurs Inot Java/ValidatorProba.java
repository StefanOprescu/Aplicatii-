package me.validator;

import me.exceptii.ValidationException;
import me.model.Proba;

public class ValidatorProba implements Validator<Proba> {
    public void validate(Proba p) throws ValidationException{
        String erori="";
        if(p==null)
            throw new ValidationException("Obiectul nu exista!\n");
        if(p.getStil()==null)
            throw new ValidationException("Campurile nu pot fi null!\n");
        if(p.getDistanta()<0)
            erori+="Distanta nu poate fi negativa!\n";
        if(!erori.equals(""))
            throw new ValidationException(erori);
    }
}
