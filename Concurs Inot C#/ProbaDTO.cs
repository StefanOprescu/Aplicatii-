using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class ProbaDTO
    {
        public int Id { get; }
        public float Distanta { get; }
        public Stiluri Stil { get; }
        public int NrParticipanti { get; }

        public ProbaDTO(int id, float distanta, Stiluri stil, int nrParticipanti)
        {
            Id = id;
            Distanta = distanta;
            Stil = stil;
            NrParticipanti = nrParticipanti;
        }
    }
}
