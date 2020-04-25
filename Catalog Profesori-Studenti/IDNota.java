package me.map.lab3.domain;

public class IDNota extends Entity<String>{
    public IDNota(Integer idS, Integer idT) {
        super.setId(idS.toString()+"."+idT.toString());
    }
}
