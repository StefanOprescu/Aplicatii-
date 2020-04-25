using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class ProbaParticipant : Entity<string>
    {
        public int IdProba { get; }
        public int IdParticipant { get; }
        public ProbaParticipant(int idProba, int idParticipant)
        {
            base.Id = idProba.ToString() + "." + idParticipant.ToString();
            IdProba = idProba;
            IdParticipant = idParticipant;
        }
    }
}
