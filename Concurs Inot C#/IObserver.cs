using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.service
{
    public interface IObserver
    {
        void Update(IList<ProbaDTO> probeDTO, IList<ParticipantDTO> participanti);
    }
}
