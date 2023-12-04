package com.example.sicve.utils

import android.database.sqlite.SQLiteDatabase
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.sicve.R
import com.example.sicve.entities.Auto
import com.example.sicve.entities.ConcreteAutoBuilder
import com.example.sicve.entities.ConcreteCamionBuilder
import com.example.sicve.entities.ConcreteMotoBuilder
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.Tutor
import com.example.sicve.entities.Veicolo
import com.example.sicve.entities.VeicoloBuilder
import kotlin.random.Random

class Utils {


    companion object {
        private var formMap = mutableMapOf<Int, MutableMap<String, Any>>()

        fun generateEditTutorForm(highWayBlock: HighwayBlock?, view: View, dbw: SQLiteDatabase)
        {

            val currentTutorMap = mutableMapOf<String, Any>()
            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.linear_lay_id)
            val tutor: Tutor = highWayBlock!!.tutor!!

            val linearLayout0 = LinearLayout(view.context)
            linearLayoutContainer.addView(linearLayout0)
            val linearLayout = LinearLayout(view.context)
            linearLayoutContainer.addView(linearLayout)
            val linearLayout2 = LinearLayout(view.context)
            linearLayoutContainer.addView(linearLayout2)
            val linearLayout3 = LinearLayout(view.context)
            linearLayoutContainer.addView(linearLayout3)
            val autoveloxList = highWayBlock.tutor!!.listaAutovelox

            // Tutor attivo
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = getLayoutParams(2)
            val tutorAttivoSwitch = SwitchCompat(view.context)
            tutorAttivoSwitch.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            tutorAttivoSwitch.text = "Tutor attivo?"
            tutorAttivoSwitch.isChecked = tutor.attivo
            linearLayout.addView(tutorAttivoSwitch)
            currentTutorMap["tutor_attivo"] = tutorAttivoSwitch

            // Stazione Entrata
            linearLayout2.orientation = LinearLayout.HORIZONTAL
            linearLayout2.layoutParams = getLayoutParams(2)
            val nomeStazioneEntrataTextView = TextView(view.context)
            nomeStazioneEntrataTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneEntrataTextView.text = "Stazione Entrata"
            val nomeStazioneEntrataEditTextView = EditText(view.context)
            nomeStazioneEntrataEditTextView.setText(tutor.stazioneEntrata)
            nomeStazioneEntrataEditTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneEntrataEditTextView.setEnabled(false)

            linearLayout2.addView(nomeStazioneEntrataTextView)
            linearLayout2.addView(nomeStazioneEntrataEditTextView)
            currentTutorMap["stazione_entrata"] = nomeStazioneEntrataEditTextView

            // Stazione Uscita
            linearLayout3.orientation = LinearLayout.HORIZONTAL
            linearLayout3.layoutParams = getLayoutParams(2)

            val nomeStazioneUscitaTextView = TextView(view.context)
            nomeStazioneUscitaTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            nomeStazioneUscitaTextView.text = "Stazione Uscita"
            val nomeStazioneUscitaEditTextView = EditText(view.context)
            nomeStazioneUscitaEditTextView.setText(tutor.stazioneUscita)
            nomeStazioneUscitaEditTextView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            linearLayout3.addView(nomeStazioneUscitaTextView)
            linearLayout3.addView(nomeStazioneUscitaEditTextView)
            currentTutorMap["stazione_uscita"] = nomeStazioneUscitaEditTextView

            val autoveloxLinearLayoutList = mutableListOf<View>()
            // Limite velocita
            for(autovelox in autoveloxList) {
                val linearLayout4 = LinearLayout(view.context)
                autoveloxLinearLayoutList.add(linearLayout4)
                linearLayout4.orientation = LinearLayout.HORIZONTAL
                linearLayout4.layoutParams = getLayoutParams(2)

                val limiteVelocitaTextView = TextView(view.context)
                limiteVelocitaTextView.setLayoutParams(
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                limiteVelocitaTextView.text = "Limite autovelox ${autovelox.id}"
                val limiteVelocitaEditTextView = EditText(view.context)
                limiteVelocitaEditTextView.setLayoutParams(
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                )
                limiteVelocitaEditTextView.setText(autovelox.limiteVelocita.toString()
                )
                val buttonDeleteAutovelox = Button(view.context)
                currentTutorMap[autovelox.id.toString()] = limiteVelocitaEditTextView
                buttonDeleteAutovelox.setOnClickListener{
                    linearLayoutContainer.removeView(linearLayout4)
                    DBHelper.deleteAutoveloxById(dbw, autovelox.id)
                }
                buttonDeleteAutovelox.text = "X"
                buttonDeleteAutovelox.id = View.generateViewId()
                linearLayout4.addView(limiteVelocitaTextView)
                linearLayout4.addView(limiteVelocitaEditTextView)
                linearLayout4.addView(buttonDeleteAutovelox)
                linearLayoutContainer.addView(linearLayout4)
            }

            // Nome tutor
            linearLayout0.orientation = LinearLayout.HORIZONTAL
            linearLayout0.layoutParams = getLayoutParams(1)
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
                linearLayoutContainer.removeView(linearLayout0)
                linearLayoutContainer.removeView(linearLayout)
                linearLayoutContainer.removeView(linearLayout2)
                linearLayoutContainer.removeView(linearLayout3)
                for(autoveloxView in autoveloxLinearLayoutList)
                    linearLayoutContainer.removeView(autoveloxView)
            }


            val title = TextView(view.context)
            title.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            title.text = tutor.stazioneEntrata
            title.setTypeface(null, Typeface.BOLD)
            title.textSize = 20f
            linearLayout0.addView(title)
            linearLayout0.addView(buttonSave)
            linearLayout0.addView(buttonDelete)

            formMap[buttonSave.id] = currentTutorMap
        }


