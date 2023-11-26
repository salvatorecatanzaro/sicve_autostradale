package com.example.sicve

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import com.example.sicve.entities.HighWay
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Utils

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
        val db  = DBHelper(view.context)
        val dbr = db.readableDatabase
        val dbw = db.writableDatabase
        var highway : HighWay? = HighWay.getHighway(dbr)
        for(highWayBlock in highway?.highwayBlock.orEmpty())
        {
            Utils.generateEditTutorForm(highWayBlock, view, dbw)
        }

        val scrollView = view.findViewById<ScrollView>(R.id.scroll_lay_id)
        scrollView.invalidate()
        scrollView.requestLayout()

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