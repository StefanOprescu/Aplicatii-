package me.map.lab3.validator;

import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;

public class ValidatorTema implements Validator<Tema> {
    @Override
    public void validate(Tema entity) throws ValidationException {
        String erori ="";
        if(entity==null)
            throw new ValidationException("Obiectul nu exista!");
        if(entity.getId()==null || entity.getDescriere()==null || entity.getStartWeek()==null || entity.getDeadlineWeek()==null)
            throw new ValidationException("Campurile nu trebuie sa fie null\n");
        if(entity.getStartWeek()<1 || entity.getStartWeek()>14)
            erori+="Saptamana de inceput trebuie sa fie intre 1 si 14!\n";
        if(entity.getDeadlineWeek()<1 || entity.getDeadlineWeek()>14)
            erori+="Saptamana de sfarsit trebuie sa fie intre 1 si 14!\n";
        if(entity.getStartWeek()>=entity.getDeadlineWeek())
            erori+="Saptamana de inceput trebuie sa fie mai mica decat saptamana de sfarsit!\n";
        if(erori.length()>0)
            throw new ValidationException(erori);
    }
}
