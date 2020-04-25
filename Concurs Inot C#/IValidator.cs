using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.validator
{
    public interface IValidator<E>
    {
        void Validate(E e);
    }
}
