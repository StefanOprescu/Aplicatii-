using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Problema_3_CSharp.me.service;
using Problema_3_CSharp.me.repository;
using Problema_3_CSharp.me.validator;
using System.Threading;
using System.Net.Sockets;
 
namespace Problema_3_CSharp.me.server
{
    public class Server : ConcurrentServer
    {
        private IService server;
        private ServerObjectWorker worker;

        public Server(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
        }


        protected override Thread createWorker(TcpClient client)
        {
            worker = new ServerObjectWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
