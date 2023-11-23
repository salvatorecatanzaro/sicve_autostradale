package com.example.sicve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sicve.entities.HighWay
import com.example.sicve.utils.DBHelper

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ModifyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ModifyFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_modify, container, false)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraint_layout_modify)
        val db : DBHelper = DBHelper(view.context)
        val dbr = db.readableDatabase
        var highway : HighWay? = HighWay.getHighway(dbr)
        var test = ""


        val tutorAttivoSwitch : Switch = Switch(view.context)
        tutorAttivoSwitch.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        tutorAttivoSwitch.x = 10f
        tutorAttivoSwitch.y = 300f
        tutorAttivoSwitch.text = "Tutor attivo?"
        constraintLayout.addView(tutorAttivoSwitch)

        val nomeStazioneEntrataTextView : TextView = TextView(view.context)
        nomeStazioneEntrataTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        nomeStazioneEntrataTextView.x = 10f
        nomeStazioneEntrataTextView.y = 400f
        nomeStazioneEntrataTextView.text = "Stazione Entrata"
        constraintLayout.addView(nomeStazioneEntrataTextView)

        val nomeStazioneEntrataEditTextView : EditText = EditText(view.context)
        nomeStazioneEntrataEditTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        nomeStazioneEntrataEditTextView.x = 500f
        nomeStazioneEntrataEditTextView.y = 360f
        constraintLayout.addView(nomeStazioneEntrataEditTextView)

        val nomeStazioneUscitaTextView : TextView = TextView(view.context)
        nomeStazioneUscitaTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        nomeStazioneUscitaTextView.x = 10f
        nomeStazioneUscitaTextView.y = 500f
        nomeStazioneUscitaTextView.text = "Stazione Uscita"
        constraintLayout.addView(nomeStazioneUscitaTextView)

        val limiteVelocitaTextView : TextView = TextView(view.context)
        limiteVelocitaTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        limiteVelocitaTextView.x = 10f
        limiteVelocitaTextView.y = 600f
        limiteVelocitaTextView.text = "Limite autovelox"
        constraintLayout.addView(limiteVelocitaTextView)





        /*val dynamicButton = Button(view.context)
        // setting layout_width and layout_height using layout parameters
        dynamicButton.setText("Button 4 added dynamically")
        dynamicButton.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        dynamicButton.x = 10f
        dynamicButton.y = 200f
        constraintLayout.addView(dynamicButton)*/

        /*val button = Button(this)
        button.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        button.text = "Click me"
        button.setOnClickListener(View.OnClickListener {
            button.text = "You just clicked me"
        })
        button.setBackgroundColor(Color.GREEN)
        button.setTextColor(Color.RED)
        constraintLayout.addView(button);*/
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment modify.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ModifyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}