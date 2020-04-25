package me.map.lab3.domain;

public class Tema extends Entity<Integer>{
    private String descriere;
    private Integer startWeek;
    private Integer deadlineWeek;
    public Tema() {}
    public Tema(Integer id,String descriere, Integer startWeek, Integer deadlineWeek) {
        super.setId(id);
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    /**
     * @return descrierea temei
     */
    public String getDescriere() { return descriere; }

    /**
     * @param descriere : seteaza descrierea cu una noua
     */
    public void setDescriere(String descriere) { this.descriere = descriere; }

    /**
     * @return this.startWeek
     */
    public Integer getStartWeek() { return startWeek; }

    /**
     * @param startWeek: seteaza saptamana de start cu una noua
     */
    public void setStartWeek(Integer startWeek) { this.startWeek = startWeek; }

    /**
     * @return saptamana in care trebuie predata tema
     */
    public Integer getDeadlineWeek() { return deadlineWeek; }

    /**
     * @param deadlineWeek: seteaza saptamana de deadline cu una noua
     */
    public void setDeadlineWeek(Integer deadlineWeek) { this.deadlineWeek = deadlineWeek; }



    @Override
    public String toString() {
        return  descriere;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Tema)
        {
            Tema altaTema = (Tema)obj;
            return altaTema.getId()==this.getId();
        }
        return false;
    }
}
