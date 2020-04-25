using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using Problema_3_CSharp.me.service;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.networking;

namespace Problema_3_CSharp.me.server
{
    public class ServerObjectWorker : IObserver
    {
        private IService server;
        private TcpClient connection;
        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ServerObjectWorker(IService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = HandleRequest((Request)request);
                    if (response != null)
                    {
                        SendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();

        }

        private Response HandleRequest(Request request)
        {
            Response response = null;
            if( request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;
                Angajat user = logReq.Angajat;
                try
                {
                    lock (server)
                    {
                        server.Check(user);
                        server.Login(user, this);
                    }
                    return new OkResponse();
                }
                catch(ServiceException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if(request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                Angajat angajat = logReq.Angajat;
                try
                {
                    lock (server)
                    {
                        server.Logout(angajat, this);
                    }
                    connected = false;
                    return new OkResponse();
                }
                catch (ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if(request is GetAngajatRequest)
            {
                GetAngajatRequest gar = (GetAngajatRequest)request;
                try
                {
                    Angajat a = server.GetAngajatUsernameParola(gar.Username, gar.Parola);
                    return new GetAngajatResponse(a);
                }
                catch(ServiceException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }
            if(request is GetProbeDTORequest)
            {
                try
                {
                    IEnumerable<ProbaDTO> probeDTO = server.GetProbeDTO();
                    return new GetProbeDTOResponse(probeDTO);
                }
                catch(ServiceException e){
                    return new ErrorResponse(e.Message);
                }
            }
            if(request is GetParticipantiDTOCuProbaPRequest)
            {
                GetParticipantiDTOCuProbaPRequest request1 = (GetParticipantiDTOCuProbaPRequest)request;
                try
                {
                    IList<ParticipantDTO> participanti = server.getParticipantiDTOCuProbaP(request1.Proba).ToList();
                    return new GetParticipantiDTOCuProbaPResponse(participanti);
                }
                catch(ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            if(request is SaveParticipantRequest)
            {
                SaveParticipantRequest save = (SaveParticipantRequest)request;
                try
                {
                    Participant p = server.SaveParticipant(save.ID, save.Nume, save.Varsta);
                    return new SaveParticipantResponse(p);
                }
                catch(Exception e)
                {
                    new ErrorResponse(e.Message);
                }
            }
            if(request is SaveProbeParticipantRequest)
            {
                SaveProbeParticipantRequest request1 = (SaveProbeParticipantRequest)request;
                try
                {
                    server.SaveProbaParticipant(request1.Probe, request1.IdParticipant,request1.ProbaCurenta);
                    return new SaveProbeParticipantResponse();
                }
                catch(Exception e)
                {
                    return new ErrorResponse(e.Message);
                }

            }
            return response;
        }

        public void Update(IList<ProbaDTO> probeDTO, IList<ParticipantDTO> participanti)
        {
            try
            {
                SendResponse(new UpdateProbeDTOResponse(probeDTO,participanti));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

    }
}
