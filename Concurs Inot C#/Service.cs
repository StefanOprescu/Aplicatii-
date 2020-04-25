using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.validator;
using Problema_3_CSharp.me.repository;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.service
{
    public class Service :  IService
    {
        private IRepoAngajat repoAngajat;
        private IRepoProba repoProba;
        private IRepoParticipant repoParticipant;
        private IRepoProbaParticipant repoProbaParticipant;
        private IValidator<Angajat> validatorAngajat;
        private IValidator<Proba> validatorProba;
        private IValidator<Participant> validatorParticipant;
        private readonly IDictionary<string, IObserver> clientiLogati;

        public Service(IRepoAngajat repoAngajat, IRepoProba repoProba, IRepoParticipant 
            repoParticipant, IRepoProbaParticipant repoProbaParticipant,
            IValidator<Angajat> validatorAngajat, IValidator<Proba> validatorProba,
            IValidator<Participant> validatorParticipant)
        {
            this.repoAngajat = repoAngajat;
            this.repoProba = repoProba;
            this.repoParticipant = repoParticipant;
            this.repoProbaParticipant = repoProbaParticipant;
            this.validatorAngajat = validatorAngajat;
            this.validatorProba = validatorProba;
            this.validatorParticipant = validatorParticipant;
            clientiLogati = new Dictionary<string, IObserver>();
        }

        /*
         * REPO ANGAJAT
         */
        public Angajat SaveAngajat(int id, String nume, int varsta, String username, String parola)
        {
            lock (this)
            {
                Angajat a = new Angajat(id, nume, varsta, username, parola);
                validatorAngajat.Validate(a);
                return repoAngajat.Save(a);
            }
        }

        public Angajat GetAngajatUsernameParola(String username, String parola)
        {
            lock (this)
            {
                return repoAngajat.GetAngajatUsernameParola(username, parola);
            }
        }

        /*
         * REPO PROBA
         */
        public Proba SaveProba(int id, float distanta, String stil)
        {
            lock (this)
            {
                Proba p = new Proba(id, distanta, (Stiluri)Enum.Parse(typeof(Stiluri), stil));
                validatorProba.Validate(p);
                return repoProba.Save(p);
            }
        }

        public Proba GetProba(int id)
        {
            lock (this)
            {
                return repoProba.Get(id);
            }
        }

        /*
         * REPO PARTICIPANT
         */
        public Participant SaveParticipant(int id, String nume, int varsta)
        {
            lock (this)
            {
                Participant p = new Participant(id, nume, varsta);
                validatorParticipant.Validate(p);
                return repoParticipant.Save(p);
            }
        }

        public Participant GetParticipant(int id)
        {
            lock (this)
            {
                return repoParticipant.Get(id);
            }
        }

        public IEnumerable<Participant> GetParticipantiDupaNume(String nume)
        {
            lock (this)
            {
                return repoParticipant.FindParticipantiDupaNume(nume);
            }
        }

        /*
         * REPO PROBE-PARTICIPANTI
         */
        public void SaveProbaParticipant(List<int> probe, int idParticipant, int probaCurenta)
        {
            lock (this)
            {
                probe.ForEach(x =>
                {
                    repoProbaParticipant.Save(new ProbaParticipant(x, idParticipant));
                });
                NotifyAngajatiAdaugare(probaCurenta);
            }

        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaParticipant(int idParticipant)
        {
            lock (this)
            {
                return repoProbaParticipant.FindProbaParticipantDupaParticipant(idParticipant);
            }
        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaProba(int idProba)
        {
            lock (this)
            {
                return repoProbaParticipant.FindProbaParticipantDupaProba(idProba);
            }
        }

        public IEnumerable<ProbaDTO> GetProbeDTO()
        {
            Console.WriteLine("aici ajunge");
            lock (this)
            {
                Console.WriteLine("si aici ajunge");
                List<ProbaDTO> probeDTO = new List<ProbaDTO>();
                List<Proba> probe = new List<Proba>();
                repoProba.FindAll().ToList().ForEach(x => probe.Add(x));
                probe.ForEach(x =>
                {
                    probeDTO.Add(new ProbaDTO(x.Id, x.Distanta, x.Stil, repoProbaParticipant.FindProbaParticipantDupaProba(x.Id).Count()));
                });
                return probeDTO;
            }
        }

        public IEnumerable<ParticipantDTO> getParticipantiDTOCuProbaP(int p)
        {
            lock (this)
            {
                List<ParticipantDTO> participantiDTO = new List<ParticipantDTO>();
                List<Participant> participanti = new List<Participant>();
                repoProbaParticipant.FindProbaParticipantDupaProba(p).ToList().ForEach(x =>
                {
                    participanti.Add(repoParticipant.Get(x.IdParticipant));
                });
                participanti.ForEach(x =>
                {
                    String listaProbe = "";
                    repoProbaParticipant.FindProbaParticipantDupaParticipant(x.Id).ToList()
                    .ForEach(y =>
                    {
                        listaProbe += repoProba.Get(y.IdProba);
                        listaProbe += ", ";
                    });
                    participantiDTO.Add(new ParticipantDTO(x.Id, x.Nume, x.Varsta, listaProbe));
                });
                return participantiDTO;
            }
        }

        public void Login(Angajat a, IObserver client)
        {
            lock (this)
            {
                if (a != null)
                {
                    if (clientiLogati.ContainsKey(a.Username))
                        throw new ServiceException("User already logged!");
                    clientiLogati.Add(a.Username, client);
                }
                else throw new ServiceException("Authentication failes!");
            }
        }

        public void Logout(Angajat a, IObserver client)
        {
            IObserver localClient = clientiLogati[a.Username];
            if (localClient == null)
                throw new ServiceException("User " + a.Username + " is not logged in.");
            clientiLogati.Remove(a.Username);
        }

        public void Check(Angajat a)
        {
            List<Angajat> angajatList = (List<Angajat>)repoAngajat.FindAll();
            bool gasit = false;
            foreach (Angajat angajat in angajatList)
            {
                if (a.Username.Equals(angajat.Username) && a.Parola.Equals(angajat.Parola))
                {
                    gasit = true;
                    break;
                }
            }
            if (!gasit)
                throw new ServiceException("Datele introduse nu sunt inregistrate!");
        }

        private void NotifyAngajatiAdaugare(int probaCurenta)
        {
            foreach (var cl in clientiLogati.Keys)
            {
                IObserver Client = clientiLogati[cl];
                IList<ParticipantDTO> participants = getParticipantiDTOCuProbaP(probaCurenta).ToList();
                Task.Run(() => Client.Update(GetProbeDTO().ToList(), participants));
            }
        }
    }
}
