package me.map.lab3.repos;

import me.map.lab3.domain.Nota;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;

public class RepoNote extends GenericRepo<String, Nota> {
    public RepoNote(String fis, Validator<Nota> val) {
        super(fis, val);
    }

    protected Nota createEntity(String linie) {
        String[] params = linie.split("`");
        Student s = new Student(Integer.parseInt(params[0]), params[1], params[2], params[3], params[4], params[5]);
        Tema t = new Tema(Integer.parseInt(params[6]), params[7], Integer.parseInt(params[8]), Integer.parseInt(params[9]));
        return new Nota(s, t, Float.parseFloat(params[10]), Integer.parseInt(params[11]), Integer.parseInt(params[12]), params[13]);
    }


    protected String createString(Nota nota) {
        return nota.getStudent().getId().toString() + "`" + nota.getStudent().getNume() + "`" +
                nota.getStudent().getPrenume() + "`" + nota.getStudent().getGrupa() + "`" +
                nota.getStudent().getEmail() + "`" + nota.getStudent().getCadruDidacticIndrumator() + "`" +
                nota.getTema().getId().toString() + "`" + nota.getTema().getDescriere() + "`" +
                nota.getTema().getStartWeek() + "`" + nota.getTema().getDeadlineWeek() + "`" +
                nota.getNota().toString() + "`" + nota.getPredataIn().toString() + "`" +
                nota.getMotivat().toString() + "`" + nota.getFeedback() + "\n";
    }


    protected void updateaza(Nota e1, Nota e2) {
        e1.getStudent().setNume(e2.getStudent().getNume());
        e1.getStudent().setPrenume(e2.getStudent().getPrenume());
        e1.getStudent().setGrupa(e2.getStudent().getGrupa());
        e1.getStudent().setEmail(e2.getStudent().getEmail());
        e1.getStudent().setCadruDidacticIndrumator(e2.getStudent().getCadruDidacticIndrumator());
        e1.getTema().setDeadlineWeek(e2.getTema().getDeadlineWeek());
        e1.getTema().setDescriere(e2.getTema().getDescriere());
        e1.setMotivat(e2.getMotivat());
        e1.setNota(e2.getNota());
        e1.setPredataIn(e2.getPredataIn());
        e1.setFeedback(e2.getFeedback());
    }

