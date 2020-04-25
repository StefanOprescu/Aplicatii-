package me.map.lab3.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Semestrul2 {
    private Date inceputSemestru2;
    private Date inceputVacantaPrimavara;
    private Date sfarsitVacantaPrimavara;
    private Date sfarsitSemestru;
    private String fisier;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private void iaDateleDinFisier(){
        try{
            File f = new File(fisier);
            Scanner s = new Scanner(f);
            try {
                inceputSemestru2 = sdf.parse(s.next());
                inceputVacantaPrimavara = sdf.parse(s.next());
                sfarsitVacantaPrimavara = sdf.parse(s.next());
                sfarsitSemestru = sdf.parse(s.next());
            }
            catch(ParseException e){return;}
        }
        catch(FileNotFoundException e){System.out.println("Fisierul nu exista");}
    }
    public Semestrul2(String fis){
        fisier=fis;
        iaDateleDinFisier();
    }

    public Date getInceputSemestru() {
        return inceputSemestru2;
    }
    public Date getInceputVacantaPrimavara() {
        return inceputVacantaPrimavara;
    }
    public Date getSfarsitVacantaPrimavara() {
        return sfarsitVacantaPrimavara;
    }
    public Date getSfarsitSemestru() {
        return sfarsitSemestru;
    }
}
