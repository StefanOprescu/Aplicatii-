package me.repository;

import me.model.Participant;
import me.model.Proba;
import me.model.ProbaParticipant;
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

public class RepoProba implements IRepoProba {
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public RepoProba(Properties props){
        logger.info("Initializing RepoAngajat with properties: {} ",props);
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public List<Proba> findProbeDupaStil(Stiluri stil) { return null; }

    @Override
    public List<Proba> findProbeDupaDistanta(float distanta) { return null; }

    @Override
    public Proba save(Proba elem) {
        logger.info("Se conecteaza la baza de date");
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Probe (distanta,stil) values (?,?)")){
            preStmt.setFloat(1,elem.getDistanta());
            preStmt.setString(2,elem.getStil().toString());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.info("eroare baza de date: "+ex.getMessage());
        }
        logger.info("S-a adaugat in baza de date proba "+elem);
        return elem;
    }

    @Override
    public Proba update(Proba elem) { return null; }

    @Override
    public Proba delete(Proba elem) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Proba get(Integer integer) {
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    float distanta  = result.getFloat("distanta");
                    Stiluri stil = Stiluri.valueOf(result.getString("stil"));
                    return new Proba(id,distanta,stil);
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
    public Iterable<Proba> findAll() {
        logger.info("Se aduc toate probele...");
        Connection con=jdbcUtils.getConnection();
        List<Proba> probe = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe")){
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    float distanta = result.getFloat("distanta");
                    Stiluri stil = Stiluri.valueOf(result.getString("stil"));
                    probe.add(new Proba(id,distanta,stil));
                }
                return probe;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }
}
