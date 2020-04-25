package me.repository;

import me.model.Participant;
import me.model.Proba;
import me.model.ProbaParticipant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoProbaParticipant implements IRepoProbaParticipant {
    private static final Logger logger = LogManager.getLogger();
    JdbcUtils jdbcUtils;

    public RepoProbaParticipant(Properties props){
        logger.info("Initializing RepoAngajat with properties: {} ",props);
        jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public int numarParticipantiProba(Integer p) { return 0; }

    @Override
    public List<ProbaParticipant> findProbaParticipantDupaProba(Integer p) {
        Connection con=jdbcUtils.getConnection();
        List<ProbaParticipant> probeparticipanti = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from ProbeParticipanti where idProba=?")){
            preStmt.setInt(1,p);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idParticipant = result.getInt("idParticipant");
                    probeparticipanti.add(new ProbaParticipant(p,idParticipant));
                }
                return probeparticipanti;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<ProbaParticipant> findProbaParticipantDupaParticipant(Integer idParticipant) {
        Connection con=jdbcUtils.getConnection();
        List<ProbaParticipant> probeparticipanti = new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from ProbeParticipanti where idParticipant=?")){
            preStmt.setInt(1,idParticipant);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int idProba = result.getInt("idProba");
                    probeparticipanti.add(new ProbaParticipant(idProba,idParticipant));
                }
                return probeparticipanti;
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public ProbaParticipant save(ProbaParticipant elem) {
        logger.info("Se conecteaza la baza de date");
        Connection con=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into ProbeParticipanti (idProba,idParticipant) values (?,?)")){
            preStmt.setInt(1,elem.getIdProba());
            preStmt.setInt(2,elem.getIdParticipant());
            preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.info("eroare baza de date: "+ex.getMessage());
        }
        logger.info("S-a adaugat in baza de date probaparticipant "+elem);
        return elem;
    }

    @Override
    public ProbaParticipant update(ProbaParticipant elem) { return null; }

    @Override
    public ProbaParticipant delete(ProbaParticipant elem) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ProbaParticipant get(String s) { return null; }

    @Override
    public int size() { return 0; }

    @Override
    public Iterable<ProbaParticipant> findAll() { return null; }
}
