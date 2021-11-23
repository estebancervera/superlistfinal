package com.cerverae18.superlistfinal.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.cerverae18.superlistfinal.ListActivity
import com.cerverae18.superlistfinal.databinding.FragmentMainActivityListCellBinding
import com.cerverae18.superlistfinal.logic.EXTRA
import com.cerverae18.superlistfinal.logic.entities.List
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LIST = "ARG_LIST"



/**
 * A simple [Fragment] subclass.
 * Use the [MainActivityListCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainActivityListCellFragment : Fragment() {

    private var list: List? = null

    private var _binding: FragmentMainActivityListCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getSerializable(ARG_LIST) as List

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainActivityListCellBinding.inflate(inflater, container, false)

        val color = Color.rgb(Random.nextInt(120, 255), Random.nextInt(120, 255), Random.nextInt(120, 255))
        binding.mainActivityListCellCardview.setCardBackgroundColor(color)
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        val listNameText = binding.mainActivityListCellName
        val listDateText = binding.mainActivityListCellDate
        //listNameText.setTextColor(Color.WHITE)
        listNameText.text = list?.name
        listDateText.text = sdf.format(list?.date?.let { Date(it) })
        //listDateText.setTextColor(Color.WHITE)
        binding.mainActivityListCellCardview.setOnClickListener {
            Intent(activity, ListActivity::class.java).also {
                it.putExtra(EXTRA.EXTRA_LIST_ID, list?.listId)
                startActivity(it)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        

       return super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param list List
         * @return A new instance of fragment MainActivityListCellFragment.
         */

        @JvmStatic
        fun newInstance(list: List) =
            MainActivityListCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_LIST, list)

                }
            }
    }
}