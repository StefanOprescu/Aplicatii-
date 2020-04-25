package me.map.lab3.repos;

import me.map.lab3.domain.Student;
import me.map.lab3.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;

public class RepoStudenti  extends GenericRepo<Integer, Student>{
    public RepoStudenti(String fis, Validator<Student> val) {
        super(fis, val);
    }

    protected Student createEntity(String linie) {
        String[] params = linie.split("`");
        return new Student(Integer.parseInt(params[0]),params[1],params[2],params[3],params[4],params[5]);
    }

    protected String createString(Student s) {
        return s.getId()+"`"+s.getNume()+"`"+s.getPrenume()+"`"+s.getGrupa()+"`"+s.getEmail()+"`"+s.getCadruDidacticIndrumator()+"\n";
    }

    protected void updateaza(Student s, Student altStd){
        s.setPrenume(altStd.getPrenume());
        s.setNume(altStd.getNume());
        s.setCadruDidacticIndrumator(altStd.getCadruDidacticIndrumator());
        s.setEmail(altStd.getEmail());
        s.setGrupa(altStd.getGrupa());
    }

    protected void createEntitiesFromXML(Document doc) {
            NodeList elemente = doc.getElementsByTagName("student");
            for(int i = 0 ; i <elemente.getLength(); i ++)
            {
                Node curent = elemente.item(i);
                if (curent.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)curent;
                    Integer id = Integer.parseInt(element.getAttribute("id"));
                    String prenume = element.getElementsByTagName("prenume").item(0).getTextContent();
                    String nume = element.getElementsByTagName("nume").item(0).getTextContent();
                    String grupa = element.getElementsByTagName("grupa").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String profesor = element.getElementsByTagName("profesor").item(0).getTextContent();
                    elems.add(new Student(id,nume,prenume,grupa,email,profesor));
                }
        }
    }

    protected void addEntitiesToXML(Element rootElement, Document doc){
            for(Student s : elems) {
                Element student = doc.createElement("student");
                rootElement.appendChild(student);
                student.setAttribute("id",s.getId().toString());
                Element nume = doc.createElement("nume");
                nume.appendChild(doc.createTextNode(s.getNume()));
                Element prenume = doc.createElement("prenume");
                prenume.appendChild(doc.createTextNode(s.getPrenume()));
                Element grupa = doc.createElement("grupa");
                grupa.appendChild(doc.createTextNode(s.getGrupa()));
                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(s.getEmail()));
                Element profesor = doc.createElement("profesor");
                profesor.appendChild(doc.createTextNode(s.getCadruDidacticIndrumator()));
                student.appendChild(nume);
                student.appendChild(prenume);
                student.appendChild(grupa);
                student.appendChild(email);
                student.appendChild(profesor);
            }
    }

    protected void readFromPostgres(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "123123123");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM postgres.studenti");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                elems.add(new Student(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected String createSQLAdd(Student student) {
           return "INSERT INTO Studenti (idStudent,nume,prenume,grupa,email,profesor) "+
                    "VALUES (" + student.getId().toString() +",'"+student.getNume()+"','"+student.getPrenume()+"','"+student.getGrupa()+"','"+student.getEmail()+"','"+student.getCadruDidacticIndrumator()+ "');";
    }

    protected String createSQLUpdate(Student student){
     return "DELETE FROM Studenti WHERE idStudent="+student.getId().toString()+"; "+
            "INSERT INTO Studenti (idStudent,nume,prenume,grupa,email,profesor)"+
                    "VALUES (" + student.getId().toString() +",'"+student.getNume()+"','"+student.getPrenume()+"','"+student.getGrupa()+"','"+student.getEmail()+"','"+student.getCadruDidacticIndrumator()+ "');"; }

    protected String createSQLDelete(Student student){
        return "DELETE FROM Studenti WHERE idStudent="+student.getId().toString()+";";
    }
}
