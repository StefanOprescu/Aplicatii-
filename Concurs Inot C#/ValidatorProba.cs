using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.exceptii;

namespace Problema_3_CSharp.me.validator
{
    public class ValidatorProba : IValidator<Proba>
    {
        public void Validate(Proba p)
        {
            String erori = "";
            if(p==null)
                throw new ValidationException("Obiectul nu poate fi null!\n");
            if (p.Distanta < 0)
                erori += "Distanta nu poate fi negativa!\n";
            if (!erori.Equals(""))
                throw new ValidationException(erori);
        }
    }
}
