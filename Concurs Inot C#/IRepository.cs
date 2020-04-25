using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.repository
{
    public interface IRepository<ID,E> where E : Entity<ID>
    {
        E Save(E elem);
        E Update(E elem);
        E Delete(E elem);
        E Get(ID id);
        int Size();
        IEnumerable<E> FindAll();
    }
}
