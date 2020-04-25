namespace Problema_3_CSharp.me.client
{
    partial class FereastraPrincipala
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.butonLogout = new System.Windows.Forms.Button();
            this.tabelParticipanti = new System.Windows.Forms.DataGridView();
            this.tabelProbe = new System.Windows.Forms.DataGridView();
            this.butonCauta = new System.Windows.Forms.Button();
            this.labelNume = new System.Windows.Forms.Label();
            this.labelVarsta = new System.Windows.Forms.Label();
            this.textBoxNume = new System.Windows.Forms.TextBox();
            this.textBoxVarsta = new System.Windows.Forms.TextBox();
            this.buttonAdauga = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.tabelParticipanti)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.tabelProbe)).BeginInit();
            this.SuspendLayout();
            // 
            // butonLogout
            // 
            this.butonLogout.Location = new System.Drawing.Point(837, 513);
            this.butonLogout.Name = "butonLogout";
            this.butonLogout.Size = new System.Drawing.Size(75, 23);
            this.butonLogout.TabIndex = 2;
            this.butonLogout.Text = "Logout";
            this.butonLogout.UseVisualStyleBackColor = true;
            this.butonLogout.Click += new System.EventHandler(this.butonLogout_Click);
            // 
            // tabelParticipanti
            // 
            this.tabelParticipanti.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tabelParticipanti.Location = new System.Drawing.Point(523, 39);
            this.tabelParticipanti.Name = "tabelParticipanti";
            this.tabelParticipanti.ReadOnly = true;
            this.tabelParticipanti.Size = new System.Drawing.Size(368, 282);
            this.tabelParticipanti.TabIndex = 1;
            // 
            // tabelProbe
            // 
            this.tabelProbe.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.tabelProbe.Location = new System.Drawing.Point(39, 39);
            this.tabelProbe.Name = "tabelProbe";
            this.tabelProbe.ReadOnly = true;
            this.tabelProbe.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.tabelProbe.Size = new System.Drawing.Size(393, 282);
            this.tabelProbe.TabIndex = 0;
            // 
            // butonCauta
            // 
            this.butonCauta.Location = new System.Drawing.Point(523, 346);
            this.butonCauta.Name = "butonCauta";
            this.butonCauta.Size = new System.Drawing.Size(75, 23);
            this.butonCauta.TabIndex = 3;
            this.butonCauta.Text = "Cauta";
            this.butonCauta.UseVisualStyleBackColor = true;
            this.butonCauta.Click += new System.EventHandler(this.butonCauta_Click);
            // 
            // labelNume
            // 
            this.labelNume.AutoSize = true;
            this.labelNume.Location = new System.Drawing.Point(36, 392);
            this.labelNume.Name = "labelNume";
            this.labelNume.Size = new System.Drawing.Size(35, 13);
            this.labelNume.TabIndex = 4;
            this.labelNume.Text = "Nume";
            // 
            // labelVarsta
            // 
            this.labelVarsta.AutoSize = true;
            this.labelVarsta.Location = new System.Drawing.Point(36, 435);
            this.labelVarsta.Name = "labelVarsta";
            this.labelVarsta.Size = new System.Drawing.Size(37, 13);
            this.labelVarsta.TabIndex = 5;
            this.labelVarsta.Text = "Varsta";
            // 
            // textBoxNume
            // 
            this.textBoxNume.Location = new System.Drawing.Point(93, 385);
            this.textBoxNume.Name = "textBoxNume";
            this.textBoxNume.Size = new System.Drawing.Size(100, 20);
            this.textBoxNume.TabIndex = 6;
            // 
            // textBoxVarsta
            // 
            this.textBoxVarsta.Location = new System.Drawing.Point(93, 428);
            this.textBoxVarsta.Name = "textBoxVarsta";
            this.textBoxVarsta.Size = new System.Drawing.Size(100, 20);
            this.textBoxVarsta.TabIndex = 7;
            // 
            // buttonAdauga
            // 
            this.buttonAdauga.Location = new System.Drawing.Point(39, 491);
            this.buttonAdauga.Name = "buttonAdauga";
            this.buttonAdauga.Size = new System.Drawing.Size(75, 23);
            this.buttonAdauga.TabIndex = 8;
            this.buttonAdauga.Text = "Adauga";
            this.buttonAdauga.UseVisualStyleBackColor = true;
            this.buttonAdauga.Click += new System.EventHandler(this.buttonAdauga_Click);
            // 
            // FereastraPrincipala
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(924, 548);
            this.Controls.Add(this.buttonAdauga);
            this.Controls.Add(this.textBoxVarsta);
            this.Controls.Add(this.textBoxNume);
            this.Controls.Add(this.labelVarsta);
            this.Controls.Add(this.labelNume);
            this.Controls.Add(this.butonCauta);
            this.Controls.Add(this.butonLogout);
            this.Controls.Add(this.tabelParticipanti);
            this.Controls.Add(this.tabelProbe);
            this.Name = "FereastraPrincipala";
            this.Text = "FereastraPrincipala";
            ((System.ComponentModel.ISupportInitialize)(this.tabelParticipanti)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.tabelProbe)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Button butonLogout;
        private System.Windows.Forms.DataGridView tabelParticipanti;
        private System.Windows.Forms.DataGridView tabelProbe;
        private System.Windows.Forms.Button butonCauta;
        private System.Windows.Forms.Label labelNume;
        private System.Windows.Forms.Label labelVarsta;
        private System.Windows.Forms.TextBox textBoxNume;
        private System.Windows.Forms.TextBox textBoxVarsta;
        private System.Windows.Forms.Button buttonAdauga;
    }
}