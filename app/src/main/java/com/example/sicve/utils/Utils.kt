package com.example.sicve.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.sicve.R
import com.example.sicve.buttons.ButtonEditSave
import com.example.sicve.buttons.ButtonInsert
import com.example.sicve.buttons.ButtonRegister
import com.example.sicve.buttons.ButtonSaveMyVehicle
import com.example.sicve.buttons.ButtonTransit
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.Tutor

class Utils {


    companion object {
        private var NOME_STAZIONE_ENTRATA = "Stazione entrata"
        private var NOME_STAZIONE_USCITA = "Stazione uscita"
        private var LIMITE_VELOCITA_AUTOVELOX = "Limite autovelox"
        private var AGGIUNGI_AUTOVELOX = "Aggiungi autovelox"

        private var formMap = mutableMapOf<Int, MutableMap<String, Any>>()

        fun generateEditTutorForm(highWayBlock: HighwayBlock?, view: View, dbw: SQLiteDatabase)
        {

            val currentTutorMap = mutableMapOf<String, Any>()
            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.linear_lay_id)

            val tutor: Tutor = highWayBlock!!.tutor!!

            val linearLayout0 = LinearLayout(view.context)
            linearLayout0.orientation = LinearLayout.HORIZONTAL
            linearLayout0.layoutParams = getLayoutParamsWrap(40, 100, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayoutContainer.addView(linearLayout0)
            val linearLayout = LinearLayout(view.context)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = getLayoutParamsWrap(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayoutContainer.addView(linearLayout)
            val linearLayout2 = LinearLayout(view.context)
            linearLayout2.orientation = LinearLayout.HORIZONTAL
            linearLayout2.layoutParams = getLayoutParamsWrap(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayoutContainer.addView(linearLayout2)
            val linearLayout3 = LinearLayout(view.context)
            linearLayout3.orientation = LinearLayout.HORIZONTAL
            linearLayout3.layoutParams = getLayoutParamsWrap(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayoutContainer.addView(linearLayout3)
            val autoveloxList = highWayBlock.tutor!!.listaAutovelox

            // Tutor attivo
            val tutorAttivoSwitch = createSwitchCompat("Tutor attivo?", tutor.attivo, view.context)
            linearLayout.addView(tutorAttivoSwitch)
            currentTutorMap["tutor_attivo"] = tutorAttivoSwitch

            // Stazione Entrata
            val nomeStazioneEntrataTextView = createTextView("Stazione Entrata", view.context)
            val nomeStazioneEntrataEditTextView = createEditTextView(tutor.stazioneEntrata, false, view.context)
            linearLayout2.addView(nomeStazioneEntrataTextView)
            linearLayout2.addView(nomeStazioneEntrataEditTextView)
            currentTutorMap["stazione_entrata"] = nomeStazioneEntrataEditTextView

            // Stazione Uscita
            val nomeStazioneUscitaTextView = createTextView("Stazione Uscita", view.context)
            val nomeStazioneUscitaEditTextView = createEditTextView(tutor.stazioneUscita, true, view.context)
            linearLayout3.addView(nomeStazioneUscitaTextView)
            linearLayout3.addView(nomeStazioneUscitaEditTextView)
            currentTutorMap["stazione_uscita"] = nomeStazioneUscitaEditTextView

            val autoveloxLinearLayoutList = mutableListOf<View>()
            // Limite velocita
            for(autovelox in autoveloxList) {
                val linearLayout4 = LinearLayout(view.context)
                autoveloxLinearLayoutList.add(linearLayout4)
                linearLayout4.orientation = LinearLayout.HORIZONTAL
                linearLayout4.layoutParams = getLayoutParamsWrap(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)

                val limiteVelocitaTextView = createTextView("Limite autovelox ${autovelox.id}", view.context)
                val limiteVelocitaEditTextView = createEditTextView(autovelox.limiteVelocita.toString(), true, view.context)
                val buttonDeleteAutovelox = Button(view.context)
                buttonEffect(buttonDeleteAutovelox)
                currentTutorMap[autovelox.id.toString()] = limiteVelocitaEditTextView
                buttonDeleteAutovelox.setOnClickListener{
                    if(autoveloxLinearLayoutList.size == 1)
                    {
                        DBHelper.deleteHighwayBlockModifyView(dbw, highWayBlock)
                        linearLayoutContainer.removeView(linearLayout0)
                        linearLayoutContainer.removeView(linearLayout)
                        linearLayoutContainer.removeView(linearLayout2)
                        linearLayoutContainer.removeView(linearLayout3)
                        for(autoveloxView in autoveloxLinearLayoutList)
                            linearLayoutContainer.removeView(autoveloxView)
                    }else
                    {
                        linearLayoutContainer.removeView(linearLayout4)
                        autoveloxLinearLayoutList.remove(linearLayout4)
                        DBHelper.deleteAutoveloxById(dbw, autovelox.id)
                    }
                }
                buttonDeleteAutovelox.layoutParams = getLayoutParamsWrap(40, 0, 10, 0, false, 80, 80, Gravity.NO_GRAVITY)
                buttonDeleteAutovelox.setBackgroundResource(R.drawable.ic_delete)
                buttonDeleteAutovelox.id = View.generateViewId()

                linearLayout4.addView(limiteVelocitaTextView)
                linearLayout4.addView(limiteVelocitaEditTextView)
                linearLayout4.addView(buttonDeleteAutovelox)
                linearLayoutContainer.addView(linearLayout4)
            }

            // Nome tutor
            val buttonSave = Button(view.context)
            buttonSave.layoutParams = getLayoutParamsWrap(40, 0, 10, 0, false, 80, 80, Gravity.NO_GRAVITY)
            buttonSave.setBackgroundResource(R.drawable.ic_save)
            buttonSave.id = View.generateViewId()
            buttonEffect(buttonSave)
            buttonSave.setOnClickListener{
                val buttonEditSave = ButtonEditSave()
                buttonEditSave.updateTutor(highWayBlock, formMap.get(buttonSave.id), view, dbw)
            }

            val buttonDelete = Button(view.context)
            buttonDelete.layoutParams = getLayoutParamsWrap(40, 0, 10, 0, false, 80, 80, Gravity.NO_GRAVITY)
            buttonEffect(buttonDelete)
            buttonDelete.setBackgroundResource(R.drawable.ic_delete)
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


            val title: TextView = createTextView(tutor.stazioneEntrata, view.context)
            title.setTypeface(null, Typeface.BOLD)
            title.textSize = 20f
            linearLayout0.addView(title)
            linearLayout0.addView(buttonSave)
            linearLayout0.addView(buttonDelete)
            formMap[buttonSave.id] = currentTutorMap
        }


        fun generateTransitView(highWayBlock: HighwayBlock?, view: View, targa: String, tipoVeicolo: String, velMassima: Int, dbw: SQLiteDatabase, messaggiAttivi: SwitchCompat)
        {

            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.transit_linear_lay_id)
            val tutor: Tutor = highWayBlock!!.tutor!!

            val linearLayout0 = LinearLayout(view.context)
            linearLayout0.orientation = LinearLayout.VERTICAL
            linearLayout0.layoutParams = getLayoutParamsFillParentWidth(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
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

            linearLayout0.addView(entrata)
            linearLayout0.addView(uscita)

            var autoveloxCount = 0
            for(autovelox in tutor.listaAutovelox) {
                //val linearLayout4 = LinearLayout(view.context)
                //linearLayout4.orientation = LinearLayout.HORIZONTAL
                //linearLayout4.layoutParams = getLayoutParamsWrap(40, 0, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)

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
                    linearLayout0.addView(limiteVelocitaTextView)
                }
            }

            val buttonTransitHighway = Button(view.context)
            buttonEffect(buttonTransitHighway)
            buttonTransitHighway.layoutParams = getLayoutParamsFillParentWidth(20, 0 ,20, 20, false, 300 ,100, Gravity.END)
            buttonTransitHighway.text = "Percorri tratta"
            buttonTransitHighway.id = View.generateViewId()
            buttonTransitHighway.setBackgroundColor(Color.parseColor("#24A0ED"))
            buttonTransitHighway.setOnClickListener{
                val formMap = mutableMapOf(
                    "messaggi_attivi" to messaggiAttivi,
                    "targa" to targa,
                    "tipo_veicolo" to tipoVeicolo,
                    "tutor" to tutor,
                    "vel_massima" to velMassima
                )
                val buttonTransit = ButtonTransit()
                buttonTransit.autoveloxChecks(formMap, dbw, view)
            }
            linearLayout0.addView(buttonTransitHighway)


        }

        fun generateMessageView(message: String, view: View)
        {

            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.messages_linear_lay_id)
            val linearLayout0 = LinearLayout(view.context)
            val messageView = createTextView(message, view.context)
            messageView.layoutParams = getLayoutParamsWrap(50, 20, 50, 20, true, 0, 0, 0)
            messageView.setBackgroundColor(Color.parseColor("#24A0ED"))
            linearLayout0.addView(messageView)
            linearLayoutContainer.addView(linearLayout0)
        }

        fun generateInsertTutorForm(view: View?, dbw: SQLiteDatabase?) {
            val insertTutorMap = mutableMapOf<String, Any?>(
                "tutor_attivo" to null,
                "stazione_entrata" to null,
                "stazione_uscita" to null,
                "limite_autovelox" to mutableListOf<EditText>()
            )
            val linearLayoutContainer = view!!.findViewById<LinearLayout>(R.id.insert_linear_lay_id)

            val linearLayout0 = LinearLayout(view.context)
            val tutorAttivoView = createSwitchCompat("Tutor attivo?", false, view.context)
            tutorAttivoView.layoutParams = getLayoutParamsWrap(5, 30, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val buttonSave = Button(view.context)
            buttonSave.layoutParams = getLayoutParamsWrap(50, 30, 0, 0, false, 80, 80, Gravity.NO_GRAVITY)
            buttonSave.setBackgroundResource(R.drawable.ic_save)
            buttonEffect(buttonSave)
            buttonSave.setOnClickListener{
                val buttonSaveOperation = ButtonInsert()
                buttonSaveOperation.saveOperation(view, insertTutorMap, dbw!!)
            }
            insertTutorMap["tutor_attivo"] = tutorAttivoView
            linearLayout0.addView(tutorAttivoView)
            linearLayout0.addView(buttonSave)

            val linearLayout1 = LinearLayout(view.context)
            val stazioneEntrataView = createTextView(NOME_STAZIONE_ENTRATA, view.context)
            stazioneEntrataView.layoutParams = getLayoutParamsWrap(5, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayout1.addView(stazioneEntrataView)
            val stazioneEntrataViewEdit = createEditTextView("", true, view.context)
            stazioneEntrataViewEdit.layoutParams = getLayoutParamsWrap(50, 10, 0, 0, false, 500, 120, Gravity.NO_GRAVITY)
            insertTutorMap["stazione_entrata"] = stazioneEntrataViewEdit
            linearLayout1.addView(stazioneEntrataViewEdit)

            val linearLayout2 = LinearLayout(view.context)
            val stazioneUscitaView = createTextView(NOME_STAZIONE_USCITA, view.context)
            stazioneUscitaView.layoutParams = getLayoutParamsWrap(5, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayout2.addView(stazioneUscitaView)
            val stazioneUscitaViewEdit = createEditTextView("", true, view.context)
            stazioneUscitaViewEdit.layoutParams = getLayoutParamsWrap(50 + getMarginLeftBasedOnFirstString(NOME_STAZIONE_ENTRATA, NOME_STAZIONE_USCITA), 10, 0, 0, false, 500, 120, Gravity.NO_GRAVITY)
            insertTutorMap["stazione_uscita"] = stazioneUscitaViewEdit
            linearLayout2.addView(stazioneUscitaViewEdit)

            val linearLayout3 = LinearLayout(view.context)
            val aggiungiAutovelox = createTextView(AGGIUNGI_AUTOVELOX, view.context)
            aggiungiAutovelox.layoutParams = getLayoutParamsWrap(5, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayout3.addView(aggiungiAutovelox)
            val buttonAggiungiAutovelox = Button(view.context)
            buttonEffect(buttonAggiungiAutovelox)
            buttonAggiungiAutovelox.layoutParams = getLayoutParamsWrap(50, 10, 0, 0, false, 80, 80, Gravity.NO_GRAVITY)
            buttonAggiungiAutovelox.setBackgroundResource(R.drawable.ic_add)
            linearLayout3.addView(buttonAggiungiAutovelox)
            buttonAggiungiAutovelox.setOnClickListener{
                val linearLayout4 = LinearLayout(view.context)
                val autoveloxTextView = createTextView(LIMITE_VELOCITA_AUTOVELOX, view.context)
                autoveloxTextView.layoutParams = getLayoutParamsWrap(5, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
                linearLayout4.addView(autoveloxTextView)
                val autoveloxEditTextView = createEditTextView("", true, view.context)
                autoveloxEditTextView.layoutParams = getLayoutParamsWrap(50 + getMarginLeftBasedOnFirstString(NOME_STAZIONE_ENTRATA, LIMITE_VELOCITA_AUTOVELOX), 10, 0, 0, false, 500, 120, Gravity.NO_GRAVITY)
                autoveloxEditTextView.transformationMethod = null
                (insertTutorMap["limite_autovelox"] as MutableList<EditText>).add(autoveloxEditTextView)
                linearLayout4.addView(autoveloxEditTextView)
                val buttonRimuoviAutovelox = Button(view.context)
                buttonEffect(buttonRimuoviAutovelox)
                buttonRimuoviAutovelox.layoutParams = getLayoutParamsWrap(50, 10, 0, 0, false, 80, 80, Gravity.NO_GRAVITY)
                buttonRimuoviAutovelox.setBackgroundResource(R.drawable.ic_delete)
                buttonRimuoviAutovelox.setOnClickListener{
                    linearLayoutContainer.removeView(linearLayout4)
                }
                linearLayout4.addView(buttonRimuoviAutovelox)
                linearLayoutContainer.addView(linearLayout4)

            }

            linearLayoutContainer.addView(linearLayout0)
            linearLayoutContainer.addView(linearLayout1)
            linearLayoutContainer.addView(linearLayout2)
            linearLayoutContainer.addView(linearLayout3)
        }

        private fun getMarginLeftBasedOnFirstString(nomeStazioneEntrata: String, nomeStazioneUscita: String): Int {
            return (nomeStazioneEntrata.length - nomeStazioneUscita.length) * 20
        }

        fun generateComputerView(tutor: Tutor, view: View, dbw:SQLiteDatabase)
        {

            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.info_linear_lay_id)
            for(autovelox in tutor.listaAutovelox) {
                val linearLayout = LinearLayout(view.context)
                val tutorView = createTextView("Tutor ${tutor.stazioneEntrata} - ", view.context)
                tutorView.layoutParams = getLayoutParamsWrap(5, 10, 5, 0, true, 0, 0,0)
                tutorView.setTypeface(null, Typeface.BOLD)
                tutorView.textSize = 20f
                val computerView = createTextView("Computer ${autovelox.computer.id}", view.context)
                computerView.setTypeface(null, Typeface.BOLD)
                computerView.textSize = 20f
                computerView.layoutParams = getLayoutParamsWrap(5, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
                linearLayout.addView(tutorView)
                linearLayout.addView(computerView)
                val linearLayout1 = LinearLayout(view.context)
                val numeroMulte: Int = DBHelper.getNumeroMulte(autovelox.computer.id, dbw)
                val multeView = createTextView("Multe inviate: $numeroMulte", view.context)
                multeView.layoutParams = getLayoutParamsWrap(5, 0, 5, 10, true, 0, 0,0)
                linearLayout1.addView(multeView)

                linearLayoutContainer.addView(linearLayout)
                linearLayoutContainer.addView(linearLayout1)
            }
        }

        fun getLayoutParamsWrap(left: Int, top: Int, right: Int, bottom: Int, wrap: Boolean, width: Int, height: Int, alignment: Int) : LinearLayout.LayoutParams{
            /**
             *  Used to create Layout parameters
             */

            var layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            if(!wrap)
                layoutParams = LinearLayout.LayoutParams(width, height)
            layoutParams.setMargins(left, top, right, bottom)

            return layoutParams
        }

        fun getLayoutParamsFillParentWidth(left: Int, top: Int, right: Int, bottom: Int, fillParent: Boolean, width: Int, height: Int, alignment: Int) : LinearLayout.LayoutParams{
            /**
             *  Used to create Layout parameters
             */

            var layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            if(!fillParent)
                layoutParams = LinearLayout.LayoutParams(width, height)
            layoutParams.setMargins(left, top, right, bottom)
            layoutParams.gravity = alignment

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
            val targaTextView = createTextView("Targa", view.context)
            targaTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val targaEditTextView = createEditTextView(tmpValuesMap["targa"]!!, true, view.context)
            targaEditTextView.layoutParams = getLayoutParamsWrap(255, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            linearLayout.addView(targaTextView)
            linearLayout.addView(targaEditTextView)
            linearLayoutContainer.addView(linearLayout)

            var linearLayout3 = LinearLayout(view.context)
            val velocitaMassimaView = createTextView("Velocita massima", view.context)
            velocitaMassimaView.layoutParams = getLayoutParamsWrap(20, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val velocitaMassimaEditText = createEditTextView(tmpValuesMap["velocita_massima"]!!, true, view.context)
            velocitaMassimaEditText.layoutParams = getLayoutParamsWrap(50, 10, 0, 0, false, 400, 120, Gravity.NO_GRAVITY)
            linearLayout3.addView(velocitaMassimaView)
            linearLayout3.addView(velocitaMassimaEditText)
            linearLayoutContainer.addView(linearLayout3)

            var linearLayout1 = LinearLayout(view.context)
            val casaAutomobilisticaView = createTextView("Casa automobilistica", view.context)
            casaAutomobilisticaView.layoutParams = getLayoutParamsWrap(20, 10, 0, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val casaAutomobilisticaViewEdit = createEditTextView(tmpValuesMap["casa_automobilistica"]!!, true, view.context)
            casaAutomobilisticaViewEdit.layoutParams = getLayoutParamsWrap(0, 10, 0, 0, false, 400, 120, Gravity.NO_GRAVITY)
            linearLayout1.addView(casaAutomobilisticaView)
            linearLayout1.addView(casaAutomobilisticaViewEdit)
            linearLayoutContainer.addView(linearLayout1)

            val spinner = Spinner(view.context)
            val spinnerData = arrayOf("AUTO", "MOTO", "CAMION")
            spinner.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            if(tmpValuesMap["tipo_veicolo"] != "")
                spinner.setSelection(selectionMap[tmpValuesMap["tipo_veicolo"]]!!)
            val linearLayout2 = LinearLayout(view.context)

            val tipoVeicoloView = createTextView("Tipo veicolo", view.context)
            tipoVeicoloView.layoutParams = getLayoutParamsWrap(10, 10, 0, 5, true, 0, 0, Gravity.NO_GRAVITY)
            linearLayout2.addView(tipoVeicoloView)
            linearLayout2.addView(spinner)
            linearLayoutContainer.addView(linearLayout2)

            var linearLayout5 = LinearLayout(view.context)
            val buttonSaveMyVehicle = Button(view.context)
            buttonEffect(buttonSaveMyVehicle)
            buttonSaveMyVehicle.text = "Salva"
            buttonSaveMyVehicle.id = View.generateViewId()
            buttonSaveMyVehicle.setBackgroundColor(Color.parseColor("#24A0ED"))
            buttonSaveMyVehicle.setOnClickListener{
                val formMap = mutableMapOf<String, Any>(
                    "casa_automobilistica" to casaAutomobilisticaViewEdit,
                    "targa" to targaEditTextView,
                    "velocita_massima" to velocitaMassimaEditText,
                    "spinner" to spinner
                )
                val buttonSave = ButtonSaveMyVehicle()
                buttonSave.saveMyVehicle(formMap, dbw, username!!, view)
            }
            linearLayout5.layoutParams = getLayoutParamsWrap(40, 100, 20, 0, true, 0, 0, Gravity.NO_GRAVITY)
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

        fun generateRegisterForm(view: View, dbw: SQLiteDatabase) {
            val formMap = mutableMapOf<String, Any?>(
                "username" to null,
                "nome" to null,
                "cognome" to null,
                "password" to null,
                "repeat_password" to null,
                "ruolo" to null,
            )
            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.linear_lay_id_register)
            val linearLayout = LinearLayout(view.context)
            val usernameTextView = createTextView("Username", view.context)
            usernameTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val usernameEditTextView = createEditTextView("", true, view.context)
            usernameEditTextView.layoutParams = getLayoutParamsWrap(100, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            formMap["username"] = usernameEditTextView
            linearLayout.addView(usernameTextView)
            linearLayout.addView(usernameEditTextView)
            linearLayoutContainer.addView(linearLayout)

            val linearLayout1 = LinearLayout(view.context)
            val nomeTextView = createTextView("Nome", view.context)
            nomeTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val nomeEditTextView = createEditTextView("", true, view.context)
            nomeEditTextView.layoutParams = getLayoutParamsWrap(170, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            formMap["nome"] = nomeEditTextView
            linearLayout1.addView(nomeTextView)
            linearLayout1.addView(nomeEditTextView)
            linearLayoutContainer.addView(linearLayout1)

            val linearLayout2 = LinearLayout(view.context)
            val cognomeTextView = createTextView("Cognome", view.context)
            cognomeTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val cognomeEditTextView = createEditTextView("", true, view.context)
            cognomeEditTextView.layoutParams = getLayoutParamsWrap(110, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            formMap["cognome"] = cognomeEditTextView
            linearLayout2.addView(cognomeTextView)
            linearLayout2.addView(cognomeEditTextView)
            linearLayoutContainer.addView(linearLayout2)

            val linearLayout3 = LinearLayout(view.context)
            val passwordTextView = createTextView("Password", view.context)
            passwordTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val passwordEditTextView = createEditTextView("", true, view.context)
            passwordEditTextView.layoutParams = getLayoutParamsWrap(110, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            formMap["password"] = passwordEditTextView
            linearLayout3.addView(passwordTextView)
            linearLayout3.addView(passwordEditTextView)
            linearLayoutContainer.addView(linearLayout3)

            val linearLayout4 = LinearLayout(view.context)
            val repeatPasswordTextView = createTextView("Ripeti Password", view.context)
            repeatPasswordTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val repeatPasswordEditTextView = createEditTextView("", true, view.context)
            repeatPasswordEditTextView.layoutParams = getLayoutParamsWrap(5, 10, 5, 0, false, 400, 120, Gravity.NO_GRAVITY)
            formMap["repeat_password"] = repeatPasswordEditTextView
            linearLayout4.addView(repeatPasswordTextView)
            linearLayout4.addView(repeatPasswordEditTextView)
            linearLayoutContainer.addView(linearLayout4)

            val linearLayout5 = LinearLayout(view.context)
            val ruoloTextView = createTextView("Ruolo", view.context)
            ruoloTextView.layoutParams = getLayoutParamsWrap(20, 10, 5, 0, true, 0, 0, Gravity.NO_GRAVITY)
            val spinner = Spinner(view.context)
            val spinnerData = arrayOf("ADMIN", "UTENTE")
            spinner.adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_dropdown_item, spinnerData)
            formMap["ruolo"] = spinner
            linearLayout5.addView(ruoloTextView)
            linearLayout5.addView(spinner)
            linearLayoutContainer.addView(linearLayout5)

            val linearLayout6 = LinearLayout(view.context)
            val buttonSave = Button(view.context)
            buttonEffect(buttonSave)
            buttonSave.layoutParams = getLayoutParamsWrap(40, 0, 10, 0, true, 80, 80, Gravity.NO_GRAVITY)
            buttonSave.text = "Save"
            buttonSave.id = View.generateViewId()
            buttonSave.setOnClickListener{
                val buttonRegister = ButtonRegister()
                buttonRegister.saveOperation(view, formMap, dbw)
            }
            linearLayout6.addView(buttonSave)
            linearLayoutContainer.addView(linearLayout6)

        }
        private fun createSwitchCompat(s: String, attivo: Boolean, context: Context): SwitchCompat {
            val result = SwitchCompat(context)
            result.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            result.text = s
            result.isChecked = attivo
            return result
        }

        private fun createEditTextView(s: String, enabled: Boolean, context: Context?): EditText {
            val result = EditText(context)
            result.setText(s)
            result.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            result.setEnabled(enabled)
            return result
        }

        private fun createTextView(s: String, context: Context): TextView {
            val result = TextView(context)
            result.setLayoutParams(ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            result.text = s
            return result
        }

        @SuppressLint("ClickableViewAccessibility")
        fun buttonEffect(buttonSave: Button) {
            buttonSave.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        v.background.setColorFilter(-0x1f0b8ccc, PorterDuff.Mode.SRC_ATOP)
                        v.invalidate()
                    }
                    MotionEvent.ACTION_UP -> {
                        v.background.clearColorFilter()
                        v.invalidate()
                    }
                }
                false
            }
        }
    }

}