package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase

class HighWay(
    var id: Int,
    var highwayBlock: MutableList<HighwayBlock?>

) {


    // Used to declare static members
    companion object{
        fun getHighway(db: SQLiteDatabase?) : HighWay? {
            var instance : HighWay? = null
            val cursor = db?.query("HIGHWAY", null, null, null, null, null, null)
            if(cursor?.count == 0)
                return null
            cursor!!.moveToNext()
            val id = cursor.getInt(0)
            instance = HighWay(id, getHighwayBlock(db, id))
            return instance
        }

        private fun getHighwayBlock(db: SQLiteDatabase, idHighway: Int): MutableList<HighwayBlock?> {
            val highwayBlockList = mutableListOf<HighwayBlock?>()
            val cursor = db?.query("HIGHWAY_BLOCK", null, "HIGHWAY_FK=$idHighway", null, null, null, null)
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


        fun getAutoveloxList(db: SQLiteDatabase?) : MutableList<Autovelox> {
            var autoveloxList = mutableListOf<Autovelox>()
            val cursor = db?.query("AUTOVELOX", null, null, null, null, null, null)
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


        fun getComputerById(db: SQLiteDatabase?, autoveloxId: Int) : Computer {
            val cursor = db?.query("COMPUTER", null, "AUTOVELOX_FK=$autoveloxId", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return Computer(cursor.getInt(0))
            }
            cursor!!.moveToNext()
            val computer = Computer(cursor.getInt(0))
            cursor.close()
            return computer
        }

        fun getTutor(db: SQLiteDatabase?, idHighwayBlock: Int) : Tutor?{
            var tutor : Tutor? = null
            val cursor = db?.query("TUTOR", null, "HIGHWAY_BLOCK_FK=$idHighwayBlock", null, null, null, null)
            if(cursor?.count == 0) {
                cursor.close()
                return tutor
            }
            cursor!!.moveToNext()
            val attivo = cursor.getInt(0) == 1
            tutor = Tutor(attivo, getAutoveloxList(db), cursor.getString(1), cursor.getString(2))

            cursor.close()
            return tutor
        }
    }




}
