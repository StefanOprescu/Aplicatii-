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
using Problema_3_CSharp.me.repository;
using Problema_3_CSharp.me.validator;
using Problema_3_CSharp.me.model;
using Problema_3_CSharp.me.exceptii;
using Problema_3_CSharp.me.client;

namespace Problema_3_CSharp.me.client
{
    public partial class FereastraPrincipala : Form
    {
        private readonly ClientController ctrl;
        private List<ProbaDTO> probeDTO = new List<ProbaDTO>();
        private List<ParticipantDTO> participantiDTO = new List<ParticipantDTO>();
        DataTable tableProbe = new DataTable();
        DataTable tableParticipanti = new DataTable();
        public FereastraPrincipala(ClientController ctr)
        {
            InitializeComponent();
            probeDTO = new List<ProbaDTO>();
            this.ctrl = ctr;
            ctrl.updateEvent += userUpdate;
            //this.probeDTO = ctrl.GetProbeDTO();
            populeazaListaProbe();
        }

        public void userUpdate(object sender, UserEventArgs e)
        {
            if(e.GetUserEventType()== UserEvent.UPDATE)
            {
                probeDTO.Clear();
                List<ProbaDTO> d = (List<ProbaDTO>)e.GetData();
                List<ParticipantDTO> pt = (List<ParticipantDTO>)e.GetData2();
                d.ForEach(x => probeDTO.Add(x));
                pt.ForEach(x => participantiDTO.Add(x));
                tabelProbe.BeginInvoke(new UpdateTableCallBack(this.updateTable), new Object[] { tabelProbe, probeDTO, tabelParticipanti, participantiDTO });
            }
        }

        private void populeazaListaParticipanti(int p)
        {
            tableParticipanti.Rows.Clear();
            tableParticipanti.Columns.Clear();
            tableParticipanti.Columns.Add("Nume", typeof(string));
            tableParticipanti.Columns.Add("Varsta", typeof(Int32));
            tableParticipanti.Columns.Add("Probe", typeof(string));
            tabelParticipanti.DataSource = tableParticipanti;
            foreach(ParticipantDTO participant in ctrl.getParticipantiDTOCuProbaP(p))
            {
                tableParticipanti.Rows.Add(participant.Nume, participant.Varsta, participant.ListaProbe);
            }
        }

        public delegate void UpdateTableCallBack(DataGridView dataGrid, List<ProbaDTO> c, DataGridView dataGrid2, List<ParticipantDTO> participanti);
        private void updateTable(DataGridView dataGrid, List<ProbaDTO> probe, DataGridView dataGrid2 , List<ParticipantDTO> participanti)
        {
            tableProbe.Rows.Clear();
            tableProbe.Columns.Clear();
            tableProbe.Columns.Add("Id", typeof(Int32));
            tableProbe.Columns.Add("Distanta", typeof(float));
            tableProbe.Columns.Add("Stil", typeof(Stiluri));
            tableProbe.Columns.Add("Nr. Participanti", typeof(Int32));
            tabelProbe.DataSource = tableProbe;
            foreach (ProbaDTO pb in probe)
            {
                tableProbe.Rows.Add(pb.Id, pb.Distanta, pb.Stil, pb.NrParticipanti);
            }
            tableParticipanti.Rows.Clear();
            tableParticipanti.Columns.Clear();
            tableParticipanti.Columns.Add("Nume", typeof(string));
            tableParticipanti.Columns.Add("Varsta", typeof(Int32));
            tableParticipanti.Columns.Add("Probe", typeof(string));
            tabelParticipanti.DataSource = tableParticipanti;
            foreach (ParticipantDTO participant in participanti)
            {
                tableParticipanti.Rows.Add(participant.Nume, participant.Varsta, participant.ListaProbe);
            }

        }

        private void populeazaListaProbe()
        {
            tableProbe.Rows.Clear();
            tableProbe.Columns.Clear();
            tableProbe.Columns.Add("Id",typeof (Int32));
            tableProbe.Columns.Add("Distanta", typeof(float));
            tableProbe.Columns.Add("Stil", typeof(Stiluri));
            tableProbe.Columns.Add("Nr. Participanti", typeof(Int32));
            tabelProbe.DataSource = tableProbe;
            foreach (ProbaDTO pb in ctrl.GetProbeDTO())
            {
                tableProbe.Rows.Add(pb.Id, pb.Distanta, pb.Stil, pb.NrParticipanti);
            }
        }

        private void butonLogout_Click(object sender, EventArgs e)
        {
            ctrl.Logout();
            ctrl.updateEvent -= userUpdate;
            Application.Exit();
        }

        private void butonCauta_Click(object sender, EventArgs e)
        {
            int idProba = (Int32)tabelProbe[0, tabelProbe.CurrentRow.Index].Value;
            populeazaListaParticipanti(idProba);
        }

        private void buttonAdauga_Click(object sender, EventArgs e)
        {
            Int32 selectedRowCount = tabelProbe.SelectedRows.Count;
            if (selectedRowCount == 0)
                MessageBox.Show("Trebuie sa selectati cel putin o proba");
            else
            {
                try
                {
                    int varsta = Int32.Parse(textBoxVarsta.Text);
                    try
                    {
                        ctrl.setProbaCurenta((Int32)tabelProbe[0, tabelProbe.CurrentRow.Index].Value);
                        Participant p = ctrl.SaveParticipant(textBoxNume.Text, varsta);
                        List<int> probe = new List<int>();
                        int idProba = 1;
                        for (int i = 0; i < selectedRowCount; i++)
                        {
                            idProba = (Int32)tabelProbe.SelectedRows[i].Cells["Id"].Value;
                            probe.Add(idProba);
                        }
                        ctrl.SaveProbeParticipant(probe, p.Id, ctrl.getProbaCurenta());
                    }
                    catch (ValidationException v)
                    {
                        MessageBox.Show(v.Message);
                    }
                }
                catch (FormatException eroare)
                {
                    MessageBox.Show(eroare.Message);
                }
            }
        }
    }
}
