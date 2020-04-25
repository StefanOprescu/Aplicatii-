using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;
using log4net;

namespace Problema_3_CSharp.me.repository
{
    
    public class RepoProbaParticipant : IRepoProbaParticipant
    {
        private static readonly ILog log = LogManager.GetLogger("LoggerRepoProbaParticipant");
        public ProbaParticipant Delete(ProbaParticipant elem)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ProbaParticipant> FindAll()
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaParticipant(int p)
        {
            log.Info("Se incepe conectarea la bd si selectarea probe participantilor  " + p);
            var con = DBUtils.getConnection();
            IList<ProbaParticipant> probeparticipanti = new List<ProbaParticipant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from ProbeParticipanti where idParticipant=@idParticipant";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@idParticipant";
                paramNume.Value = p;
                comm.Parameters.Add(paramNume);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        ProbaParticipant pp = new ProbaParticipant(id,p);
                        probeparticipanti.Add(pp);
                    }
                }
            }
            return probeparticipanti;
        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaProba(int p)
        {
            log.Info("Se incepe conectarea la bd si selectarea probe participantilor  " + p);
            var con = DBUtils.getConnection();
            IList<ProbaParticipant> probeparticipanti = new List<ProbaParticipant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from ProbeParticipanti where idProba=@idProba";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@idProba";
                paramNume.Value = p;
                comm.Parameters.Add(paramNume);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(1);
                        ProbaParticipant pp = new ProbaParticipant(p,id);
                        probeparticipanti.Add(pp);
                    }
                }
            }
            return probeparticipanti;
        }

        public ProbaParticipant Get(string id)
        {
            throw new NotImplementedException();
        }

        public int NumarParticipantiProba(Proba p)
        {
            throw new NotImplementedException();
        }

        public ProbaParticipant Save(ProbaParticipant elem)
        {
            log.Info("Se incearca adaugarea");
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {

                comm.CommandText = "insert into ProbeParticipanti (idProba,idParticipant)  values (@idProba, @idParticipant)";
                var paramNume = comm.CreateParameter();
                paramNume.ParameterName = "@idProba";
                paramNume.Value = elem.IdProba;
                comm.Parameters.Add(paramNume);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@idParticipant";
                paramVarsta.Value = elem.IdParticipant;
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

        public ProbaParticipant Update(ProbaParticipant elem)
        {
            throw new NotImplementedException();
        }
    }
}
