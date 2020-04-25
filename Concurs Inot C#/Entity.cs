using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public class Entity<ID>
    {
        public ID Id { get; set; }
    }
}
