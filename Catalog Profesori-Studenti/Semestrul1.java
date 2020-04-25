package me.map.lab3.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Semestrul1 {
    private Date inceputSemestru;
    private Date inceputVacantaIarna;
    private Date sfarsitVacantaIarna;
    private Date sfarsitSemestru;
    private String fisier;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private void iaDateleDinFisier(){
        File f = new File(fisier);
        try{
            Scanner s = new Scanner(f);
            try {
                inceputSemestru = sdf.parse(s.next());
                inceputVacantaIarna = sdf.parse(s.next());
                sfarsitVacantaIarna = sdf.parse(s.next());
                sfarsitSemestru = sdf.parse(s.next());
            }
            catch(ParseException e){System.out.println("Nu se poate formata");}
        }
        catch(FileNotFoundException e){System.out.println("Fisierul nu exista");}
    }
    public Semestrul1(String fis){
        fisier=fis;
        iaDateleDinFisier();
    }

    public Date getInceputSemestru() {
        return inceputSemestru;
    }
    public Date getInceputVacantaIarna() {
        return inceputVacantaIarna;
    }
    public Date getSfarsitVacantaIarna() {
        return sfarsitVacantaIarna;
    }
    public Date getSfarsitSemestru() {
        return sfarsitSemestru;
    }
}
