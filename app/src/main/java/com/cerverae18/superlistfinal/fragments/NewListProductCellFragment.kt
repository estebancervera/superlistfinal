package com.cerverae18.superlistfinal.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cerverae18.superlistfinal.R
import com.cerverae18.superlistfinal.databinding.FragmentNewListProductCellBinding
import com.cerverae18.superlistfinal.logic.entities.Product


// constants to make sure the same key  is used in the parameters of the onCreate and  factory methods
private const val ARG_PRODUCT = "EXTRA_PRODUCT"
private const val ARG_PRODUCTS_ADDED_LIST = "EXTRA_PRODUCTS_ADDED_LIST"

/**
 * A simple [Fragment] subclass.
 * Use the [NewListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 * @property product is the product from which the fragment's data is rendered
 * @property productsAdded is a HashMap<Product, Int> reference to add products and quantities when the user clicks the buttons
 * @property _binding is a FragmentNewListProductCellBinding? that helps with the accessing of UI elements inside the onCreateView method
 * @property binding is a FragmentNewListProductCellBinding getter referencing to _binding.
 * @property quantity is an Int that represents the number the user is currently on after pressing the add/decrease buttons
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
    /**
     *  This method renders a fragment's view.
     *
     *  @param inflater LayoutInflater object that can be used to inflate any views in the fragment
     *  @param parent parent view that the fragment's UI should be attached to.
     *  @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved
     *                            state as given here
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //gets color from theme
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorSecondaryVariant,typedValue,true)

        // Inflate the layout for this fragment
        _binding = FragmentNewListProductCellBinding.inflate(inflater, container, false)

        binding.newListCardview.setCardBackgroundColor(typedValue.data)
        binding.newListProductCellNameText.text = product?.name

        // creates a OnClickListener for the decrease button
        binding.btnDecrease.setOnClickListener {
            //checks if quantity is not 0
            if(quantity != 0){
                //removes one from the quantity
                quantity -= 1
                //updates the quantity label
                binding.newListProductCellQtyText.text = "$quantity"
                if (quantity == 0){
                    //removes the product from the hashmap if the quantity is 0
                    product?.let { product -> productsAdded?.remove(product) }
                    return@setOnClickListener
                }
                //updates the hashmap
                product?.let { product -> productsAdded?.put(product, quantity) }

            }
        }
    // creates a OnClickListener for the increase button
        binding.btnIncrease.setOnClickListener {
            //checks if quantity is less than 99
            if(quantity < 99){
                quantity += 1
                //updates the hashmap
                product?.let { product -> productsAdded?.put(product, quantity) }
                //updates the quantity label
                binding.newListProductCellQtyText.text = "$quantity"
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param product is a Product instance
         * @param productsAdded is a HashMap<Product, Int> reference

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