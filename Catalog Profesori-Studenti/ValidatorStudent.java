package me.map.lab3.validator;

import me.map.lab3.domain.Student;
import me.map.lab3.exceptii.ValidationException;

public class ValidatorStudent implements Validator<Student>{
    public void validate(Student student) throws ValidationException {
        String erori="";
        if(student==null)
            throw new ValidationException("Obiectul nu exista!\n");
        if(student.getNume()==null || student.getPrenume()==null || student.getId()==null || student.getCadruDidacticIndrumator()==null || student.getEmail()==null || student.getGrupa()==null)
            throw new ValidationException("Campurile nu trebuie sa fie null\n");
        if(student.getNume().compareTo("")==0)
            erori+="Numele nu trebuie sa fie vid\n";
        if(student.getId()<0)
            erori+="ID-ul nu trebuie sa fie <0\n";
        if(student.getGrupa().compareTo("")==0)
            erori+="Grupa nu trebuie sa fie vida\n";
        if(student.getPrenume().compareTo("")==0)
            erori+="Prenumele nu trebuie sa fie vid\n";
        if(student.getEmail().compareTo("")==0)
            erori+="Email-ul nu trebuie sa fie vid\n";
        if(student.getCadruDidacticIndrumator().compareTo("")==0)
            erori+="Profesorul nu trebuie sa fie vid\n";
        if(erori.length()!=0)
            throw new ValidationException(erori);

    }
}