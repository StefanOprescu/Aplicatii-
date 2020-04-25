using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.exceptii;

namespace Problema_3_CSharp.me.validator
{
    public class ValidatorAngajat : IValidator<Angajat>
    {
        public void Validate(Angajat a)
        {
            String erori = "";
            if (a == null)
                throw new ValidationException("Obiectul nu poate fi null!\n");
            if (a.Nume == null || a.Username == null || a.Parola == null)
                throw new ValidationException("Campurile nu pot fi null!\n");
            if (a.Nume.Equals(""))
                erori += "Numele nu poate fi gol!\n";
            if (a.Varsta < 0)
                erori += "Varsta nu poate fi negativa!\n";
            if (a.Username.Equals(""))
                erori += "Username-ul nu poate fi gol!\n";
            if (a.Parola.Equals(""))
                erori += "Parola nu poate fi goala!\n";
            if (!erori.Equals(""))
                throw new ValidationException(erori);
        }
    }
}
