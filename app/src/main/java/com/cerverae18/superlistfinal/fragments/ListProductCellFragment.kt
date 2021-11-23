package com.cerverae18.superlistfinal.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.cerverae18.superlistfinal.databinding.FragmentListProductCellBinding
import com.cerverae18.superlistfinal.logic.entities.Product

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCTS_LIST = "ARG_PRODUCTS_LIST"

/**
 * A simple [Fragment] subclass.
 * Use the [ListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListProductCellFragment : Fragment() {

    private var list: Product? = null

    private var _binding: FragmentListProductCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getSerializable(ARG_PRODUCTS_LIST) as Product

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListProductCellBinding.inflate(inflater, container, false)

        val listNameText = binding.listProductCellName
        val listCategoryText = binding.listProductCellCategory
        listNameText.text = list?.name
        listCategoryText.text = list?.category?.name

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
        fun newInstance(list: Product) =
            ListProductCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCTS_LIST, list)
                }
            }
    }
}