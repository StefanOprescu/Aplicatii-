package me.map.lab3.teste;

import me.map.lab3.domain.AnUniversitar;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.RepoNote;
import me.map.lab3.services.ServiceNote;
import me.map.lab3.validator.ValidatorNota;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNote {
    private void testServiceNote(){
        try{
            File f = new File("Fisiere\\TesteNote.txt");
            if(!f.createNewFile())
                System.out.println("nu s-a putut crea!\n");
            RepoNote r = new RepoNote("Fisiere\\TesteNote.txt",new ValidatorNota());
            ServiceNote s = new ServiceNote(r,"Teste Note Exportate\\","Teste Note PDF\\");
            Student s1 = new Student(1,"Ion","Marin","225","ionmarin@scs.ubbcluj.ro","Pop Leon");
            Student s2 = new Student(2,"Gheorghe","Gigi","226","gg@scs.ubbcluj.ro","Marin Marin");
            Student s3 = new Student(3,"Numescu","Ioana","221","niir@scs.ubbcluj.ro","Ion Ioan");
            Tema t = new Tema(1,"a",5,6);
            Tema t2 = new Tema(5,"a",5,6);
            try {
                assert(s.daTema(s1,t)==null);
                assert(s.daTema(s1,t).getId().equals("1.1"));
                assert(s.daTema(s2,t)==null);
                assert(s.daTema(s2,t).getId().equals("2.1"));
                assert(s.daTema(s3,t)==null);
                assert(s.daTema(s3,t).getId().equals("3.1"));
            }
            catch(ValidationException e){
                assert(false);
            }
            assert(s.sterge(3,4)==null);
            assert(s.sterge(1,1).getId().equals("1.1"));
            try {
                assert(s.daTema(s1,t)==null);}
            catch(ValidationException e){
                assert(false);
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d = sdf.parse("07-11-2019");
                    AnUniversitar a = new AnUniversitar(d);
                    assert(s.modifica(s1, t, (float) 8, 0, "qqq",a)==null);
                }
                catch(ParseException e){return;}
            }
            catch(ValidationException e){
                assert(false);
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d = sdf.parse("14-11-2019");
                    AnUniversitar a = new AnUniversitar(d);
                    s.modifica(s1, t, (float) 8, 0, "qqq",a);
                }
                catch(ParseException e){return;}
            }
            catch(ValidationException e){
                assert(e.getMessage().equals("Nota nu isi poate modifica saptamana cand a fost predata!\n"));
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d = sdf.parse("08-11-2019");
                    AnUniversitar a = new AnUniversitar(d);
                    assert(s.modifica(s1, t2, (float) 8, 0, "qqq",a).getTema().getId()==5);
                }
                catch(ParseException e){return;}
            }
            catch(ValidationException e){
                assert(false);
            }
            if(f.delete())
                System.out.println("fisier sters");
            else
                System.out.println("fisier nesters");
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
    public void testAll(){
        testServiceNote();
    }
}
