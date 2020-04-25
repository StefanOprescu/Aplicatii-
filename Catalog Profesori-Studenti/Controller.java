package me.map.lab3.controller;

import javafx.util.Pair;
import me.map.lab3.domain.AnUniversitar;
import me.map.lab3.domain.Nota;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.services.ServiceNote;
import me.map.lab3.services.ServiceStudenti;
import me.map.lab3.services.ServiceTeme;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Controller {
    private ServiceNote sn;
    private ServiceStudenti ss;
    private ServiceTeme st;
    public Controller(ServiceStudenti ss, ServiceTeme st, ServiceNote sn) {
        this.sn = sn;
        this.ss = ss;
        this.st = st;
    }

    /**
     * Operatiile pentru anul universitar
     */
    public Integer getSaptamanaCurenta() {
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return a.getSaptamanaCurenta();
    }

    /**
     * Operatiile service-ului de studenti
     */
    public Student adaugaStudent(Integer id, String nume, String prenume, String grupa, String email, String profIndrumator) throws ValidationException{
        return ss.adauga(id,nume,prenume,grupa,email,profIndrumator);
    }
    public Student cautaStudent(Integer id) { return ss.cauta(id); }
    public Student stergeStudent(Integer id) { return ss.sterge(id); }
    public Student modificaStudent(Integer id, String nume, String prenume, String grupa, String email, String profIndrumator) throws ValidationException{
        return ss.modifica(id,nume,prenume,grupa,email,profIndrumator);
    }
    public Iterable<Student> getStudenti() { return ss.getStudenti(); }

    /**
     * Operatiile service-ului de teme
     */
    public Tema adaugaTema(Integer id, String descriere, Integer deadline) throws ValidationException{
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return st.adauga(id,descriere,deadline,a);
    }
    public Tema cautaTema(Integer id) { return st.cauta(id); }
    public Tema stergeTema(Integer id) { return st.sterge(id); }
    public Tema modificaTema(Integer id, String descriere, Integer deadline) throws ValidationException {
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return st.modifica(id,descriere,deadline,a);
    }
    public Iterable<Tema> getTeme() { return st.getElems(); }

    /**
     * Operatiile service-ului de note
     */
    public Nota adaugaNota(Integer idStudent ,Integer idTema) throws ValidationException {
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        return sn.daTema(student,tema);
    }
    public Nota cautaNota(String id) { return sn.cauta(id); }
    public Nota stergeNota(Integer idS, Integer idT) { return sn.sterge(idS,idT); }
    public Float getNotaMaxima(Integer idStudent,Integer idTema){ return sn.getNotaMaxima(idStudent,idTema); }
    public Nota modificaNota(Integer idStudent, Integer idTema, Float n, Integer motivat, String feedback) throws ValidationException {
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return sn.modifica(student,tema,n,motivat,feedback,a);
    }

    public Nota adaugaNotaMotivat(Integer idStudent, Integer idTema, Integer motivat) throws ValidationException {
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return sn.adaugaNota(student,tema,a,motivat);
    }
    public Iterable<Nota> getNote() { return sn.getElems(); }

    public List<Student> totiStudentiiUneiGrupe(String gr){
        Predicate<Student> p = x->x.getGrupa().equals(gr);
        return ss.totiStudentiiUneiGrupe(p);
    }

    public List<Student> totiStudentiiCuOTema(Integer id){
        Function<Nota,Student> f =x->{
            if(x.getTema().getId().equals(id))
                return x.getStudent();
            return null;};
        return sn.filterTotiStudentiiNote(f);
    }

    public List<Student> totiStudentiiCuOTemaSiProfesor(Integer id, String prof){
        Function<Nota,Student> f =x->{
        if(x.getTema().getId().equals(id) && x.getStudent().getCadruDidacticIndrumator().equals(prof))
            return x.getStudent();
        return null;};
        return sn.filterTotiStudentiiNote(f);
    }

    public List<Nota> toateNoteleCuOSaptamanaSiOTema(Integer idTema, Integer sapt){
        Predicate<Nota> p = x-> x.getPredataIn().equals(sapt) && x.getTema().getId().equals(idTema);
        return sn.filterToateNotele(p);
    }

    public Student cautaStudentNume(String nume, String prenume){ return ss.cautaStudentNume(nume,prenume); }

    public Tema ceaMaiApropiataTemaDeOData(){
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        return st.temaCeaMaiApropiataDeOData(a);
    }

    public boolean maiPoateFiPredata(Integer idStudent, Integer idTema){
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        if(sn.poateFiPredata(student,tema,a))
            return true;
        sn.sterge(idStudent,idTema);
        return false;
    }

    public Nota adaugaNotaUitat(Integer idStudent, Integer idTema) throws ValidationException {
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        return sn.adaugaNotaUitat(student,tema,tema.getDeadlineWeek());
    }

    public Nota modificaNotaUitat(Integer idStudent, Integer idTema, Float n, String feedback) throws ValidationException {
        Student student = ss.cauta(idStudent);
        Tema tema = st.cauta(idTema);
        return sn.modificaUitat(student,tema,n,feedback);
    }

    public void modificaStudentNote(Integer id, String nume, String prenume, String grupa, String email, String profesor){
        sn.modificaStudent(id, nume, prenume, grupa, email, profesor);
    }

    public Vector<Pair<Student,Float>> notaFiecareStudent(){
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        Iterable<Student> studenti = ss.getStudenti();
        Iterable<Tema> teme = st.getElems();
        return sn.notaFiecareStudent(studenti, teme,a);
    }

    public Vector<Student> studentiCarePotIntraInExamen(){
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        Iterable<Student> studenti = ss.getStudenti();
        Iterable<Tema> teme = st.getElems();
        return sn.studentiCarePotIntraInExamen(studenti, teme,a);
    }

    public Vector<Student> studentiCareAuPredatLaTimp(){
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        Iterable<Student> studenti = ss.getStudenti();
        Iterable<Tema> teme = st.getElems();
        return sn.studentiCareAuPredatLaTimp(studenti,teme,a); }

    public Vector<Pair<Tema,Float>> ceaMaiGreaTema(){
        Date d = new Date(System.currentTimeMillis());
        AnUniversitar a = new AnUniversitar(d);
        Iterable<Student> studenti = ss.getStudenti();
        Iterable<Tema> teme = st.getElems();
        return sn.ceaMaiGreaTema(studenti, teme,a);
    }

    public void sprePDF(String titlu,String mesaj){ sn.exportaSprePDF(titlu,mesaj); }

    public int getSizeStudenti(){
        return ss.getSize();
    }

    public int getSizeTeme(){
        return st.getSize();
    }
}
