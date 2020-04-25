using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Problema_3_CSharp.me.service;
using Problema_3_CSharp.me.validator;
using Problema_3_CSharp.me.repository;
using Problema_3_CSharp.me.client;

namespace Problema_3_CSharp.me.client
{
    public partial class Form1 : Form
    {
        private ClientController ctrl;
        public Form1(ClientController ctrl)
        {
            InitializeComponent();
            textBoxParola.PasswordChar = '*';
            this.ctrl = ctrl;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                ctrl.Login(textBoxUsername.Text, textBoxParola.Text);
                this.Visible = false;
                FereastraPrincipala fereastraPrincipala = new FereastraPrincipala(ctrl);
                fereastraPrincipala.Show();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
