package com.example.sicve

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.sicve.entities.Autovelox
import com.example.sicve.entities.Computer
import com.example.sicve.entities.HighWay
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.Tutor
import com.example.sicve.utils.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InsertFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InsertFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_insert, container, false)
        //view.context.deleteDatabase("sicve")
        val db : DBHelper = DBHelper(view.context)
        val dbw = db.writableDatabase
        var values = ContentValues().apply{
            put("NAME", "Main")
        }

        val highwayId = dbw.insert("HIGHWAY", null, values)
        val saveButton = view.findViewById<Button>(R.id.button)
        val stazione_entrata_et = view.findViewById<EditText>(R.id.stazione_entrata)
        val stazione_uscita_et = view.findViewById<EditText>(R.id.stazione_uscita)
        val tutor_attivo_sw = view.findViewById<Switch>(R.id.tutor_attivo)
        val limite_velocita_et = view.findViewById<EditText>(R.id.limite_autovelox)
        var limite_velocita_int = 0

        saveButton.setOnClickListener {

            try {
                limite_velocita_int = limite_velocita_et.getText().toString().toInt()
            }catch(e: Exception){
                print("cannot cast value")

            }
            val entrata = stazione_entrata_et.text.toString()
            val uscita = stazione_uscita_et.text.toString()
            val cursor = dbw?.query("TUTOR", null, "STAZIONE_ENTRATA='$entrata' and STAZIONE_USCITA='$uscita'", null, null, null, null)
            if(cursor?.count != 0){
                cursor?.moveToNext()

                DBHelper.updateTutor(
                    dbw,
                    stazione_entrata_et.text.toString(),
                    tutor_attivo_sw.isChecked,
                    limite_velocita_int
                )
            }
            else{
                // new tutor
                DBHelper.insertTutor(
                    dbw,
                    stazione_entrata_et.text.toString(),
                    stazione_uscita_et.text.toString(),
                    tutor_attivo_sw.isChecked,
                    limite_velocita_int
                )


            }
            // Autovelox id 0 because it will not be used when inserting into the db,
            // it is an autoincrement column
            val autovelox : Autovelox = Autovelox(0, limite_velocita_int, Computer(-1))
            val tutor : Tutor = Tutor(
                tutor_attivo_sw.isChecked(),
                mutableListOf(),
                stazione_entrata_et.getText().toString(),
                stazione_uscita_et.getText().toString()
                )
        }

        return view
    }

    fun tutorExists(entrata: String, uscita: String, dbw: SQLiteDatabase) : Boolean{
        val cursor = dbw?.query("TUTOR", null, "STAZIONE_ENTRATA='$entrata' and STAZIONE_USCITA='$uscita'", null, null, null, null)
        if(cursor?.count == 0)
            return false
        return true
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment insert.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InsertFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}