package com.cerverae18.superlistfinal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cerverae18.superlistfinal.databinding.FragmentNewListProductCellBinding


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCT = "EXTRA_PRODUCT"

/**
 * A simple [Fragment] subclass.
 * Use the [NewListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewListProductCellFragment : Fragment() {



    private var product: Product? = null

    private var _binding: FragmentNewListProductCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT) as Product

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewListProductCellBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param product Parameter 1.

         * @return A new instance of fragment new_list_product_cell.
         */
        @JvmStatic
        fun newInstance(product: Product) =
            NewListProductCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCT, product)

                }
            }
    }
}