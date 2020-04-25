using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.repository;
using log4net;
using Mono.Data.Sqlite;

namespace Problema_3_CSharp.me.repository
{
    public class RepoAngajat : IRepoAngajat
    {
        private static readonly ILog log = LogManager.GetLogger("LoggerRepoAngajat");
        public Angajat Delete(Angajat elem)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Angajat> FindAll()
        {
            log.Info("Se incepe conectarea la bd si selectarea tuturor angajatilor");
            var con = DBUtils.getConnection();
            IList<Angajat> angajati = new List<Angajat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Angajati";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        string nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        string username = dataR.GetString(3);
                        string parola = dataR.GetString(4);
                        angajati.Add(new Angajat(id,nume,varsta,username,parola));
                    }
                }
            }
            return angajati;
        }

        public IEnumerable<Angajat> FindAngajatiDupaNume(string nume)
        {
            throw new NotImplementedException();
        }

        public Angajat Get(int id)
        {
            throw new NotImplementedException();
        }

        public Angajat GetAngajatUsernameParola(string username, string parola)
        {
            log.InfoFormat("Se cauta angajatul cu username-ul {0} si parola ********", username);
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Angajati where username=@username and parola=@parola";
                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = username;
                comm.Parameters.Add(paramUsername);

                var paramParola = comm.CreateParameter();
                paramParola.ParameterName = "@parola";
                paramParola.Value = parola;
                comm.Parameters.Add(paramParola);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Angajat a = new Angajat(id, nume, varsta, username, parola);
                        log.InfoFormat("S-a gasit {0}", a);
                        return a;
                    }
                }
            }
            log.InfoFormat("Nu s-a gasit niciun angajat");
            return null;
        }

        public Angajat Save(Angajat elem)
        {
            log.Info("Se incearca adaugarea");
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                
                comm.CommandText = "insert into Angajati (nume,varsta,username,parola)  values (@nume, @varsta, @username, @parola)";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = elem.Nume;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@varsta";
                paramVarsta.Value = elem.Varsta;
                comm.Parameters.Add(paramVarsta);

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = elem.Username;
                comm.Parameters.Add(paramUsername);

                var paramParola = comm.CreateParameter();
                paramParola.ParameterName = "@parola";
                paramParola.Value = elem.Parola;
                comm.Parameters.Add(paramParola);

                var result = comm.ExecuteNonQuery();

                log.Info("S-a adaugat " + elem.ToString());
                return elem;
            }
        }

        public int Size()
        {
            throw new NotImplementedException();
        }

        public Angajat Update(Angajat elem)
        {
            throw new NotImplementedException();
        }
    }
}
