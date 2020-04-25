using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class Angajat : Persoana
    {
        public string Username { get; set; }
        public string Parola { get; set; }
        public Angajat(int id, string nume, int varsta, string username, string parola): base(id, nume, varsta)
        {
            Username = username;
            Parola = parola;
        }

        public override string ToString()
        {
            return base.ToString();
        }
    }
}
