using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
namespace Problema_3_CSharp.me.repository
{
    public interface IRepoProba : IRepository<int,Proba>
    {
        IEnumerable<Proba> FindProbeDupaStil(Stiluri stil);
        IEnumerable<Proba> FindProbeDupaDistanta(float distanta);
    }
}
