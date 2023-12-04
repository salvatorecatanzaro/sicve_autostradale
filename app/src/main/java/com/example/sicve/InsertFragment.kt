package com.example.sicve

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
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
        val db = DBHelper(view.context)
        var deleteDb = false
        if(deleteDb) {
            view.context.deleteDatabase("sicve")

        }
        val dbw = db.writableDatabase
        val values = ContentValues().apply{
            put("NAME", "Main")
        }
        val hwid = dbw.insert("HIGHWAY", null, values)
        val saveButton = view.findViewById<Button>(R.id.button)
        val stazioneEntrataEt = view.findViewById<EditText>(R.id.stazione_entrata)
        val stazioneUscitaEt = view.findViewById<EditText>(R.id.stazione_uscita)
        val tutorAttivoSw = view.findViewById<Switch>(R.id.tutor_attivo)
        val limiteVelocitaEt = view.findViewById<EditText>(R.id.limite_autovelox)
        var limiteVelocitaInt = 0

        saveButton.setOnClickListener {

            try {
                limiteVelocitaInt = limiteVelocitaEt.getText().toString().toInt()
            }catch(e: Exception){
                print("cannot cast value")

            }
            val entrata = stazioneEntrataEt.text.toString()
            val uscita = stazioneUscitaEt.text.toString()
            val cursor = dbw?.query("TUTOR", null, "STAZIONE_ENTRATA='$entrata' and STAZIONE_USCITA='$uscita'", null, null, null, null)
            if(cursor?.count != 0){
                cursor?.moveToNext()

                DBHelper.updateTutorInsertView(
                    dbw,
                    stazioneEntrataEt.text.toString(),
                    tutorAttivoSw.isChecked,
                    limiteVelocitaInt
                )
            }
            else{
                // new tutor
                DBHelper.insertTutor(
                    dbw,
                    stazioneEntrataEt.text.toString(),
                    stazioneUscitaEt.text.toString(),
                    tutorAttivoSw.isChecked,
                    limiteVelocitaInt
                )


            }
            cursor!!.close()
        }

        return view
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