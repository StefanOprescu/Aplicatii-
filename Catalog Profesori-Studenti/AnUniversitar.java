package me.map.lab3.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AnUniversitar {
    private int saptamanaCurenta;
    private Date acum;
    private Semestrul1 sem1;
    private Semestrul2 sem2;
    public AnUniversitar(Date acm){
        acum = acm;
        //acum = new Date(System.currentTimeMillis());
        //try { acum = formatData.parse("0-10-2019");}
        //catch(ParseException p){return;}
        sem1 = new Semestrul1("Fisiere\\Semestrul1.txt");
        sem2 = new Semestrul2("Fisiere\\Semestrul2.txt");
    }

    /**
     * @return true daca data actuala e mai mica decat 20-01-2020, false altfel
     */
    private boolean eInSemestrul1(){ return acum.before(sem1.getSfarsitSemestru()); }

    /**
     * @return true daca data actuala e strict mai mare decat 23-02-2020, false altfel
     */
    private boolean eInSemestrul2(){ return acum.after(sem2.getInceputSemestru()); }

    /**
     * @return numarul 2, reprezentand numarul de saptamani ale vacantei, in cazul in care data curenta a trecut de sfarsitul vacantei, 0 altfel
     */
    private int eDupaVacantaIarna(){
        if(acum.after(sem1.getSfarsitVacantaIarna())) {
            //long diferentaMilisecunde = Math.abs(sem1.getSfarsitVacantaIarna().getTime() - sem1.getInceputVacantaIarna().getTime());
            //long diferenta = TimeUnit.DAYS.convert(diferentaMilisecunde, TimeUnit.MILLISECONDS);
            //return (int)diferenta/7;
            return 2;
        }
        else
            return 0;
    }

    /**
     * @return numarul 1, reprezentand numarul de saptamani ale vacantei, in cazul in care data curenta a trecut de sfarsitul vacantei, 0 altfel
     */
    private int eDupaVacantaPrimavara(){
        if(acum.after(sem2.getSfarsitVacantaPrimavara())) {
            //long diferentaMilisecunde = Math.abs(sem2.getSfarsitVacantaPrimavara().getTime() - sem2.getInceputVacantaPrimavara().getTime());
            //long diferenta = TimeUnit.DAYS.convert(diferentaMilisecunde, TimeUnit.MILLISECONDS);
            //return (int) diferenta / 7 + 1;
            return 1;
        }
        else
            return 0;
    }

    /**
     * Calculeaza saptamana, luand numarul de milisecunde diferenta dintre data actuala si data de inceput a semestrului, pe care il convertim in numar de saptamani
     */
    private void calculeazaSaptamana(){
        if(eInSemestrul1()){
            long diferentaMilisecunde = Math.abs(acum.getTime()-sem1.getInceputSemestru().getTime());
            long diferenta = TimeUnit.DAYS.convert(diferentaMilisecunde, TimeUnit.MILLISECONDS);
            saptamanaCurenta = (int)diferenta/7+1-eDupaVacantaIarna();
        }
        else if(eInSemestrul2()){
            long diferentaMilisecunde = Math.abs(acum.getTime()-sem2.getInceputSemestru().getTime());
            long diferenta = TimeUnit.DAYS.convert(diferentaMilisecunde, TimeUnit.MILLISECONDS);
            saptamanaCurenta = (int)diferenta/7+1-eDupaVacantaPrimavara();
        }
        else
            saptamanaCurenta=-1;
    }

    /**
     * @return saptamana curenta semestrului
     */
    public int getSaptamanaCurenta(){
        calculeazaSaptamana();
        return saptamanaCurenta; }
}
