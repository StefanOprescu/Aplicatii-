package me.repository;

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

public class RepoParticipant implements IRepoParticipant {
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public RepoParticipant(Properties props){
        logger.info("Initializing RepoAngajat with properties: {} ",props);
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public List<Participant> findParticipantiDupaNume(String nume) {
        Connection con=jdbcUtils.getConnection();
        List<Participant> participanti = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where nume=?")){
            preStmt.setString(1,nume);
            try(ResultSet result=preStmt.executeQuery()) {
                 while (result.next()) {
                    int id = result.getInt("id");
                    int varsta = result.getInt("varsta");
                    participanti.add(new Participant(id,nume,varsta));
                }
                 return participanti;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Participant> findParticipantiDupaVarsta(int i) {
        return null;
    }

    @Override
    public Participant save(Participant elem) {
        logger.info("Se conecteaza la baza de date");
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participanti (nume,varsta) values (?,?)")){
            preStmt.setString(1,elem.getNume());
            preStmt.setInt(2,elem.getVarsta());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.info("eroare baza de date: "+ex.getMessage());
        }
        logger.info("S-a adaugat in baza de date participant "+elem);
        return findParticipantiDupaNume(elem.getNume()).get(0);
    }

    @Override
    public Participant update(Participant elem) { return null; }

    @Override
    public Participant delete(Participant elem) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Participant get(Integer integer) {
        Connection con=jdbcUtils.getConnection();
        List<Participant> participanti = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String nume  = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    return new Participant(id,nume,varsta);
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public int size() { return 0; }

    @Override
    public Iterable<Participant> findAll() {
        Connection con=jdbcUtils.getConnection();
        List<Participant> participanti = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti")){
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String nume = result.getString("nume");
                    int varsta = result.getInt("varsta");
                    participanti.add(new Participant(id,nume,varsta));
                }
                return participanti;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }
}
