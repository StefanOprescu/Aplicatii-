package me.map.lab3.writerPDF;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import me.map.lab3.elementeGui.AlertBox;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PDFGenerator {
    public static void writeToPDF(String numeFisier, String mesaj){
            Document document = new Document();
            String fileName = "donare sange";
            String filePath = "C:\\Users\\Stefan\\Desktop\\" + fileName + ".pdf";
            try {
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                String text = "ANUL: " + Calendar.getInstance().get(Calendar.YEAR) + " NUME/PRENUME: " + "gigel" + " VÂRSTA: " + "18" + "\n" +
                        "CHESTIONAR PENTRU DONATORUL DE SÂNGE\n" + "Convorbirea in cadrul consultatiei medicale este acoperita de secretul medical.\n" +
                        "Precautiunile sunt luate în scopul securitaţii medicale, atât pentru donator cât si pentru\n" +
                        "primitorul de sânge.\n" + "1. Considerati ca aveţi o stare buna de sanatate? " + "DA" + "\n" +
                        "În ultima vreme ati avut: - o pierdere în greutate neasteptata, " + "DA" + "\n" +
                        "                          - febră neexplicabilă " + "NU" + "\n" +
                        "                          - tratament stomatologic, vaccinări" + "DA" + "\n" +
                        "2. Urmaţi vreun tratament medicamentos? " + "NU" + "\n" +
                        "3. În ultimele 12 luni aţi avut contact sexual cu:\n" +
                        "       - un partener cu hepatită sau HIV pozitiv " + "NU" + "\n" +
                        "       - un partener ce se droghează prin injecţii " + "DA" + "\n" +
                        "       - un partener care este plătit pentru sex " + "NU" + "\n" +
                        "       - un partener cu hemofilie " + "NU" + "\n" +
                        "       - parteneri multipli " + "NU" + "\n" +
                        "*V-aţi injectat vreodată droguri? " + "NU" + "\n" +
                        "*Aţi acceptat bani sau droguri pentru a întreţine relaţii sexuale? " + "NU" + "\n" +
                        "*pentru bărbaţi: aţi avut relaţii sexuale cu un alt bărbat? " + "NU" + "\n" +
                        "*pentru femei: a avut partenerul dumneavoastră relaţii sexuale\n" +
                        "cu un alt bărbat? " + "NU" + "\n" +
                        "*aţi schimbat partenerul (partenera) în ultimele 6 luni? " + "NU" + "\n" +
                        "4. De la ultima donare, sau în ultimele 12 luni aţi suferit:\n" +
                        "- o intervenţie chirurgicală sau investigaţii medicale? " + "NU" + "\n" +
                        "- tatuaje, acupunctură, găuri pentru cercei? " + "NU" + "\n" +
                        "- aţi fost transfuzat (a)? " + "NU" + "\n" +
                        "- aţi fost însărcinată? " + "NU" + "\n" +
                        "pentru femei:\n" +
                        "- data naşterii ultimului copil: " + "" + "\n" +
                        "- data ultimei menstruaţii: " + "" + "\n" +
                        "5.V-aţi născut, aţi trăit sau călătorit în străinătate? " + "NU" + "\n" +
                        "Unde? " + "" + "\n" +
                        "6.Aţi fost în detenţie (INCHISOARE) în ultimul an? " + "NU" + "\n" +
                        "7.Aţi fost expuşi la hepatită(bolnavi în familie sau risc profesional)? " + "NU" + "\n" +
                        "8.Aţi suferit vreodată de:\n" +
                        "- icter, tuberculoză, febră reumatică, malarie? " + "NU" + "\n" +
                        "- boli de inimă, tensiune arterială mare sau mică? " + "NU" + "\n" +
                        "- boli transmise sexual (hiv, sifilis etc) " + "NU" + "\n" +
                        "- accidente vasculare cardiace sau cerebrale? " + "NU" + "\n" +
                        "- convulsii, boli nervoase? " + "NU" + "\n" +
                        "- boli cronice (diabet, ulcer, cancer, astm) ? " + "NU" + "\n" +
                        "*Sunteţi fumător? " + "NU" + "\n" +
                        "*Aţi consumat alcool recent? " + "NU" + "\n" +
                        "*Ce băuturi aţi consumat? " + "" + "\n" +
                        "*În ce cantitate? " + "NU" + "\n" +
                        "9.Aţi fost refuzat sau amânat la o donare anterioară? " + "NU" + "\n" +
                        "10. Aveţi pasiuni sau ocupaţii ce necesită atenţie specială 24 ore\n" +
                        "postdonare (de ex: sofer, alpinist, scafandru, etc)? " + "NU" + "\n" +
                        "Declar pe propria răspundere/Semnătura\n" +
                        "Semnătura persoanei care supraveghează completarea chestionarului\n" +
                        "Semnătura medicului din cadrul cabinetului de triaj\n" +
                        "CE TREBUIE SA STIE DONATORII DE SANGE\n" +
                        "a). Importanta sangelui, procesul de donare de sange si principalele beneficii ale pacientului\n" +
                        "transfuzat;\n" +
                        "b). Protectia si confidentialitatea datelor personale ale donatorului, respectiv identitatea si starea\n" +
                        "de sanatate a acestuia , rezultatele testelor efectuate, care nu vor fi furnizate fara autorizare;\n" +
                        "c). Natura procedurilor implicate in donarea de sange sau de componentele sanguine destinate a\n" +
                        "fi transfuzate unor alte persoane si riscurile asociate fiecareia in parte;\n" +
                        "d). Optiunea donatorilor de a se razgandi in ceea ce priveste actul donarii, inainte de a merge\n" +
                        "mai departe sau despre eventualitatea de a se autoexclude in orice moment in cursul procesului de\n" +
                        "donare, fara nici un fel de jena sau discomfort;\n" +
                        "e). Donatorii sa informeze centrul de transfuzie sanguina despre orice eventual eveniment ulterior\n" +
                        "transfuziei ce poate face donarea anterioara improprie pentru terapia transfuzionala;\n" +
                        "f). Responsabilitatea centrului de transfuzie sanguina de a informa donatorul printr-un mecanism\n" +
                        "adecvat care sa asigure pastrarea confidentialitatii , daca rezultatele testelor arata vreo anomalitate\n" +
                        "cu semnificatie asupra starii de sanatate a donatorului; crearea de mecanisme de informare a\n" +
                        "donatorului, ulterior donarii, motivelor pentru care unitatile de sange si componente sanguine\n" +
                        "nefolosite provenite din donarea autologa, vor fi rebutate si nu transfuzate altor pacienti;\n" +
                        "h). Rezultatele testelor ce detecteaza markeri pentru virusuri sau agenti microbieni cu transmitere\n" +
                        "transfuzionala vor duce la excluderea donatorilor si distrugerea unitatilor de sange colectate;\n" +
                        "i). Donatorii sa puna intrebari in orice moment;\n" +
                        "j). La selectia potentialilor donatori, donarea se realizeaza exclusiv la sediul unde se efectueaza\n" +
                        "recoltarea numai de catre personalul instruit al centrelor de transfuzie sanguina teritoriale (CTS),\n" +
                        "precum si la sediul celui mai apropiat centru de transfuzie sanguina;\n" +
                        "k). Donarea de sange, in Romania este voluntara si neremunerata; persoanele care au donat sau\n" +
                        "doneaza sange nu pot primi recompense de natura materiala, cum ar fi premii in bani, gratificatii\n" +
                        "salariale, pensie de stat sau ajutor social, pentru faptul ca au donat sange sau componente sanguine;\n" +
                        "l). Beneficiile si riscurile pentru sanatate ale donarii de sange;\n" +
                        "m). Explicarea criteriilor de excludere pentru donatorii de sange;\n" +
                        "n). Existenta si semnificatia “consimtamantului informat”;\n" +
                        "o). Importanta efectuarii examinarii medicale si a testelor obligatorii, solicitatea antecedentelor\n" +
                        "fiziologice si patologice si motivarea acestora;\n" +
                        "p). Nedeclararea intentionata de catre donator a bolilor transmisibile sau a factorilor de risc\n" +
                        "cunoscuti, constituie infractiune ce se pedepseste conform art.39 si 40 din Legea nr.282/2005;\n" +
                        "q). Pot dona sange la centrul de transfuzie sanguina dintr-un anumit teritoriu cetatenii altor tari,\n" +
                        "care locuiesc in Romania de cel putin un an si care pot prezenta acte doveditoare in acest sens,\n" +
                        "originale sau copii legalizate la notariat;\n" +
                        "r). Cetatenii romani care au domiciliul stabil, serviciul sau studiaza in teritoriul arondat centrului\n" +
                        "de transfuzie sanguina, respectiv cetatenii romani care au donat sange sau componente sanguine\n" +
                        "intr-un alt centru de transfuzie sanguina, dar si-au schimbat domiciliul de cel putin 6 luni in teritoriul\n" +
                        "respectiv si care pot prezenta acte doveditoare, sunt eligibili pentru donarea de sange;\n" +
                        "s). Militarii din unitatile situate in raza teritoriala a centrului de transfuzie sanguina teritorial, pot\n" +
                        "dona sange la acest centru;\n" +
                        "t). Conditiile pe care trebuie sa le indeplineasca potentialul donator in vederea donarii de sange,\n" +
                        "respectiv sa aiba o stare buna de sanatate fizica si mentala, o stare de igiena personala\n" +
                        "corespunzatoare si sa prezinte documentele medicale doveditoare ca a efectuat examenele medicale\n" +
                        "recomandate de medicul responsabil cu selectia donatorilor;\n" +
                        "u). Potentialul donator de sange este eligibil numaiin conditiile in care nu apartine grupului care\n" +
                        "prin comportamentul sexual sau/si habitual il plaseaza in zona cu risc de a contacta sau/si de a\n" +
                        "raspandi afectiuni severe ce se pot transmite prin sange.\n\n\n" +
                        "      Data " + dateFormat.format(date) + "\n\n" +
                        "      AM LUAT LA CUNOSTINTA\n\n\n" +
                        "      SEMNATURA\n\n" +
                        "      ____________";
                document.add(new Paragraph(text));
                document.close();
                System.out.println("S-a creat pdf-ul!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
