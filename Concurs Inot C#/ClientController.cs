using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.service;
using Problema_3_CSharp.me.model;


namespace Problema_3_CSharp.me.client
{
    public class ClientController : IObserver
    {
        public event EventHandler<UserEventArgs> updateEvent;
        private readonly IService server;
        private int probaCurenta = 1;
        private Angajat angajatCurent;

        public ClientController(IService server)
        {
            this.server = server;
            angajatCurent = null;
        }

        protected virtual void OnUserEvent(UserEventArgs userEvent)
        {
            if (updateEvent == null) return;
            updateEvent(this, userEvent);
        }

        public void Login(String username, String parola)
        {
            Angajat angajat = new Angajat(1, "a", 20, username, parola);
            server.Login(angajat, this);
            angajatCurent = angajat;
        }

        public List<ProbaDTO> GetProbeDTO()
        {
            return server.GetProbeDTO().ToList();
        }

        public void Logout()
        {
            server.Logout(angajatCurent, this);
            angajatCurent = null;
        }

        public Participant SaveParticipant(string nume, int varsta)
        {
            Participant participant = server.SaveParticipant(1, nume, varsta);
            return participant;
        }

        public void Update(IList<ProbaDTO> probe, IList<ParticipantDTO> participanti)
        {
            UserEventArgs userArgs = new UserEventArgs(UserEvent.UPDATE, probe, participanti);
            OnUserEvent(userArgs);
        }

        public List<ParticipantDTO> getParticipantiDTOCuProbaP(int p)
        {
            return server.getParticipantiDTOCuProbaP(p).ToList();
        }

        public void SaveProbeParticipant(List<int> probe, int idParticipant, int probaCurenta)
        {
            server.SaveProbaParticipant(probe, idParticipant, probaCurenta);
        }

        public void setProbaCurenta(int p)
        {
            this.probaCurenta = p;
        }

        public int getProbaCurenta()
        {
            return probaCurenta;
        }
    }
}
