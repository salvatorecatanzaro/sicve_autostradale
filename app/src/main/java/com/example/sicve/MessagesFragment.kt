package com.example.sicve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Utils


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MessagesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var username: String? = null
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
        val view = inflater.inflate(R.layout.fragment_messages, container, false)
        val db  = DBHelper(view.context)
        val dbw = db.writableDatabase

        var counter = 0
        var messages: MutableList<String> = mutableListOf()
        val tables = arrayOf("AUTO", "MOTO", "CAMION")
        while(counter < 3 && messages.isEmpty()) {
            val cursor =  dbw?.query(tables[counter], null, "USER_FK='${this.username}'", null, null, null, null)
            if(cursor!!.count == 0){
                print("")
            }
            else{
                cursor.moveToNext()
                messages = DBHelper.getMessages(dbw, cursor.getString(0))
            }
            cursor.close()
            counter += 1
        }


        for(message in messages)
        {
            Utils.generateTutorView(message, view)
        }
        return view
    }

    fun setCurrentUser(username: String?) {
        this.username = username
    }

}
