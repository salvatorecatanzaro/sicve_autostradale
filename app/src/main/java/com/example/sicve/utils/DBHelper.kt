package com.example.sicve.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.SimpleDateFormat
import com.example.sicve.entities.Auto
import com.example.sicve.entities.Camion
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.Moto
import com.example.sicve.entities.Tutor
import com.example.sicve.entities.User
import java.util.Date


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    // this is called when getting the db instance eg. db.writableDatabase
    // Remember that this method is only called if the db does not exist.
    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db!!.execSQL("PRAGMA foreign_keys=ON")
    }
    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("PRAGMA foreign_keys=ON")
        val createHighway = "CREATE TABLE  HIGHWAY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT)"

        val createHighwayBlock = "CREATE TABLE  HIGHWAY_BLOCK (" +
                "HIGHWAYBLOCK_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HIGHWAY_FK INTEGER," +
                "CONSTRAINT FK FOREIGN KEY(HIGHWAY_FK) REFERENCES HIGHWAY(_id) ON DELETE CASCADE)"

        val createTutor = "CREATE TABLE  TUTOR (" +
                "ATTIVO INTEGER NOT NULL," +
                "STAZIONE_ENTRATA TEXT NOT NULL," +
                "STAZIONE_USCITA TEXT NOT NULL," +
                "TRATTA_COPERTA_KM INTEGER NOT NULL," +
                "HIGHWAY_BLOCK_FK INTEGER NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(HIGHWAY_BLOCK_FK) REFERENCES HIGHWAY_BLOCK(HIGHWAYBLOCK_PK) ON DELETE CASCADE," +
                "PRIMARY KEY(STAZIONE_ENTRATA))"

        val createAuto = "CREATE TABLE AUTO (" + "TARGA TEXT PRIMARY KEY," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "NUMERO_PORTE INTEGER NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL," +
                "USER_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(USER_FK) REFERENCES USER(USERNAME))"

        val createCamion = "CREATE TABLE CAMION (" + "TARGA TEXT PRIMARY KEY," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "NUMERO_PORTE INTEGER NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL," +
                "USER_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(USER_FK) REFERENCES USER(USERNAME))"

        val createMoto = "CREATE TABLE  MOTO (" + "TARGA TEXT PRIMARY KEY," +
                "NUMERO_RUOTE INTEGER NOT NULL," +
                "CASA_AUTOMOBILISTICA TEXT NOT NULL," +
                "TIPO_VEICOLO TEXT NOT NULL," +
                "VELOCITA_MASSIMA_VEICOLO INTEGER NOT NULL," +
                "USER_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(USER_FK) REFERENCES USER(USERNAME))"

        val createComputer = "CREATE TABLE  COMPUTER (" +
                "COMPUTER_PK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AUTOVELOX_FK INTEGER," +
                "CONSTRAINT FK FOREIGN KEY(AUTOVELOX_FK) REFERENCES AUTOVELOX(ID) ON DELETE CASCADE)"

        val createAutovelox = "CREATE TABLE AUTOVELOX (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LIMITE_VELOCITA INTEGER NOT NULL," +
                "TUTOR_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(TUTOR_FK) REFERENCES TUTOR(STAZIONE_ENTRATA) ON DELETE CASCADE)"

        val carTransitMessage = "CREATE TABLE AUTO_MESSAGE (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MESSAGE TEXT NOT NULL," +
                "TARGA_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(TARGA_FK) REFERENCES AUTO(TARGA) ON DELETE CASCADE)"

        val motoTransitMessage = "CREATE TABLE MOTO_MESSAGE (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MESSAGE TEXT NOT NULL," +
                "TARGA_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(TARGA_FK) REFERENCES CAMION(TARGA) ON DELETE CASCADE)"
        val camionTransitMessage = "CREATE TABLE CAMION_MESSAGE (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MESSAGE TEXT NOT NULL," +
                "TARGA_FK TEXT NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(TARGA_FK) REFERENCES MOTO(TARGA) ON DELETE CASCADE)"
        val createUser = "CREATE TABLE  USER (" + "USERNAME TEXT PRIMARY KEY," +
                "NOME TEXT NOT NULL," +
                "COGNOME TEXT NOT NULL," +
                "PASSWORD TEXT NOT NULL," +
                "RUOLO TEXT NOT NULL)"

        val createMulte = "CREATE TABLE  MULTE (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MESSAGE TEXT NOT NULL," +
                "COMPUTER_FK INTEGER NOT NULL," +
                "CONSTRAINT FK FOREIGN KEY(COMPUTER_FK) REFERENCES COMPUTER(COMPUTER_PK))"

        db.execSQL(createHighway)
        db.execSQL(createHighwayBlock)
        db.execSQL(createTutor)
        db.execSQL(createAuto)
        db.execSQL(createCamion)
        db.execSQL(createMoto)
        db.execSQL(createComputer)
        db.execSQL(createAutovelox)
        db.execSQL(carTransitMessage)
        db.execSQL(camionTransitMessage)
        db.execSQL(motoTransitMessage)
        db.execSQL(createMulte)
        db.execSQL(createUser)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
