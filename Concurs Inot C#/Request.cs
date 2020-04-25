using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.networking
{
    public interface Request
    {
    }

    [Serializable]
    public class LoginRequest : Request
    {
        public Angajat Angajat { get; }
        public LoginRequest(Angajat user)
        {
            Angajat = user;
        }
    }

    [Serializable]
    public class CheckRequest : Request
    {
        private Angajat Angajat { get; }
        public CheckRequest(Angajat angajat)
        {
            this.Angajat = angajat;
        }
    }


    [Serializable]
    public class LogoutRequest : Request
    {
        public Angajat Angajat { get; }

        public LogoutRequest(Angajat user)
        {
            this.Angajat = user;
        }

    }

    [Serializable]
    public class GetAngajatRequest : Request
    {
        public String Username { get; }
        public String Parola { get; }
        public GetAngajatRequest(String username, String parola)
        {
            this.Username = username;
            this.Parola = parola;
        }
    }

    [Serializable]
    public class GetParticipantiDTOCuProbaPRequest : Request
    {
        public int Proba { get; }
        public GetParticipantiDTOCuProbaPRequest(int p)
        {
            this.Proba = p;
        }
    }

    [Serializable]
    public class GetProbeDTORequest : Request
    {
    }

    [Serializable]
    public class SaveParticipantRequest : Request
    {
        public int ID { get; }
        public String Nume { get; }
        public int Varsta { get; }
        public SaveParticipantRequest(int id, string nume, int varsta)
        {
            ID = id;
            Nume = nume;
            Varsta = varsta;
        }
    }

    [Serializable]
    public class SaveProbeParticipantRequest : Request
    {
        public List<int> Probe { get; }
        public int IdParticipant { get; }
        public int ProbaCurenta { get; }
        public SaveProbeParticipantRequest(List<int> probe, int idParticipant, int probaCurenta)
        {
            Probe = probe;
            IdParticipant = idParticipant;
            ProbaCurenta = probaCurenta;
        }
    }
}
