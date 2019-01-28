package com.example.paulo.healthapp.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dao extends SQLiteOpenHelper
{
    private static final int VERSAO = 1;
    private static final String BANCO_DE_DADOS = "healthapp";

    private static Dao dao;

    private Dao(Context context)
    {
        super(context, BANCO_DE_DADOS, null, VERSAO);
    }

    public static Dao getInstance(Context context)
    {
        if (dao == null)
            dao = new Dao(context);

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query;

        //Table historicomedicamento
        query = "DROP TABLE IF EXISTS `historicomedicamento`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `historicomedicamento` (     " +
                "  `idHistoricoMedicamento` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "  `horaPrevista` INTEGER DEFAULT NULL,                      " +
                "  `horaReal` INTEGER DEFAULT NULL,                          " +
                "  `qtdMedicamento` int(11) DEFAULT NULL,         " +
                "  `qtdTomados` int(11) DEFAULT NULL,         " +
                "  `idMedicamento` int(11) NOT NULL,                    " +
                "  `confirmado` int(1) DEFAULT 0);                    ";

        db.execSQL(query);

        //Table medicamento
        query = "DROP TABLE IF EXISTS `medicamento`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `medicamento` (       " +
                "  `idMedicamento` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                "  `nomeMedicamento` varchar(45) DEFAULT NULL,    " +
                "  `qtdMedicamento` int(11) DEFAULT NULL,         " +
                "  `corMedicamento` varchar(45) DEFAULT NULL,     " +
                "  `horaMedicamento` INTEGER DEFAULT NULL,        " +
                "  `dataInicio` INTEGER DEFAULT NULL,             " +
                "  `dataFinal` INTEGER DEFAULT NULL,              " +
                "  `idTratamento` int(11) NOT NULL,               " +
                "  `possuiHistorico` INTEGER DEFAULT 0,           " +
                "  `qtdTomados` int(11) DEFAULT NULL);";

        db.execSQL(query);

        //Table paciente
        query = "DROP TABLE IF EXISTS `paciente`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `paciente` (                     " +
                "  `idPaciente` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                "  `nomePaciente` varchar(45) DEFAULT NULL,                  " +
                "  `nomeResponsavel` varchar(45) DEFAULT NULL,               " +
                "  `nDeRegistroDaUnidadeSaude` varchar(45) DEFAULT NULL,     " +
                "  `cartaoNacionalDeSaude` varchar(45) DEFAULT NULL,         " +
                "  `dataDeNascimento` INTEGER DEFAULT NULL,                      " +
                "  `telefone` varchar(45) DEFAULT NULL,                      " +
                "  `endereco` varchar(45) DEFAULT NULL,                      " +
                "  `tuberculose` varchar(45) DEFAULT NULL,                  " +
                "  `peso` int(3) DEFAULT NULL);                  ";
                db.execSQL(query);

        //Table responsavel
        query = "DROP TABLE IF EXISTS `responsavel`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `responsavel` (       " +
                "  `idResponsavel` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                "  `nomeResponsavel` varchar(45) DEFAULT NULL,    " +
                "  `cpf` varchar(45) DEFAULT NULL,                " +
                "  `endereco` varchar(45) DEFAULT NULL,           " +
                "  `telefone` varchar(45) DEFAULT NULL);";
        db.execSQL(query);

        //Tabela tratamento
        query = "DROP TABLE IF EXISTS `tratamento`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `tratamento` ("+
                " `idTratamento` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"+
                " `nomeTratamento` varchar(40) NOT NULL,"+
                " `dataInicio` INTEGER DEFAULT NULL,"+
                " `dataTermino` INTEGER DEFAULT NULL,"+
                " `idPaciente` int(11) NOT NULL,"+
                " `idResponsavel` int(11) DEFAULT NULL, "+
                " `doses` int(11) DEFAULT NULL);";
        db.execSQL(query);

        //Tabela Login
        query = "DROP TABLE IF EXISTS `login`;";
        db.execSQL(query);
        query = "CREATE TABLE IF NOT EXISTS `login` ("+
                " `idLogin` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,"+
                " `status` int(1) NOT NULL);";
        db.execSQL(query);
        query = "INSERT INTO login (status) " + "VALUES ("  + 0 + ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        onCreate(db);
    }

}
