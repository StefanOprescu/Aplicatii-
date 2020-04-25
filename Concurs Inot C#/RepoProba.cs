using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using log4net;

namespace Problema_3_CSharp.me.repository
{
    public class RepoProba : IRepoProba
    {
        private static readonly ILog log = LogManager.GetLogger("LoggerRepoProba");
        public Proba Delete(Proba elem)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Proba> FindAll()
        {
            log.Info("Se incepe conectarea la bd si selectarea tuturor probelor");
            var con = DBUtils.getConnection();
            IList<Proba> probe = new List<Proba>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Probe";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        Console.WriteLine("proba " + id);
                        float distanta = dataR.GetFloat(1);
                        Stiluri stil = (Stiluri)Enum.Parse(typeof(Stiluri), dataR.GetString(2));
                        Proba p = new Proba(id, distanta, stil);
                        probe.Add(p);
                    }
                }
            }
            return probe;
        }

        public IEnumerable<Proba> FindProbeDupaDistanta(float distanta)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Proba> FindProbeDupaStil(Stiluri stil)
        {
            throw new NotImplementedException();
        }

        public Proba Get(int id)
        {
            log.InfoFormat("Se cauta proba cu id-ul {0}", id);
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Probe where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        float distanta = dataR.GetFloat(1);
                        Stiluri stil = (Stiluri)Enum.Parse(typeof(Stiluri), dataR.GetString(2));
                        Proba p = new Proba(id, distanta, stil);
                        log.InfoFormat("S-a gasit {0}", p);
                        return p;
                    }
                }
            }
            log.InfoFormat("Nu s-a gasit nicio proba cu id-ul {0}", id);
            return null;
        }

        public Proba Save(Proba elem)
        {
            log.Info("Se incearca adaugarea");
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {

                comm.CommandText = "insert into Probe (distanta,stil)  values (@distanta, @stil)";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@distanta";
                paramNume.Value = elem.Distanta;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@stil";
                paramVarsta.Value = elem.Stil.ToString();
                comm.Parameters.Add(paramVarsta);

                var result = comm.ExecuteNonQuery();

                log.Info("S-a adaugat " + elem.ToString());
                return elem;
            }
        }

        public int Size()
        {
            throw new NotImplementedException();
        }

        public Proba Update(Proba elem)
        {
            throw new NotImplementedException();
        }
    }
}
