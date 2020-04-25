using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.repository
{
    public interface IRepoProbaParticipant : IRepository<string,ProbaParticipant>
    {
        int NumarParticipantiProba(Proba p);
        IEnumerable<ProbaParticipant> FindProbaParticipantDupaProba(int p);
        IEnumerable<ProbaParticipant> FindProbaParticipantDupaParticipant(int p);
    }
}
