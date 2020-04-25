using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using log4net;

namespace Problema_3_CSharp.me.repository
{
    public class RepoParticipant : IRepoParticipant
    {
        private static readonly ILog log = LogManager.GetLogger("LoggerRepoParticipant");
        public Participant Delete(Participant elem)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Participant> FindAll()
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Participant> FindParticipantiDupaNume(string nume)
        {
            log.Info("Se incepe conectarea la bd si selectarea participantilor dupa numele " + nume);
            var con = DBUtils.getConnection();
            IList<Participant> participanti = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participanti where nume=@nume";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = nume;
                comm.Parameters.Add(paramNume);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        int varsta = dataR.GetInt32(2);
                        Participant p = new Participant(id, nume, varsta);
                        participanti.Add(p);
                    }
                }
            }
            return participanti;
        }

        public IEnumerable<Participant> FindParticipantiDupaVarsta(int i)
        {
            throw new NotImplementedException();
        }

        public Participant Get(int id)
        {
            log.InfoFormat("Se cauta participantul cu id-ul {0}", id);
            var con = DBUtils.getConnection();

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from Participanti where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        String nume = dataR.GetString(1);
                        int varsta = dataR.GetInt32(2);
                        Participant p = new Participant(id, nume, varsta);
                        log.InfoFormat("S-a gasit {0}", p);
                        return p;
                    }
                }
            }
            log.InfoFormat("Nu s-a gasit niciun participant cu id-ul {0}", id);
            return null;
        }


        public Participant Save(Participant elem)
        {
            log.Info("Se incearca adaugarea");
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {

                comm.CommandText = "insert into Participanti (nume,varsta)  values (@nume, @varsta)";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@nume";
                paramNume.Value = elem.Nume;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@varsta";
                paramVarsta.Value = elem.Varsta;
                comm.Parameters.Add(paramVarsta);

                var result = comm.ExecuteNonQuery();

                log.Info("S-a adaugat " + elem.ToString());
                return FindParticipantiDupaNume(elem.Nume).First();
            }
        }

        public int Size()
        {
            throw new NotImplementedException();
        }

        public Participant Update(Participant elem)
        {
            throw new NotImplementedException();
        }
    }
}
