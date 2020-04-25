package me.map.lab3.services;

import me.map.lab3.domain.Student;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.RepoStudenti;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ServiceStudenti {
    private RepoStudenti r;
    public ServiceStudenti(RepoStudenti r) { this.r=r; }

    /**
     * @param id-ul Studentului
     * @param nume-le studentului
     * @param prenume-le studentului
     * @param grupa studentului
     * @param email-ul studentului
     * @param profIndrumator al studentului
     * @return null daca studentul format pe baza parametrilor a fost adaugat in repo, altfel returneaza studentul
     * @throws ValidationException daca studentul nu este valid
     */
    public Student adauga(Integer id, String nume, String prenume, String grupa, String email, String profIndrumator) throws ValidationException {
        Student s = new Student(id,nume,prenume,grupa,email,profIndrumator);
        return r.save(s);
    }

    /**
     * @param id-ul pentru care se face stergerea
     * @return studentul care are id-ul id, in cazul in care id-ul se gaseste in lista, altfel null
     */
    public Student sterge(Integer id){
        return r.delete(id);
    }

    /**
     * @param id-ul Studentului
     * @param nume-le studentului
     * @param prenume-le studentului
     * @param grupa studentului
     * @param email-ul studentului
     * @param profIndrumator al studentului
     * @return null in cazul in care studentul exista si se modifica, studentul altfel
     * @throws ValidationException in cazul in care studentul nu exista
     */
    public Student modifica(Integer id, String nume, String prenume, String grupa, String email, String profIndrumator) throws ValidationException{
        Student s = new Student(id,nume,prenume,grupa,email,profIndrumator);
        return r.update(s);
    }

    /**
     * @param id-ul pentru care se face cautarea
     * @return studentul cu id-ul id in cazul in care exista, null altfel
     */
    public Student cauta(Integer id){ return r.findOne(id); }

    /**
     * @return lista de studenti
     */
    public Iterable<Student> getStudenti() { return r.findAll(); }

    /**
     * @return toti studentii unei grupe
     */
    //public Iterable<Student> getStudentiiUneiGrupe(String grupa) {
     //   Iterable<Student> vec = r.findAll();
      //  return StreamSupport.stream(vec.spliterator(),false).filter(x->
      //      x.getGrupa().equals(grupa)).collect(Collectors.toList());
    //}

    public List<Student> totiStudentiiUneiGrupe(Predicate<Student> p){
        List<Student> l = new ArrayList<>();
        r.findAll().forEach(l::add);
        return l.stream().filter(p).collect(Collectors.toList());
    }

    public Student cautaStudentNume(String nume, String prenume){
        Iterator<Student> studenti = r.findAll().iterator();
        while(studenti.hasNext()){
            Student s = studenti.next();
            if(s.getNume().equals(nume) && s.getPrenume().equals(prenume))
                return s;
        }
        return null;
    }

    public int getSize(){
        return r.getSize();
    }
}
