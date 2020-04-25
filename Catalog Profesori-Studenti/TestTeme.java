package me.map.lab3.teste;

import me.map.lab3.domain.AnUniversitar;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.GenericRepo;
import me.map.lab3.repos.RepoTeme;
import me.map.lab3.services.ServiceTeme;
import me.map.lab3.validator.ValidatorTema;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TestTeme {
    private void testeazaCRUDTeme(){
        try{
            File f = new File("Fisiere\\TesteTeme.txt");
            f.createNewFile();
            RepoTeme r = new RepoTeme("Fisiere\\TesteTeme.txt",new ValidatorTema());
            Tema t = new Tema(1,"a",5,10);
            try {
                assert(r.save(t)==null);
                assert(r.save(t).getId()==1);
            }
            catch(ValidationException v){assert(false);}
            Tema t2 = new Tema(null,null,null,null);
            try{
                r.save(t2);
            }
            catch(ValidationException e){
                assert(e.getMessage().compareTo("Campurile nu trebuie sa fie null\n")==0);}
            try{
                r.save(null);
            }
            catch(ValidationException e){
                assert(e.getMessage().compareTo("Obiectul nu exista!\n")!=0);}
            Tema t4 = new Tema(2,"b",5,12);
            Tema t5 = new Tema(3,"c",6,7);
            try {
                r.save(t4);
            }
            catch(ValidationException v){assert(false);}
            try {
                r.save(t5);
            }
            catch(ValidationException v){assert(false);}
            Iterator<Tema> it = r.findAll().iterator();
            assert(it.next().getId()==1 && it.next().getId()==2 && it.next().getId()==3 && !it.hasNext());

            assert(r.delete(1).getId()==1);

            assert(r.findOne(2).getId()==2);
            try{
                r.delete(null);
            }
            catch(IllegalArgumentException e){
                assert(e.getMessage().compareTo("ID-ul nu trebuie sa fie null!")==0);
            }
            assert(r.delete(1)==null);
            assert(r.findOne(1)==null);
            try{
                r.findOne(null);
            }
            catch(IllegalArgumentException e){
                assert(e.getMessage().compareTo("ID-ul nu trebuie sa fie null!")==0);
            }
            Tema tNoua = new Tema (2,"zzz",9,10);
            try {
                r.update(tNoua);
            }
            catch(ValidationException v){assert(false);}
            assert(r.getSize()==2);


            if(f.delete())
                System.out.println("fisier sters");
            else
                System.out.println("fisier nesters");
        }
        catch(IOException e){ System.out.println(e.getMessage());}

    }
    private void testeazaServiceTeme(){
        try {
            File f = new File("D:\\Facultate\\Semestrul 3\\Metode Avansate de Programare\\Laborator\\Laborator 3\\Fisiere\\TesteTeme.txt");
            f.createNewFile();
            RepoTeme r = new RepoTeme("D:\\Facultate\\Semestrul 3\\Metode Avansate de Programare\\Laborator\\Laborator 3\\Fisiere\\TesteTeme.txt", new ValidatorTema());
            ServiceTeme s = new ServiceTeme(r);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                    Date d = sdf.parse("08-11-2019");
                    AnUniversitar a = new AnUniversitar(d);
                    try {
                        assert (s.adauga(1, "a", 10, a) == null);
                    } catch (ValidationException e) {
                        assert (false);
                    }
                } catch (ParseException e) {
                    return;
                }
            try {
                Date d = sdf.parse("8-11-2019");
                AnUniversitar a = new AnUniversitar(d);
                try {
                    s.adauga(1, "a", 2, a);
                } catch (ValidationException e) {
                    assert (e.getMessage().equals("Saptamana de inceput trebuie sa fie mai mica decat saptamana de sfarsit!\n"));
                }
            } catch (ParseException e) {
                return;
            }
            assert(s.sterge(1).getDescriere().equals("a"));
            try {
                Date d = sdf.parse("08-11-2019");
                AnUniversitar a = new AnUniversitar(d);
                try {
                    assert (s.adauga(2, "a", 10, a) == null);
                } catch (ValidationException e) {
                    assert (false);
                }
            } catch (ParseException e) {
                return;
            }
            assert(s.cauta(2).getDescriere().equals("a"));
            try {
                Date d = sdf.parse("08-11-2019");
                AnUniversitar a = new AnUniversitar(d);
                try {
                    s.modifica(2, "a", 1, a);
                } catch (ValidationException e) {
                    assert (e.getMessage().equals("Saptamana curenta nu poate fi mai mare decat deadline-ul\n"));
                }
            } catch (ParseException v) {
                return;
            }
            try {
                Date d = sdf.parse("08-11-2019");
                AnUniversitar a = new AnUniversitar(d);
                try {
                    assert (s.modifica(2, "b", 10, a) == null);
                } catch (ValidationException e) {
                    assert (false);
                }
            } catch (ParseException v) {
                return;
            }
            try {
                Date d = sdf.parse("08-11-2019");
                AnUniversitar a = new AnUniversitar(d);
                try {
                    assert (s.modifica(4, "b", 10, a).getDescriere().equals("b"));
                } catch (ValidationException e) {
                    assert (false);
                }
            } catch (ParseException v) {
                return;
            }
            assert(s.cauta(2).getDescriere().equals("b"));
            assert(s.cauta(4)==null);

        f.delete();
        }
        catch(IOException e){
                System.out.println(e.getMessage());
            }
    }
    private void testeazaDomeniuTema(){
        Tema t = new Tema(1,"a",10,12);
        assert(t.getDescriere().compareTo("a")==0);
        t.setDescriere("b");
        assert(t.getDescriere().compareTo("b")==0);
        t.setStartWeek(11);
        assert(t.getStartWeek()==11);
        t.setDeadlineWeek(13);
        assert(t.getDeadlineWeek()==13);
    }

    public void testAll(){
        testeazaDomeniuTema();
        testeazaCRUDTeme();
        testeazaServiceTeme();
    }
}
