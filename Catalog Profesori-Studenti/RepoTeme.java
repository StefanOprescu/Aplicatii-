package me.map.lab3.repos;

import me.map.lab3.domain.Tema;
import me.map.lab3.validator.Validator;
import org.w3c.dom.*;

import java.sql.*;

public class RepoTeme extends GenericRepo<Integer, Tema> {
    public RepoTeme(String fis, Validator<Tema> val) {
        super(fis, val);
    }

    protected Tema createEntity(String linie) {
        String[] params = linie.split("`");
        return new Tema(Integer.parseInt(params[0]),params[1],Integer.parseInt(params[2]),Integer.parseInt(params[3]));
    }

    protected String createString(Tema t) {
        return t.getId()+"`"+t.getDescriere()+"`"+t.getStartWeek()+"`"+t.getDeadlineWeek()+"\n";
    }

    protected void updateaza(Tema t, Tema altaTema) {
        t.setDescriere(altaTema.getDescriere());
        t.setDeadlineWeek(altaTema.getDeadlineWeek());
    }

    protected void createEntitiesFromXML(Document doc) {
        NodeList elemente = doc.getElementsByTagName("tema");
        for (int i = 0; i < elemente.getLength(); i++) {
            Node curent = elemente.item(i);
            if (curent.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) curent;
                Integer idTema = Integer.parseInt(element.getAttribute("id"));
                String descriere = element.getElementsByTagName("descriere").item(0).getTextContent();
                Integer startWeek = Integer.parseInt(element.getElementsByTagName("start").item(0).getTextContent());
                Integer deadlineWeek = Integer.parseInt(element.getElementsByTagName("deadline").item(0).getTextContent());
                elems.add(new Tema(idTema, descriere, startWeek, deadlineWeek));
            }
        }
    }

    protected void addEntitiesToXML(Element rootElement, Document doc){
            for(Tema t : elems) {
                Element tema = doc.createElement("tema");
                rootElement.appendChild(tema);

                tema.setAttribute("id",t.getId().toString());
                Element descriere = doc.createElement("descriere");
                descriere.appendChild(doc.createTextNode(t.getDescriere()));
                tema.appendChild(descriere);

                Element startWeek = doc.createElement("start");
                startWeek.appendChild(doc.createTextNode(t.getStartWeek().toString()));
                tema.appendChild(startWeek);

                Element deadline = doc.createElement("deadline");
                deadline.appendChild(doc.createTextNode(t.getDeadlineWeek().toString()));
                tema.appendChild(deadline);

            }
    }

    protected void readFromPostgres(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "123123123");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Teme");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                elems.add(new Tema(resultSet.getInt(1),resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4)));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected String createSQLAdd(Tema tema) {
        return "INSERT INTO Teme"+
                "VALUES (" +tema.getId().toString() +",'"+ tema.getDescriere() + "'," + tema.getStartWeek() + "," +tema.getDeadlineWeek()+ ");";
    }

    protected String createSQLUpdate(Tema tema) {
        return "DELETE FROM Teme WHERE idTema="+tema.getId().toString()+"; "+
                "INSERT INTO Teme "+
                "VALUES ("+tema.getId().toString() +",'"+ tema.getDescriere() + "'," + tema.getStartWeek() + "," +tema.getDeadlineWeek()+ ");";
    }

    protected String createSQLDelete(Tema tema) { return "DELETE FROM Teme WHERE idTema="+tema.getId().toString()+";"; }
}
