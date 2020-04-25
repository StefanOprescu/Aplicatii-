package me.map.lab3.teste;

import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.GenericRepo;
import me.map.lab3.repos.RepoStudenti;
import me.map.lab3.services.ServiceStudenti;
import me.map.lab3.validator.ValidatorStudent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TestStudenti {
    private void testeazaCRUDStudenti(){
        try{
            File f = new File("Fisiere\\TesteStudenti.txt");
            f.createNewFile();
            RepoStudenti r = new RepoStudenti("Fisiere\\TesteStudenti.txt",new ValidatorStudent());
            Student s = new Student(1,"Ion","Marin","225","ionmarin@scs.ubbcluj.ro","Pop Leon");
            try {
                assert(r.save(s)==null);
                assert(r.save(s).getNume().compareTo("Ion")==0);
            }
            catch(ValidationException v){assert(false);}
            Student s2 = new Student(null,"Ion","Marin","225","ionmarin@scs.ubbcluj.ro","Pop Leon");
            try{
                r.save(s2);
            }
            catch(ValidationException v){
            assert(v.getMessage().compareTo("Campurile nu trebuie sa fie null\n")==0);}
            try{
                r.save(null);
            }
            catch(ValidationException v){
                assert(v.getMessage().compareTo("Obiectul nu exista!\n")==0);}
            Student s3 = new Student(-2,"","","","","");
            try{
                r.save(s3);
            }
            catch(ValidationException v){
                assert(v.getMessage().compareTo("Numele nu trebuie sa fie vid\nID-ul nu trebuie sa fie <0\nGrupa nu trebuie sa fie vida\nPrenumele nu trebuie sa fie vid\nEmail-ul nu trebuie sa fie vid\nProfesorul nu trebuie sa fie vid\n")==0);}
            Student s4 = new Student(2,"Gheorghe","Gigi","226","gg@scs.ubbcluj.ro","Marin Marin");
            Student s5 = new Student(3,"Numescu","Ioana","221","niir@scs.ubbcluj.ro","Ion Ioan");
            try {
                r.save(s4);
                r.save(s5);
            }
            catch(ValidationException v){assert(false);}
            Iterator<Student> it = r.findAll().iterator();
            assert(it.next().getId()==1 && it.next().getId()==2 && it.next().getId()==3 && !it.hasNext());
            Student deActualizat = new Student (1,"Catavencu","Vasilica","223","cvir4523@scs.ubbcluj.ro","Marin Marin");
            try {
                r.update(deActualizat);
            }
            catch(ValidationException v){assert(false);}
            assert(r.delete(1).getId()==1);
            assert(r.delete(1)==null);
            assert(r.findOne(1)==null);
            assert(r.findOne(2).getId()==2);
            assert(r.getSize()==2);
            try{
                r.delete(null);
            }
            catch(IllegalArgumentException e){
                assert(e.getMessage().compareTo("ID-ul nu trebuie sa fie null!")==0);
            }
            try{
                r.findOne(null);
            }
            catch(IllegalArgumentException e){
                assert(e.getMessage().compareTo("ID-ul nu trebuie sa fie null!")==0);
            }


            if(f.delete())
                System.out.println("fisier sters");
            else
                System.out.println("fisier nesters");
        }
        catch(IOException e){ System.out.println(e.getMessage());}

    }

    private void testeazaServiceStudent(){
        try {
            File f = new File("D:\\Facultate\\Semestrul 3\\Metode Avansate de Programare\\Laborator\\Laborator 3\\Fisiere\\TesteStudenti.txt");
            f.createNewFile();
            RepoStudenti r = new RepoStudenti("D:\\Facultate\\Semestrul 3\\Metode Avansate de Programare\\Laborator\\Laborator 3\\Fisiere\\TesteStudenti.txt", new ValidatorStudent());
            ServiceStudenti service = new ServiceStudenti(r);
            try {
                assert(service.adauga(1, "Ion", "Marin", "225", "ionmarin@scs.ubbcluj.ro", "Pop Leon")==null);
            }
            catch(ValidationException e){
                assert(false);
            }
            try{
                assert(service.modifica(1, "Ion", "Marian", "226", "ionmarin@scs.ubbcluj.ro", "Pop Leon")==null);
            }
            catch(ValidationException e){
                assert(false);
            }
            try{
                assert(service.modifica(2, "Ion", "Marian", "226", "ionmarin@scs.ubbcluj.ro", "Pop Leon").getId().equals(2));
            }
            catch(ValidationException e){
                assert(false);
            }
            assert(service.sterge(1).getPrenume().equals("Marian"));
            assert(service.sterge(2)==null);
            assert(service.cauta(1)==null);
            try {
                assert(service.adauga(1, "Ion", "Marin", "225", "ionmarin@scs.ubbcluj.ro", "Pop Leon")==null);
            }
            catch(ValidationException e){
                assert(false);
            }
            assert(service.cauta(1).getPrenume().equals("Marin"));
            f.delete();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void testeazaDomeniuStudent(){
        Student s = new Student(1,"Ion","Marin","225","ionmarin@scs.ubbcluj.ro","Pop Leon");
        assert(s.getNume().compareTo("Ion")==0);
        s.setNume("Ioon");
        assert(s.getNume().compareTo("Ioon")==0);
        assert(s.getId()==1);
        s.setPrenume("Ioan");
        assert(s.getPrenume().compareTo("Ioan")==0);
        s.setGrupa("221");
        assert(s.getGrupa().compareTo("221")==0);
        s.setEmail("as");
        assert(s.getEmail().compareTo("as")==0);
        s.setCadruDidacticIndrumator("ABCD");
        assert(s.getCadruDidacticIndrumator().compareTo("ABCD")==0);
    }
    public void testAll(){
        testeazaDomeniuStudent();
        testeazaCRUDStudenti();
        testeazaServiceStudent();
    }
}
