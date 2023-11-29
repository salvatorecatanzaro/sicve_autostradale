package com.example.sicve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
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
            var highway : HighWay? = HighWay.getHighway(dbr)
            for(highWayBlock in highway?.highwayBlock.orEmpty())
            {
                Utils.generateTutorView(highWayBlock, view, dbw)
            }

            val scrollView = view.findViewById<ScrollView>(R.id.transit_scroll_lay_id)
            scrollView.invalidate()
            scrollView.requestLayout()

            return view
        }

}
