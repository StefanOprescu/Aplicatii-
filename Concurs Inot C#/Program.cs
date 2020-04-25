using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Configuration;
using System.Collections.Specialized;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.repository;
using Problema_3_CSharp.me.networking;
using Problema_3_CSharp.me.service;
using Problema_3_CSharp.me.client;

namespace Problema_3_CSharp.me.client
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            IService server = new ServerProxy("127.0.0.1", 55555);
            ClientController ctrl = new ClientController(server);
            Form1 logIn = new Form1(ctrl);
            Application.Run(logIn);
            //RepoAngajat repoAngajat = new RepoAngajat();
            //RepoProba repoProba = new RepoProba();
            //RepoParticipant repoParticipant = new RepoParticipant();
            //RepoProbaParticipant repoProbaParticipant = new RepoProbaParticipant();
            //repoProbaParticipant.FindProbaParticipantDupaParticipant(2).ToList().ForEach(x =>
            //{
            //    Console.WriteLine(repoProba.Get(x.IdProba));
            //});

        }
    }
}
