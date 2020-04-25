using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.repository
{
    public interface IRepoAngajat : IRepository<int,Angajat>
    {
        Angajat GetAngajatUsernameParola(string username, string parola);
        IEnumerable<Angajat> FindAngajatiDupaNume(string nume);
    }
}
