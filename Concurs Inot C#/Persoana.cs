using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class Persoana : Entity<int>
    {
        public string Nume { get; set; }
        public int Varsta { get; set; }
        public Persoana(int id,string nume, int varsta)
        {
            base.Id = id;
            Nume = nume;
            Varsta = varsta;
        }

        public override string ToString()
        {
            return Nume + " " + Varsta + " ani";
        }
    }
}
