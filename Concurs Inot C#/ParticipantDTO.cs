using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class ParticipantDTO
    {
        public int Id { get; }
        public String Nume { get; }
        public int Varsta { get; }
        public String ListaProbe { get; }

        public ParticipantDTO(int id, string nume, int varsta, string listaProbe)
        {
            Id = id;
            Nume = nume;
            Varsta = varsta;
            ListaProbe = listaProbe;
        }
    }
}
