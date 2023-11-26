package com.example.sicve.utils

import android.database.sqlite.SQLiteDatabase
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sicve.R
import com.example.sicve.entities.HighWay
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.Tutor

class Utils {


    companion object {
        var formMap = mutableMapOf<Int, MutableMap<String, Any>>()
        fun highwayBlockBuilder(
            highway: HighWay,
            stazione_entrata: String,
            stazione_uscita: String,
            limite_velocita_int: Int
        )
        {



        }

        fun generateEditTutorForm(highWayBlock: HighwayBlock?, view: View, dbw: SQLiteDatabase)
        {

            var currentTutorMap = mutableMapOf<String, Any>()
            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.linear_lay_id)
            val tutor: Tutor = highWayBlock!!.tutor!!

            // Nome tutor
            val linearLayout0 = LinearLayout(view.context)
            linearLayout0.orientation = LinearLayout.HORIZONTAL
            linearLayout0.layoutParams = getLayoutParams(view, 1)

            // Save modification button
            val buttonSave = Button(view.context)
            buttonSave.text = "V"
            buttonSave.id = View.generateViewId()
            buttonSave.setOnClickListener{
                updateTutor(highWayBlock, formMap.get(buttonSave.id))
                DBHelper.updateTutorModifyView(dbw, highWayBlock.tutor!!)
            }

            val buttonDelete = Button(view.context)
            buttonDelete.text = "X"
            buttonDelete.id = View.generateViewId()
            buttonDelete.setOnClickListener{
                DBHelper.deleteHighwayBlockModifyView(dbw, highWayBlock)
            }


            val title: TextView = TextView(view.context)
            title.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            title.text = tutor.stazioneEntrata
            title.setTypeface(null, Typeface.BOLD)
            title.textSize = 20f
            linearLayout0.addView(title)
            linearLayout0.addView(buttonSave)
            linearLayout0.addView(buttonDelete)
            linearLayoutContainer.addView(linearLayout0)

            // Tutor attivo
            val linearLayout = LinearLayout(view.context)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = getLayoutParams(view, 2)
            val tutorAttivoSwitch : Switch = Switch(view.context)
            tutorAttivoSwitch.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            tutorAttivoSwitch.text = "Tutor attivo?"
            tutorAttivoSwitch.isChecked = tutor.attivo

            linearLayout.addView(tutorAttivoSwitch)
            linearLayoutContainer.addView(linearLayout)
            currentTutorMap.put("tutor_attivo", tutorAttivoSwitch)

            // Stazione Entrata
            val linearLayout2 = LinearLayout(view.context)
            linearLayout2.orientation = LinearLayout.HORIZONTAL
            linearLayout2.layoutParams = getLayoutParams(view, 2)

            val nomeStazioneEntrataTextView : TextView = TextView(view.context)
            nomeStazioneEntrataTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneEntrataTextView.text = "Stazione Entrata"
            val nomeStazioneEntrataEditTextView : EditText = EditText(view.context)
            nomeStazioneEntrataEditTextView.setText(tutor.stazioneEntrata)
            nomeStazioneEntrataEditTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneEntrataEditTextView.setEnabled(false)

            linearLayout2.addView(nomeStazioneEntrataTextView)
            linearLayout2.addView(nomeStazioneEntrataEditTextView)
            linearLayoutContainer.addView(linearLayout2)
            currentTutorMap.put("stazione_entrata", nomeStazioneEntrataEditTextView)

            // Stazione Uscita
            val linearLayout3 = LinearLayout(view.context)
            linearLayout3.orientation = LinearLayout.HORIZONTAL
            linearLayout3.layoutParams = getLayoutParams(view, 2)

            val nomeStazioneUscitaTextView : TextView = TextView(view.context)
            nomeStazioneUscitaTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneUscitaTextView.text = "Stazione Uscita"
            val nomeStazioneUscitaEditTextView : EditText = EditText(view.context)
            nomeStazioneUscitaEditTextView.setText(tutor.stazioneUscita)
            nomeStazioneUscitaEditTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            linearLayout3.addView(nomeStazioneUscitaTextView)
            linearLayout3.addView(nomeStazioneUscitaEditTextView)
            linearLayoutContainer.addView(linearLayout3)
            currentTutorMap.put("stazione_uscita", nomeStazioneUscitaEditTextView)

            // Limite velocita
            val autoveloxList = highWayBlock.tutor!!.listaAutovelox
            for(autovelox in autoveloxList) {
                val linearLayout4 = LinearLayout(view.context)
                linearLayout4.orientation = LinearLayout.HORIZONTAL
                linearLayout4.layoutParams = getLayoutParams(view, 2)

                val limiteVelocitaTextView: TextView = TextView(view.context)
                limiteVelocitaTextView.setLayoutParams(
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                limiteVelocitaTextView.text = "Limite autovelox ${autovelox.id}"
                val limiteVelocitaEditTextView: EditText = EditText(view.context)
                limiteVelocitaEditTextView.setLayoutParams(
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                limiteVelocitaEditTextView.setText(autovelox.limiteVelocita.toString()
                )

                linearLayout4.addView(limiteVelocitaTextView)
                linearLayout4.addView(limiteVelocitaEditTextView)
                linearLayoutContainer.addView(linearLayout4)
                currentTutorMap[autovelox.id.toString()] = limiteVelocitaEditTextView
            }

            formMap.put(buttonSave.id, currentTutorMap)
        }

        private fun updateTutor(highWayBlock: HighwayBlock, tutorMap: MutableMap<String, Any>?) {
            highWayBlock.tutor!!.stazioneEntrata = (tutorMap!!.get("stazione_entrata") as EditText).text.toString()
            highWayBlock.tutor!!.stazioneUscita = (tutorMap.get("stazione_uscita") as EditText).text.toString()
            highWayBlock.tutor!!.attivo = (tutorMap.get("tutor_attivo") as Switch).isChecked()
            highWayBlock.tutor!!.stazioneEntrata = (tutorMap.get("stazione_entrata") as EditText).text.toString()
            for(autovelox in highWayBlock.tutor!!.listaAutovelox){
                if(tutorMap[autovelox.id.toString()] != null)
                {
                    autovelox.limiteVelocita = (tutorMap[autovelox.id.toString()] as TextView).text.toString().toInt()
                }
            }
        }


        fun getLayoutParams(view: View, position: Int) : LinearLayout.LayoutParams{
            /**
             *  The selected position will determine tha margins
             *  1 top 100 -> used for first entry
             *  2 top 0 -> used for mid entries
             */

            val topPositionMap = mapOf<Int, Int>(
                1 to 100,
                2 to 0
            )
            val layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            layoutParams.setMargins(40, topPositionMap[position]!!, 20, 0)
            return layoutParams
        }
    }
}