// Aggiornamento delle tabelle in caso di nuova versione del db
    }

    companion object {

        fun updateTutorModifyView(
            dbw: SQLiteDatabase,
            tutor: Tutor
        ) {
            dbw.beginTransaction()
            var values = ContentValues().apply{
                put("ATTIVO", if (tutor.attivo) 1 else 0)
                put("STAZIONE_ENTRATA", tutor.stazioneEntrata)
                put("STAZIONE_USCITA", tutor.stazioneUscita)
                put("TRATTA_COPERTA_KM", 1)
            }

            val tutorId = dbw.update("TUTOR",  values, "STAZIONE_ENTRATA='${tutor.stazioneEntrata}'", null)
            if(tutorId == -1)
            {
                dbw.endTransaction()
                throw Exception("Error while saving tutor")
            }
            for(autovelox in tutor.listaAutovelox) {
                values = ContentValues().apply {
                    put("LIMITE_VELOCITA", autovelox.limiteVelocita)
                }

                val autoveloxId = dbw.update("AUTOVELOX", values, "ID=${autovelox.id} AND TUTOR_FK='${tutor.stazioneEntrata}'", null)
                if(autoveloxId == -1)
                {
                    dbw.endTransaction()
                    throw Exception("Error while saving autovelox")
                }
            }
            dbw.setTransactionSuccessful()
            dbw.endTransaction()
        }

        fun deleteHighwayBlockModifyView(
            dbw: SQLiteDatabase,
            highwayBlock: HighwayBlock
        ) {

            val hwbid = dbw.delete("HIGHWAY_BLOCK", "HIGHWAYBLOCK_PK=${highwayBlock.id}", null)

        }


        fun insertTutor(
            dbw: SQLiteDatabase,
            stazioneEntrata: String,
            stazioneUscita: String,
            tutorAttivo: Boolean,
            limiteVelocitaList: MutableList<Int>
        ) {
            dbw.beginTransaction()
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
            if(tutorId.toInt() == -1)
            {
                dbw.endTransaction()
                throw Exception("Error while saving data")
            }
            //aggiungo un nuovo autovelox che si riferisce a quel tutor
            for(limiteAutovelox in limiteVelocitaList) {
                values = ContentValues().apply {
                    put("LIMITE_VELOCITA", limiteAutovelox)
                    put("TUTOR_FK", stazioneEntrata)
                }

                val autoveloxId = dbw.insert("AUTOVELOX", null, values)
                if(autoveloxId.toInt() == -1)
                {
                    dbw.endTransaction()
                    throw Exception("Error while saving data")
                }
                //aggiungo un nuovo computer che si riferisce a quel tutor
                values = ContentValues().apply {
                    put("AUTOVELOX_FK", autoveloxId)
                }
                val computerId = dbw.insert("COMPUTER", null, values)
                if(computerId.toInt() == -1)
                {
                    dbw.endTransaction()
                    throw Exception("Error while saving data")
                }

            }
            dbw.setTransactionSuccessful()
            dbw.endTransaction()
        }

        fun deleteAutoveloxById(dbw: SQLiteDatabase, id: Int) {
            dbw.delete("AUTOVELOX", "ID=${id}", null)
        }

        fun insertMessage(targa: String, tipoVeicolo: String, tutor: Tutor, dbw: SQLiteDatabase, extendedMsg: String) {

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val message = "[${currentDate}] - Il veicolo con targa $targa ha percorso la tratta ${tutor.stazioneEntrata} - ${tutor.stazioneUscita} $extendedMsg"
            val values = ContentValues().apply{
                put("MESSAGE", message)
                put("TARGA_FK", targa)
            }

            val messageId = dbw.insert("${tipoVeicolo}_MESSAGE",  null, values)
        }

        fun getMessages(dbr: SQLiteDatabase, tipoVeicolo: String, targa: String): MutableList<String>
        {
            val messageList = mutableListOf<String>()
            val cursor = dbr.query("${tipoVeicolo}_MESSAGE", null, "TARGA_FK='$targa'", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return messageList
            }
            while(cursor!!.moveToNext()) {
                messageList.add(cursor.getString(1))
            }
            cursor.close()
            return messageList
        }

        fun insertAuto(dbw: SQLiteDatabase, username: String, auto: Auto)
        {
            val values = ContentValues().apply{
                put("TARGA", auto.targa)
                put("NUMERO_RUOTE", auto.numeroRuote)
                put("CASA_AUTOMOBILISTICA", auto.casaAutomobilistica)
                put("NUMERO_PORTE", auto.numeroPorte)
                put("TIPO_VEICOLO", "AUTO")
                put("VELOCITA_MASSIMA_VEICOLO", auto.velocitaMassimaVeicolo)
                put("USER_FK", username)
            }

            val result = dbw.insert("AUTO",  null, values)

            if(result.toInt() == -1)
            {
                throw Exception("Error while inserting camion")
            }
        }

        fun insertMoto(dbw: SQLiteDatabase, username: String, moto: Moto)
        {
            val values = ContentValues().apply{
                put("TARGA", moto.targa)
                put("NUMERO_RUOTE", moto.numeroRuote)
                put("CASA_AUTOMOBILISTICA", moto.casaAutomobilistica)
                put("TIPO_VEICOLO", "MOTO")
                put("VELOCITA_MASSIMA_VEICOLO", moto.velocitaMassimaVeicolo)
                put("USER_FK", username)
            }

            val result = dbw.insert("MOTO",  null, values)

            if(result.toInt() == -1)
            {
                throw Exception("Error while inserting camion")
            }
        }

        fun insertCamion(dbw: SQLiteDatabase, username: String, camion: Camion)
        {
            val values = ContentValues().apply{
                put("TARGA", camion.targa)
                put("NUMERO_RUOTE", camion.numeroRuote)
                put("CASA_AUTOMOBILISTICA", camion.casaAutomobilistica)
                put("NUMERO_PORTE", camion.numeroPorte)
                put("TIPO_VEICOLO", "CAMION")
                put("VELOCITA_MASSIMA_VEICOLO", camion.velocitaMassimaVeicolo)
                put("USER_FK", username)
            }

            val result = dbw.insert("CAMION",  null, values)

            if(result.toInt() == -1)
            {
                throw Exception("Error while inserting camion")
            }
        }

        fun deleteVehicleByFk(dbw: SQLiteDatabase, username: String?) {
            val res = dbw.delete("AUTO", "USER_FK='${username}'", null)
            val res1 = dbw.delete("MOTO", "USER_FK='${username}'", null)
            val res2 = dbw.delete("CAMION", "USER_FK='${username}'", null)

        }

        fun insertInfrazioni(listaMulte: MutableList<String>, computerId: Int, dbw: SQLiteDatabase) {
            for(multa in listaMulte) {
                val values = ContentValues().apply {
                    put("COMPUTER_FK", computerId)
                    put("MESSAGE", multa)
                }
                val result = dbw.insert("MULTE", null, values)
            }
        }

        fun insertUser(dbw: SQLiteDatabase, user: User) {
            val values = ContentValues().apply {
                put("USERNAME", user.username)
                put("NOME", user.nome)
                put("COGNOME", user.cognome)
                put("PASSWORD", user.password)
                put("RUOLO", user.ruolo)
            }
            val result = dbw.insert("USER", null, values)

            if(result.toInt() == -1)
            {
                throw Exception("Error while saving data")
            }
        }


        private const val DB_NAME = "sicve"
        private const val DB_VERSION = 1 //gestito dall’utente
    }
}
