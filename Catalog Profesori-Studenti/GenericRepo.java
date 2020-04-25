package me.map.lab3.repos;

import me.map.lab3.domain.Entity;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public abstract class GenericRepo<ID, E extends Entity<ID>> implements CrudRepository<ID, E> {
    protected Vector<E> elems;
    private Validator<E> v;
    protected String fisier;
    protected abstract E createEntity(String s);
    protected abstract String createString(E e);
    protected abstract void updateaza(E e1, E e2);
    protected abstract void createEntitiesFromXML(Document d);
    protected abstract void addEntitiesToXML(Element root, Document d);
    protected abstract void readFromPostgres();
    protected abstract String createSQLAdd(E e);
    protected abstract String createSQLUpdate(E e);
    protected abstract String createSQLDelete(E e);

    public GenericRepo(String fis, Validator<E> val){
        v=val;
        fisier=fis;
        elems = new Vector<>();
        //readFromPostgres();
        readFromXML();
    }

    private void writeToPostgres(String s){
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "123123123");
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(s);
            stmt.close();
            con.commit();
            con.close();
        } catch (ClassNotFoundException | SQLException eroare) {
            eroare.printStackTrace();
        }
    }

/**    private void loadDatafromFile(){
        File f = new File(fisier);
        try{
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                String linie = sc.nextLine();
                E e = createEntity(linie);
                elems.add(e);
            }
            sc.close();
        }
        catch(FileNotFoundException e){System.out.println("Fisierul nu exista!");}
    }

    private void writeDataToFile(){
        File f = new File(fisier);
        try{
            PrintWriter pw = new PrintWriter(f);
            for(E e : elems)
            {
                String s = createString(e);
                pw.write(s);
            }
            pw.close();
        }
        catch(FileNotFoundException e){System.out.println("Fisierul nu exista!");}
    }
**/
    private void readFromXML(){
       try {
           File f = new File(fisier);
           DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
           Document doc = docBuilder.parse(f);
           doc.getFirstChild();
           doc.getDocumentElement().normalize();
           doc.getDocumentElement().getNodeName();
           createEntitiesFromXML(doc);
       }
       catch(Exception e){
           e.printStackTrace();
       }
    }

    private void writeToXML(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("company");
            doc.appendChild(rootElement);
            addEntitiesToXML(rootElement,doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fisier));
            transformer.transform(source, result);
        }
        catch (Exception pce) {
            pce.printStackTrace(); }
    }

    public E findOne(ID id){
        for(E e : elems)
            if(e.getId().equals(id)) {
                return e;
            }
        return null;
    }

    public Iterable<E> findAll(){
        return elems;
    }

    public E save(E entity) throws ValidationException{
        v.validate(entity);
        if(entity==null)
            throw new IllegalArgumentException("Entitatea nu trebuie sa fie null!");
        for(E e : elems)
            if(e.equals(entity))
                return e;
        elems.add(entity);
        writeToXML();
        //writeToPostgres(createSQLAdd(entity));
        return null;
    }

    public E delete(ID id){
        if(id==null)
            throw new IllegalArgumentException("ID-ul nu trebuie sa fie null!");
        int i=0;
        E entity = findOne(id);
        while(i<elems.size() && !elems.elementAt(i).equals(entity))
            i++;
        if(i==elems.size())
            return null;
        E e = elems.elementAt(i);
        elems.removeElementAt(i);
        writeToXML();
        //writeToPostgres(createSQLDelete(e));
        return e;
    }

    public E update(E otEntity) throws ValidationException {
        v.validate(otEntity);
        for (E s : elems) {
            if (s.equals(otEntity)) {
                updateaza(s, otEntity);
                writeToXML();
                //writeToPostgres(createSQLUpdate(otEntity));
                return null;
            }
        }
        return otEntity;
    }

    public int getSize(){return elems.size();}
}
