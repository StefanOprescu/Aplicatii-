package me.map.lab3.domain;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private String grupa;
    private String email;
    private String cadruDidacticIndrumator;
    public Student(){}
    public Student(Integer ID,String nume, String prenume, String grupa, String email, String cadruDidacticIndrumator) {
        super.setId(ID);
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumator = cadruDidacticIndrumator;
    }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }
    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }
    public String getGrupa() { return grupa; }
    public void setGrupa(String grupa) { this.grupa = grupa; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCadruDidacticIndrumator() { return cadruDidacticIndrumator; }
    public void setCadruDidacticIndrumator(String cadruDidacticIndrumator) {
        this.cadruDidacticIndrumator = cadruDidacticIndrumator;
    }



//10`Negrila`Ioana`225`niir2567@scs.ubbcluj.ro`Stroe Adrian

    @Override
    public String toString() {
        return  super.getId().toString() +", "+nume+", "+prenume+", "+grupa+", "+email+", "+cadruDidacticIndrumator+"";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student)
        {
            Student altStd = (Student)obj;
            return altStd.getId()==this.getId();
        }
        return false;
    }


}
