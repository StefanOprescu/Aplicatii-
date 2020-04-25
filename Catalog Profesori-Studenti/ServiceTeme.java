package me.map.lab3.services;


import me.map.lab3.domain.AnUniversitar;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.RepoTeme;

import java.util.Iterator;


public class ServiceTeme {
    private RepoTeme r;
    public ServiceTeme(RepoTeme r) { this.r=r; }

    /**
     * @param id-ul temei
     * @param descriere-a temei
     * @param deadline-ul temei
     * @return null in cazul in care tema se adauga, altfel se returneaza tema
     * @throws ValidationException daca tema formata nu-i valida
     */
    public Tema adauga(Integer id, String descriere, Integer deadline, AnUniversitar a) throws ValidationException {
        Tema t = new Tema(id,descriere,a.getSaptamanaCurenta(),deadline);
        return r.save(t);
    }

    /**
     * @param id-ul dupa care se face cautarea temei
     * @return tema cu id-ul id daca exista in repo, null altfel
     */
    public Tema sterge(Integer id){ return r.delete(id); }

    /**
     * @param id-ul temei
     * @param descriere-a temei
     * @param deadline-ul temei
     * @return null in cazul in care tema se modificae, altfel se returneaza tema
     *  @throws ValidationException daca tema formata nu-i valida
     */
    public Tema modifica(Integer id, String descriere, Integer deadline, AnUniversitar a) throws ValidationException{
        if(a.getSaptamanaCurenta()>deadline)
            throw new ValidationException("Saptamana curenta nu poate fi mai mare decat deadline-ul\n");
        Tema old = r.findOne(id);
        if(old==null)
            return new Tema(id,descriere,a.getSaptamanaCurenta(),deadline);
        Tema t = new Tema(id,descriere,old.getStartWeek(),deadline);
        return r.update(t);
    }

    /**
     * @param id-ul dupa care se face cautarea temei
     * @return tema cu id-ul id daca exista, null altfel
     */
    public Tema cauta(Integer id){ return r.findOne(id); }

    /**
     * @return lista temelor
     */
   public Iterable<Tema> getElems() {return r.findAll();}

   public Tema temaCeaMaiApropiataDeOData(AnUniversitar a){
       Iterator<Tema> it = r.findAll().iterator();
       int minim = Integer.MAX_VALUE;
       Tema deReturnat = new Tema(1,"a",3,4);
       while(it.hasNext()){
           Tema t = it.next();
           if(Math.abs(t.getDeadlineWeek()-a.getSaptamanaCurenta())<minim) {
               minim = Math.abs(t.getDeadlineWeek()-a.getSaptamanaCurenta());
               deReturnat=t;
           }
       }
       return deReturnat;
   }

   public int getSize(){
       return r.getSize();
   }
}
