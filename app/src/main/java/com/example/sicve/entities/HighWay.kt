package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase

class HighWay(
    var id: Int,
    var highwayBlock: MutableList<HighwayBlock?>

) {


    // Used to declare static members
    companion object{
        fun getHighway(db: SQLiteDatabase?) : HighWay? {
            val instance : HighWay?
            val cursor = db?.query("HIGHWAY", null, null, null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return null
            }
            cursor!!.moveToNext()
            val id = cursor.getInt(0)
            instance = HighWay(id, getHighwayBlock(db, id))
            cursor.close()
            return instance
        }

        private fun getHighwayBlock(db: SQLiteDatabase, idHighway: Int): MutableList<HighwayBlock?> {
            val highwayBlockList = mutableListOf<HighwayBlock?>()
            val cursor = db.query("HIGHWAY_BLOCK", null, "HIGHWAY_FK=$idHighway", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return highwayBlockList
            }
            while(cursor!!.moveToNext()) {
                val highwayBlockId = cursor.getInt(0)
                highwayBlockList.add(HighwayBlock(highwayBlockId, getTutor(db, highwayBlockId)))
            }

            cursor.close()
            return highwayBlockList
        }


        private fun getAutoveloxList(db: SQLiteDatabase?, tutorId: String) : MutableList<Autovelox> {
            val autoveloxList = mutableListOf<Autovelox>()
            val cursor = db?.query("AUTOVELOX", null, "TUTOR_FK='$tutorId'", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return autoveloxList
            }
            while(cursor!!.moveToNext()) {
                val autoveloxId = cursor.getInt(0)
                autoveloxList.add(Autovelox(autoveloxId, cursor.getInt(1), getComputerById(db, autoveloxId)))
            }
            cursor.close()
            return autoveloxList
        }


        private fun getComputerById(db: SQLiteDatabase?, autoveloxId: Int) : Computer {
            var cursor = db?.query("COMPUTER", null, "AUTOVELOX_FK=$autoveloxId", null, null, null, null)
            val multe = mutableListOf<String>()

            if(cursor?.count == 0) {
                cursor.close()
                return Computer(1, multe)
            }
            cursor!!.moveToNext()
            val computerId = cursor.getInt(0)
            cursor.close()
            cursor = db?.query("MULTE", null, "COMPUTER_FK=$computerId", null, null, null, null)
            if(cursor?.count == 0)
                return Computer(computerId, multe)

            while(cursor!!.moveToNext())
            {
                multe.add(cursor.getString(1))
            }
            val computer = Computer(computerId, multe)
            cursor.close()
            return computer
        }

        private fun getTutor(db: SQLiteDatabase?, idHighwayBlock: Int) : Tutor?{
            var tutor : Tutor? = null
            val cursor = db?.query("TUTOR", null, "HIGHWAY_BLOCK_FK=$idHighwayBlock", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return tutor
            }
            cursor!!.moveToNext()
            val attivo = cursor.getInt(0) == 1
            val stazioneEntrata = cursor.getString(1)
            tutor = Tutor(attivo, getAutoveloxList(db, stazioneEntrata), stazioneEntrata, cursor.getString(2))

            cursor.close()
            return tutor
        }
    }




}
