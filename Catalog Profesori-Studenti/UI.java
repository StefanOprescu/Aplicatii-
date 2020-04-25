package me.map.lab3.consola;

import me.map.lab3.controller.Controller;
import me.map.lab3.domain.Nota;
import me.map.lab3.domain.Student;
import me.map.lab3.domain.Tema;
import me.map.lab3.exceptii.ValidationException;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UI {
    private Controller controller;
    public UI(Controller c){ controller = c; }
    public void run(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Comenzile disponibile sunt:");
        System.out.println("1.saptamana");
        System.out.println("2.afisare");
        System.out.println("3.adauga student");
        System.out.println("4.modifica student");
        System.out.println("5.sterge student");
        System.out.println("6.adauga tema");
        System.out.println("7.modifica tema");
        System.out.println("8.sterge tema");
        System.out.println("9.da tema");
        System.out.println("10.sterge nota");
        System.out.println("11.modifica nota");
        System.out.println("12.exit");
        while(true){
            System.out.println("Introduceti comanda: ");
            String comanda = sc.nextLine();
            if(comanda.equals("afisare")) {
                Iterator<Student> studenti = controller.getStudenti().iterator();
                studenti.forEachRemaining(System.out::println);
                Iterator<Tema> teme = controller.getTeme().iterator();
                teme.forEachRemaining(System.out::println);
                Iterator<Nota> note = controller.getNote().iterator();
                note.forEachRemaining(System.out::println);
            }
            else if(comanda.equals("adauga student"))
            {
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=6)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                try{
                    Integer id = Integer.parseInt(params[0]);
                    try{
                        if(controller.adaugaStudent(id,params[1],params[2],params[3],params[4],params[5])==null)
                            System.out.println("Studentul a fost salvat!\n");
                        else
                            System.out.println("Studentul exista deja!\n");
                    }
                    catch(ValidationException v){
                        System.out.println(v.getMessage()); }
                }
                catch(NumberFormatException e){
                    System.out.println("Tipul id-ului invalid!\n"); }
                }
            }
            else if (comanda.equals("sterge student")){
                String parametri = sc.nextLine();
                try{
                    Integer id = Integer.parseInt(parametri);
                    if(controller.stergeStudent(id)!=null)
                        System.out.println("Studentul a fost sters!\n");
                    else
                        System.out.println("Studentul nu exista!\n");
                }
                catch(NumberFormatException e){
                    System.out.println("Tipul id-ului invalid!\n");
                }
            }
            else if(comanda.equals("modifica student"))
            {
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=6)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                    try{
                        Integer id = Integer.parseInt(params[0]);
                        try{
                            if(controller.modificaStudent(id,params[1],params[2],params[3],params[4],params[5])==null)
                                System.out.println("Studentul a fost modificat!\n");
                            else
                                System.out.println("Studentul nu exista!\n");
                        }
                        catch(ValidationException v){
                            System.out.println(v.getMessage()); }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Tipul id-ului invalid!\n"); }
                }
            }
            else if(comanda.equals("adauga tema"))
            {
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=3)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                    try{
                        Integer id = Integer.parseInt(params[0]);
                        Integer deadlineWeek = Integer.parseInt(params[2]);
                        try{
                            if(controller.adaugaTema(id,params[1],deadlineWeek)==null)
                                System.out.println("Tema a fost salvat!\n");
                            else
                                System.out.println("Tema exista deja!\n");
                        }
                        catch(ValidationException v){
                            System.out.println(v.getMessage()); }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Tipul id-ului/deadline-ului invalid!\n"); }
                }
            }
            else if (comanda.equals("sterge tema")){
                String parametri = sc.nextLine();
                try{
                    Integer id = Integer.parseInt(parametri);
                    if(controller.stergeTema(id)!=null)
                        System.out.println("Tema a fost stearsa!\n");
                    else
                        System.out.println("Tema nu exista!\n");
                }
                catch(NumberFormatException e){
                    System.out.println("Tipul id-ului invalid!\n");
                }
            }
            else if(comanda.equals("modifica tema"))
            {
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=3)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                    try{
                        Integer id = Integer.parseInt(params[0]);
                        Integer deadlineWeek = Integer.parseInt(params[2]);
                        try{
                            if(controller.modificaTema(id,params[1],deadlineWeek)==null)
                                System.out.println("Tema a fost modificata!\n");
                            else
                                System.out.println("Tema nu exista!\n");
                        }
                        catch(ValidationException v){
                            System.out.println(v.getMessage()); }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Tipul id-ului/deadline-ului invalid!\n"); }
                }
            }
            else if (comanda.equals("da tema"))
            {
                System.out.println("Introduceti id-ul studentului si id-ul temei");
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=2)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                    try{
                        Integer idStudent = Integer.parseInt(params[0]);
                        Integer idTema = Integer.parseInt(params[1]);
                        try {
                            if (controller.adaugaNota(idStudent, idTema) == null)
                                System.out.println("Tema a fost data cu succes\n");
                            else
                                System.out.println("Tema nu a putut fi data\n");
                        }
                        catch(ValidationException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Tipul id-ului/deadline-ului invalid!\n"); }
                }
            }
            else if (comanda.equals("sterge nota")){
                System.out.println("Introduceti id-ul studentului si id-ul temei");
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=2)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                try{
                    Integer idStudent = Integer.parseInt(params[0]);
                    Integer idTema = Integer.parseInt(params[1]);
                    if(controller.stergeNota(idStudent,idTema)!=null)
                        System.out.println("Nota a fost stearsa!\n");
                    else
                        System.out.println("Nota nu exista!\n");
                }
                catch(NumberFormatException e){
                    System.out.println("Tipul id-urilor invalid!\n"); }
                }
            }
            else if (comanda.equals("modifica nota"))
            {
                System.out.println("Introduceti id-ul studentului, id-ul temei");
                String parametri = sc.nextLine();
                String[] params = parametri.split("`");
                if(params.length!=2)
                    System.out.println("Numarul de parametri este invalid!\n");
                else{
                    try{
                        Integer idStudent = Integer.parseInt(params[0]);
                        Integer idTema = Integer.parseInt(params[1]);
                        //System.out.println("Nota maxima ce poate fi acordata studentului este: "+controller.getNotaMaxima(idStudent,idTema));
                        System.out.println("Introduceti nota, numarul de saptamani motivate si feedback-ul");
                        String noiParams = sc.nextLine();
                        String[] parametriNoi = noiParams.split("`");
                        if(parametriNoi.length!=3)
                            System.out.println("Numarul de parametri este invalid!\n");
                        else{
                        try{
                            Float nota = Float.parseFloat(parametriNoi[0]);
                            Integer motivat = Integer.parseInt(parametriNoi[1]);
                            try{
                                if(controller.modificaNota(idStudent,idTema,nota,motivat,parametriNoi[2])==null)
                                    System.out.println("Nota s-a modificat cu succes~\n");
                                else
                                    System.out.println("Nota nu exista!\n");
                            }
                            catch(ValidationException e){
                                System.out.println(e.getMessage());
                            }
                        }
                        catch(NumberFormatException e){
                            System.out.println("Tipul notei/numarului de saptamani motivate invalid!\n");
                        }
                    }
                    }
                    catch(NumberFormatException e){
                        System.out.println("Tipul id-urilor invalid!\n");}
                }
            }
            else if (comanda.equals("saptamana")){
                System.out.println("Ne aflam in saptamana "+controller.getSaptamanaCurenta());
            }
            else if (comanda.equals("filtrare")) {
                System.out.println("Filtrari posibile:\nstudenti grupa\nstudenti tema\nstudenti tema profesor\nnote tema saptamana");
                String filtrare = sc.nextLine();
                if (filtrare.equals("studenti grupa")) {
                    System.out.println("Introduceti grupa");
                    String grupa = sc.nextLine();
                    List<Student> lst = controller.totiStudentiiUneiGrupe(grupa);
                    for (Student s : lst) {
                        System.out.println(s);
                    }
                } else if (filtrare.equals("studenti tema")) {
                    System.out.println("Introduceti tema");
                    String tema = sc.nextLine();
                    try {
                        List<Student> lst = controller.totiStudentiiCuOTema(Integer.parseInt(tema));
                        for (Student s : lst) {
                            System.out.println(s);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Tip de data invalid!\n");
                    }
                } else if (filtrare.equals("studenti tema profesor")) {
                    System.out.println("Introduceti tema si profesorul");
                    String[] params = sc.nextLine().split("`");
                    if (params.length != 2)
                        System.out.println("Numar de parametri invalid!\n");
                    else {
                        try {
                            List<Student> lst = controller.totiStudentiiCuOTemaSiProfesor(Integer.parseInt(params[0]), params[1]);
                            for (Student s : lst) {
                                System.out.println(s);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Tip de data invalid!\n");
                        }
                    }
                } else if (filtrare.equals("note tema saptamana")) {
                    System.out.println("Introduceti tema si saptamana");
                    String[] params = sc.nextLine().split("`");
                    if (params.length != 2)
                        System.out.println("Numar de parametri invalid!\n");
                    else {
                        try {
                            List<Nota> lst = controller.toateNoteleCuOSaptamanaSiOTema(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
                            for (Nota n : lst) {
                                System.out.println(n);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Tip de data invalid!\n");
                        }
                    }


                }
                else{
                    System.out.println("Comanda gresita!\n");
                }
            }
            else if(comanda.equals("exit")){
                return;
            }
            else System.out.println("Comanda gresita!\n");
        }
    }
}

/**
 * CREATE DATABASE Laborator;
 *
 * CREATE TABLE Studenti(
 *   idStudent INT,
 *   nume VARCHAR(50),
 *   prenume VARCHAR(50),
 *   grupa VARCHAR(20),
 *   email VARCHAR(50),
 *   profesor VARCHAR(100)
 * );
 *
 * SELECT * FROM Student
 */

