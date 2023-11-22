package com.example.sicve.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    // this is called when getting the db instance eg. db.writableDatabase
    // Remember that this method is only called if the db does not exist.
    override fun onCreate(db: SQLiteDatabase) {
// Creazione delle tabelle
        //val createHighway = "CREATE TABLE IF NOT EXISTS HIGHWAY (" +
        val createHighway = "CREATE TABLE  HIGHWAY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT)"

        //val createHighwayBlock = "CREATE TABLE IF NOT EXISTS HIGHWAY_BLOCK (" +
        val createHighwayBlock = "CREATE TABLE  HIGHWAY_BLOCK (" +
                "HIGHWAYBLOCK_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HIGHWAY_FK INTEGER," +
                "FOREIGN KEY(HIGHWAY_FK) REFERENCES HIGHWAY(_id))"

        //val createTutor = "CREATE TABLE IF NOT EXISTS TUTOR (" +
        val createTutor = "CREATE TABLE  TUTOR (" +
                "ATTIVO INTEGER NOT NULL," +
                "STAZIONE_ENTRATA TEXT NOT NULL," +
                "STAZIONE_USCITA TEXT NOT NULL," +
                "TRATTA_COPERTA_KM INTEGER NOT NULL," +
                "HIGHWAY_BLOCK_FK INTEGER NOT NULL," +
                "FOREIGN KEY(HIGHWAY_BLOCK_FK) REFERENCES HIGHWAY_BLOCK(HIGHWAYBLOCK_PK)," +
                "PRIMARY KEY(STAZIONE_ENTRATA))"

        //val createAuto = "CREATE TABLE IF NOT EXISTS AUTO (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
        val createAuto = "CREATE TABLE AUTO (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "NUMERO_PORTE INTEGER NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL)"

        //val createCamion = "CREATE TABLE IF NOT EXISTS CAMION (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
        val createCamion = "CREATE TABLE CAMION (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "NUMERO_PORTE INTEGER NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL)"

        //val createMoto = "CREATE TABLE IF NOT EXISTS MOTO (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
        val createMoto = "CREATE TABLE  MOTO (" + "TARGA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "NUMERO_PORTE INTEGER NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL)"

        //val createComputer = "CREATE TABLE IF NOT EXISTS  COMPUTER (" +
        val createComputer = "CREATE TABLE  COMPUTER (" +
                "COMPUTER_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AUTOVELOX_FK INTEGER," +
                "FOREIGN KEY(AUTOVELOX_FK) REFERENCES AUTOVELOX(ID))"

        //val createAutovelox = "CREATE TABLE IF NOT EXISTS AUTOVELOX (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
        val createAutovelox = "CREATE TABLE AUTOVELOX (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LIMITE_VELOCITA INTEGER NOT NULL," +
                "TUTOR_FK TEXT NOT NULL," +
                "FOREIGN KEY(TUTOR_FK) REFERENCES TUTOR(STAZIONE_ENTRATA))"

        db.execSQL(createHighway)
        db.execSQL(createHighwayBlock)
        db.execSQL(createTutor)
        db.execSQL(createAuto)
        db.execSQL(createCamion)
        db.execSQL(createMoto)
        db.execSQL(createComputer)
        db.execSQL(createAutovelox)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
// Aggiornamento delle tabelle in caso di nuova versione del db
    }

    companion object {


        fun updateTutor(
            dbw: SQLiteDatabase,
            stazioneEntrata: String,
            tutorAttivo: Boolean,
            limiteVelocita: Int
        ) {

            // Aggiorno stato del tutor attivo/non attivo

            var values = ContentValues().apply{
                 put("ATTIVO", tutorAttivo)
             }

            val tutorId = dbw.update("TUTOR",  values, "STAZIONE_ENTRATA='$stazioneEntrata'", null)

            values = ContentValues().apply {
                put("LIMITE_VELOCITA", limiteVelocita)
                put("TUTOR_FK", stazioneEntrata)
            }

            val autoveloxId = dbw.insert("AUTOVELOX", null, values)
            //agg
        }

        fun insertTutor(
            dbw: SQLiteDatabase,
            stazioneEntrata: String,
            stazioneUscita: String,
            tutorAttivo: Boolean,
            limiteVelocita: Int
        ) {

             var values = ContentValues().apply{
                put("HIGHWAY_FK", 1)
            }

            val highwayBlockId = dbw.insert("HIGHWAY_BLOCK", null, values)

            values = ContentValues().apply{
                put("ATTIVO", tutorAttivo)
                put("STAZIONE_ENTRATA", stazioneEntrata)
                put("STAZIONE_USCITA", stazioneUscita)
                put("TRATTA_COPERTA_KM", 1)
                put("HIGHWAY_BLOCK_FK", highwayBlockId)
            }

            val tutorId = dbw.insert("TUTOR",  null, values)

            //aggiungo un nuovo autovelox che si riferisce a quel tutor
            values = ContentValues().apply {
                put("LIMITE_VELOCITA", limiteVelocita)
                put("TUTOR_FK", stazioneEntrata)
            }

            val autoveloxId = dbw.insert("AUTOVELOX", null, values)
            //aggiungo un nuovo computer che si riferisce a quel tutor
            values = ContentValues().apply{
                put("AUTOVELOX_FK", autoveloxId)
            }
            val computerId = dbw.insert("COMPUTER", null, values)

        }

        private const val DB_NAME = "sicve"
        private const val DB_VERSION = 1 //gestito dall’utente
    }
}
