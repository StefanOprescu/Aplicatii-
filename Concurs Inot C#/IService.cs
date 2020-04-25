using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.service
{
    public interface IService
    {
        Angajat SaveAngajat(int id, String nume, int varsta, String username, String parola);

        Angajat GetAngajatUsernameParola(String username, String parola);

        Proba SaveProba(int id, float distanta, String stil);

        Proba GetProba(int id);

        Participant SaveParticipant(int id, String nume, int varsta);

        Participant GetParticipant(int id);

        IEnumerable<Participant> GetParticipantiDupaNume(String nume);

        void SaveProbaParticipant(List<int> probe, int idParticipant, int probaCurenta);

        IEnumerable<ProbaParticipant> FindProbaParticipantDupaParticipant(int idParticipant);

        IEnumerable<ProbaParticipant> FindProbaParticipantDupaProba(int idProba);

        IEnumerable<ProbaDTO> GetProbeDTO();

        IEnumerable<ParticipantDTO> getParticipantiDTOCuProbaP(int p);

        void Login(Angajat a, IObserver client);

        void Logout(Angajat a, IObserver client);

        void Check(Angajat a);
    }
}
