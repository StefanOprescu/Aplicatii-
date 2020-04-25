package me.map.lab3.services;

import javafx.util.Pair;
import me.map.lab3.domain.AnUniversitar;
import me.map.lab3.domain.Nota;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;
import me.map.lab3.repos.GenericRepo;
import me.map.lab3.repos.RepoNote;
import me.map.lab3.writerPDF.PDFGenerator;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceNote{
    private RepoNote repoNote;
    private String fisierDeExport;
    private String folderDeEXportPDF;
    public ServiceNote(RepoNote r, String fisDeExport, String exportPDF) {
        this.repoNote = r;
        fisierDeExport = fisDeExport;
        folderDeEXportPDF=exportPDF;
    }

    public void exportaSprePDF(String titlu,String mesaj){
        PDFGenerator.writeToPDF(folderDeEXportPDF+titlu+".pdf",mesaj);
    }

    private void exportaSpreFisier(Nota nota) {
        String fisier = fisierDeExport + nota.getStudent().getNume() + nota.getStudent().getPrenume() + ".txt";
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fisier, true)));
            pw.write("Tema: " + nota.getTema().getId().toString() + "\n" + "Nota: " + nota.getNota().toString() +
                    "\n" + "Predata in saptamana: " + nota.getPredataIn().toString() +
                    "\n" + "Deadline: " + nota.getTema().getDeadlineWeek() +
                    "\n" + "Feedback: " + nota.getFeedback() + "\n\n");
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param s : Student
     * @param t : Tema
     * @return null daca se poate acorda tema, o noua nota altfel
     * @throws ValidationException daca:
     *                             - studentul sau tema sunt null
     *                             - studentul nu este valid
     *                             - tema nu este valida
     */
    public Nota daTema(Student s, Tema t) throws ValidationException {
        if (s == null || t == null)
            throw new ValidationException("Tema si studentul nu pot fi null!\n");
        Nota n = new Nota(s, t);
        return repoNote.save(n);
    }

    public Nota adaugaNota(Student s, Tema t, AnUniversitar a, Integer motivat) throws ValidationException {
        if (s == null || t == null)
            throw new ValidationException("Tema si studentul nu pot fi null!\n");
        Nota deModificat = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
        if (deModificat != null && deModificat.getPredataIn() != 0)
            throw new ValidationException("Nota nu isi poate modifica saptamana cand a fost predata!\n");
        Nota n = new Nota(s, t, a.getSaptamanaCurenta(), motivat);
        return repoNote.save(n);
    }

    /**
     * @param idStudent : integer
     * @param idTema    : integer
     * @return null daca nota nu exista, altfel se returneaza nota cu id-ul idStudent.idTema
     */
    public Nota sterge(Integer idStudent, Integer idTema) {
        return repoNote.delete(idStudent.toString() + "." + idTema.toString());
    }

    /**
     * @param s        student
     * @param t        tema
     * @param n        nota
     * @param motivat  numarul de saptamani motivate
     * @param feedback feedback-ul : string
     * @param a        saptamana curenta
     * @return null daca se modifica, nota altfel
     * @throws ValidationException daca:
     *                             - au trecut doua saptamani si studentul n-are motivare
     *                             - tema sau studentul sunt null
     *                             - se incearca modificarea unei teme care deja a fost predata
     */
    public Nota modifica(Student s, Tema t, Float n, Integer motivat, String feedback, AnUniversitar a) throws ValidationException {
        if (s == null || t == null)
            throw new ValidationException("Tema si studentul nu pot fi null!\n");
        Integer predataIn = a.getSaptamanaCurenta();
        if (predataIn - t.getDeadlineWeek() > 2 + motivat)
            throw new ValidationException("Tema nu mai poate fi predata!\n");
        if (predataIn > 14) // Profesorul nu a introdus nota la timp, nu se mai depuncteaza
            n = (float) 10;
        Nota deModificat = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
        if (deModificat != null) {
            Nota nota = new Nota(s, t, n, predataIn, motivat, feedback);
            Nota notaUpdatata = repoNote.update(nota);
            Nota updatata = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
            if (updatata != null)
                exportaSpreFisier(updatata);
            return notaUpdatata;
        } else {
            return new Nota(s, t, n, predataIn, motivat, feedback);
        }
    }

    /**
     * @param id id-ul Notei
     * @return nota daca exista, null altfel
     */
    public Nota cauta(String id) {
        return repoNote.findOne(id);
    }

    /**
     * @return elementele stocate
     */
    public Iterable<Nota> getElems() {
        return repoNote.findAll();
    }

    /**
     * @param idStudent : Integer
     * @param idTema    : Integer
     * @return nota maxima a studentului cu id-ul idStudent la tema cu id-ul idTema
     */
    public Float getNotaMaxima(Integer idStudent, Integer idTema) {
        String idNota = idStudent.toString() + "." + idTema.toString();
        Nota n = repoNote.findOne(idNota);
        if (n != null)
            return n.getNotaMaxima();
        else
            return (float) 0;
    }

    public List<Student> filterTotiStudentiiNote(Function<Nota, Student> f) {
        List<Student> studenti;
        List<Nota> note = new ArrayList<>();
        repoNote.findAll().forEach(note::add);
        studenti = note.stream().map(f).collect(Collectors.toList());
        studenti.removeAll(Collections.singleton(null));
        return studenti;
    }

    public List<Nota> filterToateNotele(Predicate<Nota> p) {
        List<Nota> note = new ArrayList<>();
        repoNote.findAll().forEach(note::add);
        return note.stream().filter(p).collect(Collectors.toList());
    }

    public boolean poateFiPredata(Student s, Tema t, AnUniversitar a) {
        Nota n = cauta(s.getId() + "." + t.getId());
        return n != null && a.getSaptamanaCurenta() - t.getDeadlineWeek() <= n.getMotivat() + 2;
    }


    public Nota adaugaNotaUitat(Student s, Tema t, Integer deadLine) throws ValidationException {
        if (s == null || t == null)
            throw new ValidationException("Tema si studentul nu pot fi null!\n");
        Nota deModificat = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
        if (deModificat != null && deModificat.getPredataIn() != 0)
            throw new ValidationException("Nota nu isi poate modifica saptamana cand a fost predata!\n");
        Nota n = new Nota(s, t, deadLine, 0);
        return repoNote.save(n);
    }

    public Nota modificaUitat(Student s, Tema t, Float n, String feedback) throws ValidationException {
        if (s == null || t == null)
            throw new ValidationException("Tema si studentul nu pot fi null!\n");
        Nota deModificat = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
        if (deModificat != null) {
            Nota nota = new Nota(s, t, n, t.getDeadlineWeek(), 0, feedback);
            Nota notaUpdatata = repoNote.update(nota);
            Nota updatata = repoNote.findOne(s.getId().toString() + "." + t.getId().toString());
            if (updatata != null)
                exportaSpreFisier(updatata);
            return notaUpdatata;
        } else {
            return new Nota(s, t, n, t.getDeadlineWeek(), 0, feedback);
        }
    }

    public void modificaStudent(Integer id, String nume, String prenume, String grupa, String email, String profesor){
        Iterator<Nota> elems = repoNote.findAll().iterator();
        Student student = new Student(id,nume,prenume,grupa,email,profesor);
        while(elems.hasNext()){
            Nota nota = elems.next();
            if(nota.getStudent().getId().equals(id)){
                nota.getStudent().setNume(nume);
                nota.getStudent().setPrenume(prenume);
                nota.getStudent().setGrupa(grupa);
                nota.getStudent().setEmail(email);
                nota.getStudent().setCadruDidacticIndrumator(profesor);
            }
        }
    }

    private Float notaPredata(Student st, Tema t){
        Iterator<Nota> note = repoNote.findAll().iterator();
        while(note.hasNext()){
            Nota n = note.next();
            if(n.getStudent().getId().equals(st.getId()) && n.getTema().getId().equals(t.getId()))
                return n.getNota();
        }
        return 1f;
    }

    private boolean aPredatLaTimp(Student st, Tema t){
        Iterator<Nota> note = repoNote.findAll().iterator();
        while(note.hasNext()){
            Nota n = note.next();
            if(n.getStudent().getId().equals(st.getId()) && n.getTema().getId().equals(t.getId()) && n.getPredataIn()<=t.getDeadlineWeek())
                return true;
        }
        return false;
    }

    public Vector<Pair<Student,Float>> notaFiecareStudent(Iterable<Student> studenti, Iterable<Tema> teme, AnUniversitar a){
        Vector<Pair<Student,Vector<Pair<Tema,Float>>>> noteFiecareStudent = new Vector<>();
        Vector<Pair<Student,Float>> medieFiecareStudent = new Vector<>();
        Iterator<Student> itStudenti = studenti.iterator();
        Iterator<Tema> itTeme = teme.iterator();
        while(itStudenti.hasNext()){
            Student st = itStudenti.next();
            Vector<Pair<Tema,Float>> noteStudent = new Vector<>();
            Tema t;
            while(itTeme.hasNext() && (t= itTeme.next()).getDeadlineWeek()<=a.getSaptamanaCurenta()){
                Pair<Tema,Float> temaNota = new Pair<>(t,notaPredata(st,t));
                noteStudent.add(temaNota);
            }
            itTeme = teme.iterator();
            Pair<Student, Vector<Pair<Tema,Float>>> pereche = new Pair<>(st,noteStudent);
            noteFiecareStudent.add(pereche);
        }
        for(Pair<Student,Vector<Pair<Tema,Float>>> p : noteFiecareStudent){
            Vector<Pair<Tema,Float>> temeNote = p.getValue();
            Float numarator=(float)0;
            Float numitor=(float)0;
            for(Pair<Tema,Float> p2 : temeNote){
                numarator= numarator+ (float)(p2.getKey().getDeadlineWeek()-p2.getKey().getStartWeek())*p2.getValue();
                numitor= numitor+ (float)(p2.getKey().getDeadlineWeek()-p2.getKey().getStartWeek());
            }
            Float medie = numarator/numitor;
            Pair<Student,Float> studentMedie = new Pair<>(p.getKey(),medie);
            medieFiecareStudent.add(studentMedie);
        }
        return medieFiecareStudent;
    }

    public Vector<Student> studentiCarePotIntraInExamen(Iterable<Student> studenti, Iterable<Tema> teme, AnUniversitar a){
        Vector<Student> inExamen = new Vector<>();
        Vector<Pair<Student,Float>> medieFiecareStudent = notaFiecareStudent(studenti,teme,a);
        for(Pair<Student,Float> p : medieFiecareStudent){
            if (p.getValue()>=4){
                inExamen.add(p.getKey());
            }
        }
        return inExamen;
    }

    public Vector<Student> studentiCareAuPredatLaTimp(Iterable<Student> studenti, Iterable<Tema> teme, AnUniversitar a){
        Vector<Student> predatLaTimp = new Vector<>();
        Iterator<Student> itStudenti = studenti.iterator();
        Iterator<Tema> itTeme = teme.iterator();
        while(itStudenti.hasNext()){
            Student st = itStudenti.next();
            Integer temePredate =0;
            Integer numarTeme=0;
            Tema t;
            while(itTeme.hasNext() && (t=itTeme.next()).getDeadlineWeek()<=a.getSaptamanaCurenta()){
                numarTeme++;
                if(aPredatLaTimp(st,t))
                    temePredate++;
            }
            if(temePredate.equals(numarTeme))
                predatLaTimp.add(st);
            itTeme=teme.iterator();
        }
        return predatLaTimp;
    }

    public Vector<Pair<Tema,Float>> ceaMaiGreaTema(Iterable<Student> studenti, Iterable<Tema> teme, AnUniversitar a){
        Vector<Pair<Tema,Vector<Float>>> noteFiecareTema = new Vector<>();
        Iterator<Student> itStudenti = studenti.iterator();
        Iterator<Tema> itTeme = teme.iterator();
        Tema t;
        int nrStudenti = 10;
        while(itTeme.hasNext() && (t=itTeme.next()).getDeadlineWeek()<=a.getSaptamanaCurenta()){
            Vector<Float> noteTema = new Vector<>();
            while(itStudenti.hasNext()){
                Student st = itStudenti.next();
                noteTema.add(notaPredata(st,t));
            }

            itStudenti = studenti.iterator();
            Pair<Tema, Vector<Float>> pereche = new Pair<>(t,noteTema);
            noteFiecareTema.add(pereche);
        }
        Vector<Pair<Tema,Float>> medieFiecareTema = new Vector<>();
        for(Pair<Tema,Vector<Float>> p : noteFiecareTema){
            Vector<Float> note = p.getValue();
            Float medie =(float)0;
            for(Float punctaj : note){
                medie = medie + punctaj;
            }
            Pair<Tema,Float> pereche = new Pair<>(p.getKey(),medie/note.size());
            medieFiecareTema.add(pereche);
        }

        Float min=(float)11;
        for(Pair<Tema,Float> p : medieFiecareTema){
            if(p.getValue()<min)
                min = p.getValue();
        }

        Vector<Pair<Tema,Float>> deReturnat = new Vector<>();
        for(Pair<Tema,Float> p : medieFiecareTema){
            if (p.getValue().equals(min)){
                Pair<Tema,Float> perecheDeReturnat = new Pair<>(p.getKey(),p.getValue());
                deReturnat.add(perecheDeReturnat);
            }
        }
        return deReturnat;
    }
}

