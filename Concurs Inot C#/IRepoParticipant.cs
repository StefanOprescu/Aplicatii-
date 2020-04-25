using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.repository
{
    public interface IRepoParticipant : IRepository<int,Participant>
    {
        IEnumerable<Participant> FindParticipantiDupaNume(String nume);
        IEnumerable<Participant> FindParticipantiDupaVarsta(int i);
    }
}
