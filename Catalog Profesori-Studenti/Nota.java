package me.map.lab3.domain;

public class Nota extends IDNota{
    private Student student;
    private Tema tema;
    private Float nota;
    private Integer predataIn;
    private Float notaMaxima;
    private Integer motivat=0;
    private String feedback;
    private void calculeazaNotaMaxima(){
        if(predataIn > tema.getDeadlineWeek() && motivat==0)
            notaMaxima = (float) 10 - (predataIn-tema.getDeadlineWeek());
        else if (predataIn > tema.getDeadlineWeek() && motivat>0 && motivat <=2)
            notaMaxima = (float) 10 - (predataIn-tema.getDeadlineWeek()) + motivat;
        else
            notaMaxima = (float) 10;
    }

    /**
     * Constructor folosit pentru citirea din fisier
     */
    public Nota(Student student, Tema tema, Float nota, Integer predataIn, Integer motivat, String feedback) {
        super(student.getId(),tema.getId());
        this.student = student;
        this.tema = tema;
        this.nota = nota;
        this.predataIn = predataIn;
        this.motivat = motivat;
        this.feedback = feedback;
    }

    /**
     * Constructor folosit pentru acordarea unei teme unui student
     */
    public Nota(Student student, Tema tema){
        super(student.getId(),tema.getId());
        this.student=student;
        this.tema=tema;
        predataIn=0;
        nota=(float)0;
        motivat=0;
        feedback=" ";
    }

    public Nota(Student student, Tema tema,Integer predIn, Integer m){
        super(student.getId(),tema.getId());
        this.student=student;
        this.tema=tema;
        predataIn=predIn;
        nota=(float)1;
        motivat=m;
        feedback=" ";
        calculeazaNotaMaxima();
    }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Tema getTema() { return tema; }
    public String getNume(){
        return student.getNume();
    }
    public String getPrenume(){
        return student.getPrenume();
    }
    public String getDescriere(){
        return tema.getDescriere();
    }
    public String getGrupa(){
        return student.getGrupa();
    }
    public void setTema(Tema tema) { this.tema = tema; }
    public Float getNota() { return nota; }
    public void setNota(Float nota) { this.nota = nota; }
    public Integer getPredataIn() { return this.predataIn; }
    public void setPredataIn(Integer predataIn) { this.predataIn = predataIn; }
    public Float getNotaMaxima() {
        calculeazaNotaMaxima();
        return this.notaMaxima; }
    public void setMotivat(Integer motivat1){ motivat = motivat1; }
    public String getFeedback() { return this.feedback; }
    public void setFeedback(String s) { this.feedback = s; }
    public Integer getMotivat() { return motivat; }
    @Override
    public String toString() {
        return student.toString() +", " + tema.toString() + ", " + nota +", " + predataIn +", " + motivat + ", " + feedback +"";
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Nota)
        {
            Nota altaNota = (Nota)obj;
            return altaNota.getId().equals(this.getId());
        }
        return false;
    }

}