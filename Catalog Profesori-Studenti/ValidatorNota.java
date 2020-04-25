package me.map.lab3.validator;

import me.map.lab3.domain.Nota;
import me.map.lab3.exceptii.ValidationException;

public class ValidatorNota implements Validator<Nota> {
    public void validate(Nota n) throws ValidationException {
        String erori = "";
        if(n==null)
            throw new ValidationException("Nota nu poate fi vida!\n");
        if(n.getStudent()==null || n.getTema()==null)
            throw new ValidationException("Campurile nu trebuie sa fie null\n");
        if(n.getPredataIn()!=0) {
            if (n.getNota() < 1 || n.getNota() > n.getNotaMaxima())
                erori += "Nota trebuie sa fie intre 1 si nota maxima acceptata(<=10)\n";
        }
        if(erori.length()!=0)
            throw new ValidationException(erori);
    }
}
