using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.exceptii;

namespace Problema_3_CSharp.me.validator
{
    public class ValidatorParticipant : IValidator<Participant>
    {
        public void Validate(Participant p)
        {
            String erori = "";
            if(p==null)
                throw new ValidationException("Obiectul nu poate fi null!\n");
            if (p.Nume == null)
                throw new ValidationException("Campurile nu pot fi vide!\n");
            if (p.Nume.Equals(""))
                erori += "Numele nu poate fi gol!\n";
            if (p.Varsta < 0)
                erori += "Varsta nu poate fi negativa!\n";
            if (!erori.Equals(""))
                throw new ValidationException(erori);
        }
    }
}
