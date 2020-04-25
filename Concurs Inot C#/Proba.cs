using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Problema_3_CSharp.me.model
{
    [Serializable]
    public enum Stiluri { liber, spate, fluture, mixt }
    [Serializable]
    public class Proba : Entity<int>
    {
        public Stiluri Stil { get; set; }
        public float Distanta { get; set; }
        public Proba(int id, float distanta, Stiluri stil)
        {
            base.Id = id;
            Distanta = distanta;
            Stil = stil;
        }

        public override string ToString()
        {
            return Distanta + "m " + Stil;
        }
    }
}
