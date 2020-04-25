package me.map.lab3.teste;

import me.map.lab3.domain.AnUniversitar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAnUniversitar {
    private void testeazaAnUniversitar(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d1 = sdf.parse("30-09-2019");
            AnUniversitar a = new AnUniversitar(d1);
            assert(a.getSaptamanaCurenta()==1);
            Date d2 = sdf.parse("06-10-2019");
            AnUniversitar a2 = new AnUniversitar(d2);
            assert(a2.getSaptamanaCurenta()==1);
            Date d3 = sdf.parse("07-01-2020");
            AnUniversitar a3 = new AnUniversitar(d3);
            assert(a3.getSaptamanaCurenta()==13);
            Date d4 = sdf.parse("25-03-2020");
            AnUniversitar a4 = new AnUniversitar(d4);
            assert(a4.getSaptamanaCurenta()==5);
            Date d5 = sdf.parse("25-05-2020");
            AnUniversitar a5 = new AnUniversitar(d5);
            assert(a5.getSaptamanaCurenta()==13);
        }
        catch(ParseException e){System.out.println(e.getMessage());}
    }

    public void testAll(){ testeazaAnUniversitar(); }
}
