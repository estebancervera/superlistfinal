package com.cerverae18.superlistfinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cerverae18.superlistfinal.databinding.FragmentNewListProductCellBinding
import com.cerverae18.superlistfinal.logic.entities.Product


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCT = "EXTRA_PRODUCT"
private const val ARG_PRODUCTS_ADDED_LIST = "EXTRA_PRODUCTS_ADDED_LIST"

/**
 * A simple [Fragment] subclass.
 * Use the [NewListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewListProductCellFragment : Fragment() {


    private var product: Product? = null
    private var productsAdded: HashMap<Product, Int>? = null

    private var _binding: FragmentNewListProductCellBinding? = null
    private val binding get() = _binding!!

    private var quantity = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT) as Product
            productsAdded = it.getSerializable(ARG_PRODUCTS_ADDED_LIST) as HashMap<Product, Int>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewListProductCellBinding.inflate(inflater, container, false)

        binding.newListProductCellNameText.text = product?.name

        binding.btnDecrease.setOnClickListener {
            if(quantity != 0){
                quantity -= 1
                binding.newListProductCellQtyText.text = "$quantity"
                if (quantity == 0){
                    product?.let { product -> productsAdded?.remove(product) }
                    return@setOnClickListener
                }
                product?.let { product -> productsAdded?.put(product, quantity) }

            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity += 1
            product?.let { product -> productsAdded?.put(product, quantity) }
            binding.newListProductCellQtyText.text = "$quantity"
        }

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
        fun newInstance(product: Product, productsAdded: HashMap<Product, Int>) =
            NewListProductCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCT, product)
                    putSerializable(ARG_PRODUCTS_ADDED_LIST, productsAdded)

                }
            }
    }
}