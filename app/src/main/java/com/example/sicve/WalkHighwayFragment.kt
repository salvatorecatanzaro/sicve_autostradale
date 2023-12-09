package com.example.sicve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.sicve.entities.HighWay
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Utils


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class WalkHighwayFragment : Fragment() {
        // TODO: Rename and change types of parameters
        private var param1: String? = null
        private var param2: String? = null
        private var currentUser: String? = null

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
            val view = inflater.inflate(R.layout.fragment_walk_highway, container, false)
            val db  = DBHelper(view.context)
            val dbr = db.readableDatabase
            val dbw = db.writableDatabase
            val highway : HighWay? = HighWay.getHighway(dbr)

            // Building button needed to check whether the user wants to receive messges or not
            val linearLayoutContainer = view.findViewById<LinearLayout>(R.id.transit_linear_lay_id)
            val linearLayout = LinearLayout(view.context)
            linearLayout.layoutParams = Utils.getLayoutParams(40, 0, 20, 0, true, 0, 0)
            val messaggiAttivi = SwitchCompat(view.context)
            linearLayout.addView(messaggiAttivi)
            messaggiAttivi.text = "Messaggi attivi?"
            linearLayoutContainer.addView(linearLayout)
            var counter = 0
            var targa = ""
            var tipoVeicolo = ""
            val tables = arrayOf("AUTO", "MOTO", "CAMION")
            var velMassima = 0
            while(counter < 3 && targa == "") {
                val cursor =  dbw?.query(tables[counter], null, "USER_FK='${this.currentUser}'", null, null, null, null)
                if(cursor!!.count == 0){
                    print("")
                }
                else {
                    cursor.moveToNext()
                    targa = cursor.getString(0)

                    if (tables[counter] == "MOTO") {
                        velMassima = cursor.getInt(4)
                        tipoVeicolo = cursor.getString(3)
                    }
                    else{
                        velMassima = cursor.getInt(5)
                        tipoVeicolo = cursor.getString(4)
                    }


                }
                cursor.close()
                counter++
            }
            for(highWayBlock in highway?.highwayBlock.orEmpty())
            {
                Utils.generateTransitView(highWayBlock, view, targa, tipoVeicolo, velMassima, dbw, messaggiAttivi)
            }

            val scrollView = view.findViewById<ScrollView>(R.id.transit_scroll_lay_id)
            scrollView.invalidate()
            scrollView.requestLayout()

            return view
        }
        fun setCurrentUser(user: String?){
            this.currentUser = user
        }
}
