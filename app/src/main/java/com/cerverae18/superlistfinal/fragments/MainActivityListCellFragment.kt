package com.cerverae18.superlistfinal.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import com.cerverae18.superlistfinal.ListActivity
import com.cerverae18.superlistfinal.R
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
 * @property list the list to be displayed
 * @property _binding is a FragmentMainActivityListCellBinding? that helps with the accessing of UI elements inside the onCreateView method
 * @property binding is a FragmentMainActivityListCellBinding getter referencing to _binding.
 */
class MainActivityListCellFragment : Fragment() {
    
    // list to be displayed
    private var list: List? = null

    // Fragment binding
    private var _binding: FragmentMainActivityListCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            list = it.getSerializable(ARG_LIST) as List

        }
    }

    /**
     *  This method instantiates a fragment.
     *
     *  @param inflater LayoutInflater object that can be used to inflate any views in the fragment
     *  @param container parent view that the fragment's UI should be attached to.
     *  @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved
     *                            state as given here
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //get color from Theme
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorSecondaryVariant,typedValue,true)

        // Inflate the layout for this fragment
        _binding = FragmentMainActivityListCellBinding.inflate(inflater, container, false)


        // Set CardView background theme color
        binding.mainActivityListCellCardview.setCardBackgroundColor(typedValue.data)
        val sdf = SimpleDateFormat("dd/MM/yyyy")

        // Store Views
        val listNameText = binding.mainActivityListCellName
        val listDateText = binding.mainActivityListCellDate
        
        // Set the text of the list name and date
        listNameText.text = list?.name
        listDateText.text = sdf.format(list?.date?.let { Date(it) })

        // List Cardview click listener
        binding.mainActivityListCellCardview.setOnClickListener {
            Intent(activity, ListActivity::class.java).also {
                it.putExtra(EXTRA.EXTRA_LIST_ID, list?.listId)
                startActivity(it)
            }
        }

        return binding.root
    }

    /**
    * Initialize the contents of the Activity's standard options menu.
    */
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