    protected void createEntitiesFromXML(Document doc) {
        NodeList elemente = doc.getElementsByTagName("nota");
        for (int i = 0; i < elemente.getLength(); i++) {
            Node curent = elemente.item(i);
            if (curent.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) curent;
                Integer idStudent = Integer.parseInt(element.getElementsByTagName("idStudent").item(0).getTextContent());
                String nume = element.getElementsByTagName("nume").item(0).getTextContent();
                String prenume = element.getElementsByTagName("prenume").item(0).getTextContent();
                String grupa = element.getElementsByTagName("grupa").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                String profesor = element.getElementsByTagName("profesor").item(0).getTextContent();
                Integer idTema = Integer.parseInt(element.getElementsByTagName("idTema").item(0).getTextContent());
                String descriere = element.getElementsByTagName("descriere").item(0).getTextContent();
                Integer startWeek = Integer.parseInt(element.getElementsByTagName("start").item(0).getTextContent());
                Integer deadlineWeek = Integer.parseInt(element.getElementsByTagName("deadline").item(0).getTextContent());
                Float nota = Float.parseFloat(element.getElementsByTagName("punctaj").item(0).getTextContent());
                Integer predataIn = Integer.parseInt(element.getElementsByTagName("predataIn").item(0).getTextContent());
                Integer motivat = Integer.parseInt(element.getElementsByTagName("motivat").item(0).getTextContent());
                String feedback = element.getElementsByTagName("feedback").item(0).getTextContent();
                elems.add(new Nota(new Student(idStudent, nume, prenume, grupa, email, profesor), new Tema(idTema, descriere, startWeek, deadlineWeek), nota, predataIn, motivat, feedback));
            }

        }
    }

    protected void addEntitiesToXML(Element rootElement, Document doc) {
        for (Nota n : elems) {
            Element nota = doc.createElement("nota");
            rootElement.appendChild(nota);
            Element idStudent = doc.createElement("idStudent");
            idStudent.appendChild(doc.createTextNode(n.getStudent().getId().toString()));
            Element nume = doc.createElement("nume");
            nume.appendChild(doc.createTextNode(n.getStudent().getNume()));
            Element prenume = doc.createElement("prenume");
            prenume.appendChild(doc.createTextNode(n.getStudent().getPrenume()));
            Element grupa = doc.createElement("grupa");
            grupa.appendChild(doc.createTextNode(n.getStudent().getGrupa()));
            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(n.getStudent().getEmail()));
            Element profesor = doc.createElement("profesor");
            profesor.appendChild(doc.createTextNode(n.getStudent().getCadruDidacticIndrumator()));
            Element idTema = doc.createElement("idTema");
            idTema.appendChild(doc.createTextNode(n.getTema().getId().toString()));
            Element descriere = doc.createElement("descriere");
            descriere.appendChild(doc.createTextNode(n.getTema().getDescriere()));
            Element startWeek = doc.createElement("start");
            startWeek.appendChild(doc.createTextNode(n.getTema().getStartWeek().toString()));
            Element deadline = doc.createElement("deadline");
            deadline.appendChild(doc.createTextNode(n.getTema().getDeadlineWeek().toString()));
            Element punctaj = doc.createElement("punctaj");
            punctaj.appendChild(doc.createTextNode(n.getNota().toString()));
            Element predataIn = doc.createElement("predataIn");
            predataIn.appendChild(doc.createTextNode(n.getPredataIn().toString()));
            Element motivat = doc.createElement("motivat");
            motivat.appendChild(doc.createTextNode(n.getMotivat().toString()));
            Element feedback = doc.createElement("feedback");
            feedback.appendChild(doc.createTextNode(n.getFeedback()));
            nota.appendChild(idStudent);
            nota.appendChild(nume);
            nota.appendChild(prenume);
            nota.appendChild(grupa);
            nota.appendChild(email);
            nota.appendChild(profesor);
            nota.appendChild(idTema);
            nota.appendChild(descriere);
            nota.appendChild(startWeek);
            nota.appendChild(deadline);
            nota.appendChild(punctaj);
            nota.appendChild(predataIn);
            nota.appendChild(motivat);
            nota.appendChild(feedback);
        }
    }

    protected void readFromPostgres() {
        {
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "123123123");
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM Note");
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    elems.add(new Nota(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)),
                            new Tema(resultSet.getInt(7), resultSet.getString(8), resultSet.getInt(9), resultSet.getInt(10)),
                            resultSet.getFloat(11), resultSet.getInt(13), resultSet.getInt(12), resultSet.getString(14)));
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected String createSQLAdd(Nota nota) {
        return "INSERT INTO Note VALUES(" +
                nota.getStudent().getId().toString() +",'"+nota.getStudent().getNume()+"','"+nota.getStudent().getPrenume()+"','"+nota.getStudent().getGrupa()+"','"+nota.getStudent().getEmail()+"','"+nota.getStudent().getCadruDidacticIndrumator()+","+
                nota.getTema().getId().toString() +",'"+ nota.getTema().getDescriere() + "'," + nota.getTema().getStartWeek() + "," +nota.getTema().getDeadlineWeek()+"," +
                nota.getNota() +"," + nota.getPredataIn() +"," + nota.getMotivat() + ",'" + nota.getFeedback() +"'"+
                ");";
    }

    protected String createSQLUpdate(Nota nota) {
        return "DELETE FROM Note WHERE idStudent=" + nota.getStudent().getId().toString() + " AND idTema=" + nota.getTema().getId() + "; " +
                "INSERT INTO Note " +
                "VALUES (" + nota.getStudent().getId().toString() +",'"+nota.getStudent().getNume()+"','"+nota.getStudent().getPrenume()+"','"+nota.getStudent().getGrupa()+"','"+nota.getStudent().getEmail()+"','"+nota.getStudent().getCadruDidacticIndrumator()+","+
                nota.getTema().getId().toString() +",'"+ nota.getTema().getDescriere() + "'," + nota.getTema().getStartWeek() + "," +nota.getTema().getDeadlineWeek()+"," +
                nota.getNota() +"," + nota.getPredataIn() +"," + nota.getMotivat() + ",'" + nota.getFeedback() +"');";
    }

    protected String createSQLDelete(Nota nota) {
        return "DELETE FROM Note WHERE idStudent=" + nota.getStudent().getId().toString() + " AND idTema=" + nota.getTema().getId() + ";";
    }
}
