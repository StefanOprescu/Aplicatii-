using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.service
{
    public class ServiceException : Exception
    {
        public ServiceException() : base() { }
        public ServiceException(string msg) : base(msg) { }
    }
}
