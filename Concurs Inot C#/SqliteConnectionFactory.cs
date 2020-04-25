using System;
using System.Data;
using Mono.Data.Sqlite;

namespace Problema_3_CSharp.me.repository
{
	public class SqliteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection()
		{
			//String connectionString = System.Configuration.ConfigurationManager.AppSettings.Get("bazaDate");
			return new SqliteConnection("Data Source=D:\\Facultate\\Semestrul 4\\Medii de Programare si Proiectare\\Teme Lab\\Problema 3\\Inot;Version=3");
		}
	}
}
