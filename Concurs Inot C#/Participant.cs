using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class Participant : Persoana
    {
        public Participant(int id, string nume, int varsta) : base(id, nume, varsta)
        {

        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