        fun generateTutorView(highWayBlock: HighwayBlock?, view: View, targa: String, velMassima: Int, dbw: SQLiteDatabase, messaggiAttivi: SwitchCompat)
        {

            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.transit_linear_lay_id)
            val tutor: Tutor = highWayBlock!!.tutor!!

            val linearLayout0 = LinearLayout(view.context)
            linearLayoutContainer.addView(linearLayout0)

            val entrata = TextView(view.context)
            entrata.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            entrata.text = tutor.stazioneEntrata
            entrata.setTypeface(null, Typeface.BOLD)
            entrata.textSize = 20f
            val uscita = TextView(view.context)
            uscita.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            uscita.text = tutor.stazioneUscita
            uscita.setTypeface(null, Typeface.BOLD)
            uscita.textSize = 20f
            val buttonTransitHighway = Button(view.context)
            buttonTransitHighway.text = "Percorri tratta"
            buttonTransitHighway.id = View.generateViewId()
            buttonTransitHighway.setOnClickListener{
                val multe = mutableListOf<String>()
                if(messaggiAttivi.isChecked)
                    DBHelper.insertMessage(targa, tutor, dbw, "")

                var autoveloxCount = 0
                var autoveloxSum = 0
                var velMedia = 0
                for(autovelox in tutor.listaAutovelox)
                {
                    val velCorrente = (80..velMassima).shuffled().last()
                    velMedia +=  velCorrente
                    if( velCorrente > autovelox.limiteVelocita) {
                        val msg = "Multa per aver superato il limite di velocità. velocita corrente: $velCorrente all'autovelox con id ${autovelox.id}"
                        multe.add(msg)
                        DBHelper.insertMessage(targa, tutor, dbw, msg)
                    }
                    autoveloxCount += 1
                    autoveloxSum += autovelox.limiteVelocita
                    if(tutor.listaAutovelox.size == autoveloxCount){
                        val velocitaMediaLimite = autoveloxSum / tutor.listaAutovelox.size
                        val velocitaMediaVeicolo = velMedia / tutor.listaAutovelox.size
                        if(velocitaMediaLimite < velocitaMediaVeicolo)
                            autovelox.computer.listaMulte.add("Multa per aver superato il limite di velocita media. Velocita media: $velocitaMediaVeicolo velocita media tutor $velocitaMediaLimite")

                    }
                    autovelox.computer.salvaInfrazioni(autovelox.computer.id, dbw)
                }
            }
            linearLayout0.layoutParams = getLayoutParams(1)
            linearLayout0.addView(entrata)
            linearLayout0.addView(uscita)
            linearLayout0.addView(buttonTransitHighway)

