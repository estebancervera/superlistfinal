package com.cerverae18.superlistfinal.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import com.cerverae18.superlistfinal.ListActivity
import com.cerverae18.superlistfinal.R
import com.cerverae18.superlistfinal.databinding.FragmentListProductCellBinding
import com.cerverae18.superlistfinal.logic.entities.relations.ProductFromList

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCT_FROM_LIST = "ARG_PRODUCT_FROM_LIST"

/**
 * A simple [Fragment] subclass.
 * Use the [NewListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 * @property product is the product contained in a supermarket visit list
 * @property _binding is a FragmentListProductCellBinding? that helps with the accessing of UI elements inside the onCreateView method
 * @property binding is a FragmentListProductCellBinding getter referencing to _binding.
 */
class ListProductCellFragment : Fragment() {

    // Product from list
    private var product: ProductFromList? = null

    // Fragment binding
    private var _binding: FragmentListProductCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT_FROM_LIST) as ProductFromList

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

        // get theme colors
        val secondaryVariantType = TypedValue()
        val accentype = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorSecondaryVariant,secondaryVariantType,true)
        context?.theme?.resolveAttribute(R.attr.colorAccent,accentype,true)

        // Inflate the layout for this fragment
        _binding = FragmentListProductCellBinding.inflate(inflater, container, false)

        // Store views
        val productNameText = binding.listProductCellName
        val productCategoryText = binding.listProductCellCategory
        val productQuantity = binding.listProductQuantity
        val productCheckBox = binding.productCheckBox

        // Check if product is checked
        productCheckBox.isChecked = product?.checked ?: false

        // Set theme colors
        val color = if (!productCheckBox.isChecked) secondaryVariantType.data else accentype.data
        val colorContrast = if (productCheckBox.isChecked) secondaryVariantType.data else accentype.data
        productCheckBox.setButtonTintList(ColorStateList.valueOf(colorContrast))
        binding.listActivityListCellCardview.setCardBackgroundColor(color)

        // Product checkbox listener
        binding.productCheckBox.setOnCheckedChangeListener { button, state ->
            val activity = activity as ListActivity
            product?.let { activity.productListViewModel.update(it.id, state) }
            activity.removeAllFragments()
        }

        // Set text to the views
        productQuantity.text = "${product?.quantity}"
        productNameText.text = product?.name
        productCategoryText.text = product?.category

        // Return the view
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
         * @param product is an instance of the [ProductFromList] class
         * @return A new instance of fragment [ListProductCellFragment].
         */

        @JvmStatic
        fun newInstance(product: ProductFromList) =
            ListProductCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCT_FROM_LIST, product)
                }
            }
    }
}