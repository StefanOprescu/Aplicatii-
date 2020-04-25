using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.exceptii
{
    public class ValidationException : Exception 
    {
        public ValidationException(String mesaj) : base(mesaj)
        {

        }
    }
}
