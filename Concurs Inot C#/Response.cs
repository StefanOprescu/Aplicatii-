using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.networking
{
    public interface Response
    {
    }

    public interface UpdateResponse : Response
    {

    }

    [Serializable]
    public class OkResponse : Response { }

    [Serializable]
    public class ErrorResponse : Response
    {
        public string Message { get; }
        public ErrorResponse(String message)
        {
            this.Message = message;
        }
    }


    [Serializable]
    public class GetAngajatResponse : Response
    {
        public Angajat Angajat { get; }
        public GetAngajatResponse(Angajat a)
        {
            Angajat = a;
        }
    }

    [Serializable]
    public class GetParticipantiDTOCuProbaPResponse : Response
    {
        public IList<ParticipantDTO> PDTO { get; }
        public GetParticipantiDTOCuProbaPResponse(IList<ParticipantDTO> participantDTOs)
        {
            PDTO = participantDTOs;
        }
    }

    [Serializable]
    public class GetProbeDTOResponse : Response
    {
        public IEnumerable<ProbaDTO> ProbeDTO { get; }
        public GetProbeDTOResponse(IEnumerable<ProbaDTO> lista)
        {
            ProbeDTO = lista;
        }
    }

    [Serializable]
    public class SaveParticipantResponse : Response
    {
        public Participant Participant { get; }
        public SaveParticipantResponse(Participant p)
        {
            Participant = p;
        }
    }

    [Serializable]
    public class SaveProbeParticipantResponse : Response
    {

    }

    [Serializable]
    public class UpdateProbeDTOResponse : UpdateResponse
    {
        public IList<ProbaDTO> ProbeDTO { get; }

        public IList<ParticipantDTO> ParticipantiDTO { get; }
        public UpdateProbeDTOResponse(IList<ProbaDTO> lista, IList<ParticipantDTO> participanti)
        {
            ProbeDTO = lista;
            ParticipantiDTO = participanti;
        }
    }

}

