package me.repository;


import me.model.Angajat;
import me.model.Participant;
import me.model.Proba;
import me.model.Stiluri;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoAngajat implements IRepoAngajat{
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public RepoAngajat(Properties props){
        logger.info("Initializing RepoAngajat with properties: {} ",props);
        jdbcUtils=new JdbcUtils(props);
    }


    @Override
    public List<Angajat> findAngajatiNume(String nume) { return null; }

    @Override
    public Angajat getUsernameParola(String username, String parola) {
        logger.info("Se cauta angajatul cu username-ul "+ username+" parola *******");
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati where username=? and parola=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,parola);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    return new Angajat(id,nume,varsta,username,parola);
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Angajat save(Angajat elem) {
        logger.info("Se conecteaza la baza de date");
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Angajati (nume,varsta,username,parola) values (?,?,?,?)")){
            preStmt.setString(1,elem.getNume());
            preStmt.setInt(2,elem.getVarsta());
            preStmt.setString(3,elem.getUsername());
            preStmt.setString(4,elem.getParola());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.info("eroare baza de date: "+ex.getMessage());
        }
        logger.info("S-a adaugat in baza de date angajatul "+elem);
        return elem;
    }

    @Override
    public Angajat update(Angajat elem) { return null; }

    @Override
    public Angajat delete(Angajat elem) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Angajat get(Integer integer) { return null; }

    @Override
    public int size() { return 0; }

    @Override
    public Iterable<Angajat> findAll() {
        /*logger.info("Se cauta angajatii");
        Connection con=jdbcUtils.getConnection();
        List<Angajat> angajati = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati")){
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    Angajat a = new Angajat(id,nume,varsta,username,parola);
                    angajati.add(a);
                }
                return angajati;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;*/
        logger.info("Se cauta angajatii");
        Connection con=jdbcUtils.getConnection();
        List<Angajat> angajati = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati")){
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    angajati.add(new Angajat(id,nume,varsta,username,parola));
                }
                return angajati;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }
}