            var autoveloxCount = 0
            for(autovelox in tutor.listaAutovelox) {
                val linearLayout4 = LinearLayout(view.context)
                linearLayout4.orientation = LinearLayout.HORIZONTAL
                linearLayout4.layoutParams = getLayoutParams(2)

                autoveloxCount += 1
                if(tutor.listaAutovelox.size == autoveloxCount) {
                    val limiteVelocitaTextView = TextView(view.context)
                    limiteVelocitaTextView.setLayoutParams(
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                    limiteVelocitaTextView.text = "Numero Autovelox ${autoveloxCount}"
                    limiteVelocitaTextView.textSize = 10f
                    linearLayout4.addView(limiteVelocitaTextView)
                }
                linearLayoutContainer.addView(linearLayout4)
            }

        }

        fun generateTutorView(message: String, view: View)
        {

            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.messages_linear_lay_id)
            val linearLayout0 = LinearLayout(view.context)
            val messageView = TextView(view.context)
            messageView.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            messageView.text = message
            linearLayout0.addView(messageView)
            linearLayoutContainer.addView(linearLayout0)
        }

        private fun updateTutor(highWayBlock: HighwayBlock, tutorMap: MutableMap<String, Any>?) {
            highWayBlock.tutor!!.stazioneEntrata = (tutorMap!!["stazione_entrata"] as EditText).text.toString()
            highWayBlock.tutor!!.stazioneUscita = (tutorMap["stazione_uscita"] as EditText).text.toString()
            highWayBlock.tutor!!.attivo = (tutorMap["tutor_attivo"] as Switch).isChecked()
            highWayBlock.tutor!!.stazioneEntrata = (tutorMap["stazione_entrata"] as EditText).text.toString()
            for(autovelox in highWayBlock.tutor!!.listaAutovelox){
                if(tutorMap[autovelox.id.toString()] != null)
                {
                    autovelox.limiteVelocita = (tutorMap[autovelox.id.toString()] as TextView).text.toString().toInt()
                }
            }
        }


        fun getLayoutParams(position: Int) : LinearLayout.LayoutParams{
            /**
             *  The selected position will determine tha margins
             *  1 top 100 -> used for first entry
             *  2 top 0 -> used for mid entries
             */

            val topPositionMap = mapOf(
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

        fun generateMyCarForm(dbw: SQLiteDatabase?, view: View?, username: String?) {

            val linearLayoutContainer = view!!.findViewById<LinearLayout>(R.id.my_vehicle_linear_lay_id)

            val tmpValuesMap = mutableMapOf(
                "targa" to "",
                "velocita_massima" to "",
                "tipo_veicolo" to "",
                "casa_automobilistica" to ""
            )

            val selectionMap = mutableMapOf(
                "AUTO" to 0,
                "MOTO" to 1,
                "CAMION" to 2
            )

            popTmpMap(tmpValuesMap, username, dbw!!)


            var linearLayout = LinearLayout(view.context)
            val targaTextView = TextView(view.context)
            targaTextView.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            targaTextView.text = "Targa"

            val targaEditTextView = EditText(view.context)
            targaEditTextView.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            targaEditTextView.setText(tmpValuesMap["targa"])
            linearLayout.addView(targaTextView)
            linearLayout.addView(targaEditTextView)
            linearLayoutContainer.addView(linearLayout)

            var linearLayout3 = LinearLayout(view.context)
            val velocitaMassimaView = TextView(view.context)
            velocitaMassimaView.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            velocitaMassimaView.text = "Velocita massima"

            val velocitaMassimaEditText = EditText(view.context)
            velocitaMassimaEditText.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            velocitaMassimaEditText.setText(tmpValuesMap["velocita_massima"])
            linearLayout3.addView(velocitaMassimaView)
            linearLayout3.addView(velocitaMassimaEditText)
            linearLayoutContainer.addView(linearLayout3)

            var linearLayout1 = LinearLayout(view.context)
            val casaAutomobilisticaView = TextView(view.context)
            casaAutomobilisticaView.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            casaAutomobilisticaView.text = "Casa automobilistica"

            val casaAutomobilisticaViewEdit = EditText(view.context)
            casaAutomobilisticaViewEdit.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            casaAutomobilisticaViewEdit.setText(tmpValuesMap["casa_automobilistica"])
            linearLayout1.addView(casaAutomobilisticaView)
            linearLayout1.addView(casaAutomobilisticaViewEdit)
            linearLayoutContainer.addView(linearLayout1)

            val spinner = Spinner(view.context)
            val spinnerData = arrayOf("AUTO", "MOTO", "CAMION")
            spinner.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            if(tmpValuesMap["tipo_veicolo"] != "")
                spinner.setSelection(selectionMap[tmpValuesMap["tipo_veicolo"]]!!)
            val linearLayout2 = LinearLayout(view.context)

            val tipoVeicoloView = TextView(view.context)
            tipoVeicoloView.setLayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
            tipoVeicoloView.text = "Tipo veicolo"
            linearLayout2.addView(tipoVeicoloView)
            linearLayout2.addView(spinner)
            linearLayoutContainer.addView(linearLayout2)

            var linearLayout5 = LinearLayout(view.context)
            val buttonSaveMyVehicle = Button(view.context)
            buttonSaveMyVehicle.text = "Salva"
            buttonSaveMyVehicle.id = View.generateViewId()
            buttonSaveMyVehicle.setOnClickListener{
                DBHelper.deleteVehicleByFk(dbw, username)
                val casaAutomobilistica = casaAutomobilisticaViewEdit.text.toString()
                val targa = targaEditTextView.text.toString()
                val velocitaMassimaVeicolo = velocitaMassimaEditText.text.toString().toInt()
                val veicolo: Veicolo
                var veicoloBuilder: VeicoloBuilder = ConcreteAutoBuilder()

                if(spinner.selectedItem.toString() == "AUTO"){
                    veicoloBuilder
                        .numeroRuote(4)
                        .casaAutomobilistica(casaAutomobilistica)
                        .targa(targa)
                        .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
                }
                else if(spinner.selectedItem.toString() == "MOTO"){
                    veicoloBuilder = ConcreteMotoBuilder()

                    veicoloBuilder
                        .numeroRuote(2)
                        .casaAutomobilistica(casaAutomobilistica)
                        .targa(targa)
                        .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
                }
                else if(spinner.selectedItem.toString() == "CAMION"){
                    veicoloBuilder = ConcreteCamionBuilder()
                    veicoloBuilder
                        .numeroRuote(4)
                        .casaAutomobilistica(casaAutomobilistica)
                        .targa(targa)
                        .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
                }
                veicolo = veicoloBuilder.build()
                veicolo.saveVehicle(dbw!!, username!!)
            }
            linearLayout5.layoutParams = getLayoutParams(1)
            linearLayout5.addView(buttonSaveMyVehicle)
            linearLayoutContainer.addView(linearLayout5)
        }

        private fun popTmpMap(tmpValuesMap: MutableMap<String, String>, username: String?, dbr: SQLiteDatabase) {
            var targaTmp = ""
            var velocitaMasssimaTmp = ""
            var casaAutomobilisticaTmp = ""
            var spinnerTmp = ""

            var cursor = dbr.query("AUTO", null, "USER_FK='${username}'", null, null, null, null)
            if(cursor?.count != 0){
                cursor!!.moveToNext()
                targaTmp = cursor.getString(0)
                velocitaMasssimaTmp = cursor.getInt(5).toString()
                casaAutomobilisticaTmp = cursor.getString(2)
                spinnerTmp = cursor.getString(4)
            }
            cursor = dbr.query("MOTO", null, "USER_FK='${username}'", null, null, null, null)
            if(cursor?.count != 0){
                cursor!!.moveToNext()
                targaTmp = cursor.getString(0)
                velocitaMasssimaTmp = cursor.getInt(4).toString()
                casaAutomobilisticaTmp = cursor.getString(2)
                spinnerTmp = cursor.getString(3)
            }
            cursor = dbr.query("CAMION", null, "USER_FK='${username}'", null, null, null, null)
            if(cursor?.count != 0){
                cursor!!.moveToNext()
                targaTmp = cursor.getString(0)
                velocitaMasssimaTmp = cursor.getInt(5).toString()
                casaAutomobilisticaTmp = cursor.getString(2)
                spinnerTmp = cursor.getString(4)
            }
            cursor.close()

            tmpValuesMap.put("targa", targaTmp)
            tmpValuesMap.put("velocita_massima", velocitaMasssimaTmp)
            tmpValuesMap.put("tipo_veicolo", spinnerTmp)
            tmpValuesMap.put("casa_automobilistica", casaAutomobilisticaTmp)
        }
    }
}