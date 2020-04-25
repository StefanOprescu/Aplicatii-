using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.service;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using Problema_3_CSharp.me.model;

namespace Problema_3_CSharp.me.networking
{
    public class ServerProxy : IService
    {
        private string host;
        private int port;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;
        private Queue<Response> responses;
        private IObserver client;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ServerProxy(string host, int port)
        {
            this.port = port;
            this.host = host;
            responses = new Queue<Response>();
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                //output.close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new ServiceException("Error sending object " + e);
            }

        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }

        public virtual void run()
        {
            while (!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is UpdateResponse)
                    {
                        handleUpdate((UpdateResponse)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }

            }
        }


        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        public void Login(Angajat angajat, IObserver client)
        {
            Console.WriteLine("salut 1");
            initializeConnection();
            Console.WriteLine("salut 2");
            sendRequest(new LoginRequest(angajat));
            Console.WriteLine("salut 3");
            Response response = readResponse();
            Console.WriteLine("salut 4");
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new ServiceException(err.Message);
            }
            Console.WriteLine("salut 5");
            this.client = client;
        }

        public void Check(Angajat angajat)
        {
            sendRequest(new CheckRequest(angajat));
            Response response = readResponse();
            if (response is ErrorResponse)
            {
                ErrorResponse errorResponse = (ErrorResponse)response;
                throw new ServiceException(errorResponse.Message);
            }
        }

        public void Logout(Angajat a, IObserver client)
        {
            sendRequest(new LogoutRequest(a));
            Response response = readResponse();
            closeConnection();
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
        }

        public IEnumerable<ProbaDTO> GetProbeDTO()
        {
            Console.Write("1");
            sendRequest(new GetProbeDTORequest());
            Console.Write("2");
            Response response = readResponse();
            Console.Write("3");
            if (response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
            Console.Write("4");
            GetProbeDTOResponse gpr = (GetProbeDTOResponse)response;
            Console.Write("5");
            return gpr.ProbeDTO;
        }

        public IEnumerable<ParticipantDTO> getParticipantiDTOCuProbaP(int p)
        {
            sendRequest(new GetParticipantiDTOCuProbaPRequest(p));
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
            GetParticipantiDTOCuProbaPResponse gppr = (GetParticipantiDTOCuProbaPResponse)response;
            return gppr.PDTO;
        }

        public Angajat SaveAngajat(int id, string nume, int varsta, string username, string parola)
        {
            throw new NotImplementedException();
        }

        public Angajat GetAngajatUsernameParola(string username, string parola)
        {
            sendRequest(new GetAngajatRequest(username, parola));
            Response response = readResponse();
            if(response is OkResponse)
            {
                GetAngajatResponse a = (GetAngajatResponse)response;
                return a.Angajat;
            }
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
            return null;
        }

        public Proba SaveProba(int id, float distanta, string stil)
        {
            throw new NotImplementedException();
        }

        public Proba GetProba(int id)
        {
            throw new NotImplementedException();
        }

        public Participant SaveParticipant(int id, string nume, int varsta)
        {
            sendRequest(new SaveParticipantRequest(id, nume, varsta));
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
            SaveParticipantResponse svp = (SaveParticipantResponse)response;
            return svp.Participant;
        }

        public Participant GetParticipant(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Participant> GetParticipantiDupaNume(string nume)
        {
            throw new NotImplementedException();
        }

        public void SaveProbaParticipant(List<int> probe, int idParticipant, int probaCurenta)
        {
            sendRequest(new SaveProbeParticipantRequest(probe, idParticipant, probaCurenta));
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ServiceException(err.Message);
            }
        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaParticipant(int idParticipant)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<ProbaParticipant> FindProbaParticipantDupaProba(int idProba)
        {
            throw new NotImplementedException();
        }

        private void handleUpdate(UpdateResponse response)
        {
            if(response is UpdateProbeDTOResponse)
            {
                UpdateProbeDTOResponse r = (UpdateProbeDTOResponse)response;
                try
                {
                    client.Update(r.ProbeDTO,r.ParticipantiDTO);
                }
                catch (ServiceException se)
                {
                    Console.WriteLine(se.Message);
                }
            }
        }
    }
}
