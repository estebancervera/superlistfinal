package com.cerverae18.superlistfinal.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import com.cerverae18.superlistfinal.ListActivity
import com.cerverae18.superlistfinal.R
import com.cerverae18.superlistfinal.databinding.FragmentListProductCellBinding
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ListWithProducts
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCTS_LIST = "ARG_PRODUCTS_LIST"

/**
 * A simple [Fragment] subclass.
 * Use the [ListProductCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListProductCellFragment : Fragment() {

    private var product: ListWithProducts? = null

    private var _binding: FragmentListProductCellBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCTS_LIST) as ListWithProducts

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val secondaryVariantType = TypedValue()
        val accentype = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorSecondaryVariant,secondaryVariantType,true)
        context?.theme?.resolveAttribute(R.attr.colorAccent,accentype,true)
        // Inflate the layout for this fragment
        _binding = FragmentListProductCellBinding.inflate(inflater, container, false)



        val productNameText = binding.listProductCellName
        val productCategoryText = binding.listProductCellCategory
        val productQuantity = binding.listProductQuantity
        val productCheckBox = binding.productCheckBox



        productCheckBox.isChecked = product?.checked ?: false

        val color = if (!productCheckBox.isChecked) secondaryVariantType.data else accentype.data

        val colorContrast = if (productCheckBox.isChecked) secondaryVariantType.data else accentype.data

        productCheckBox.setButtonTintList(ColorStateList.valueOf(colorContrast))

        binding.listActivityListCellCardview.setCardBackgroundColor(color)


        binding.productCheckBox.setOnCheckedChangeListener { button, state ->
            val activity = activity as ListActivity
            product?.let { activity.productListViewModel.update(it.id, state) }
            //activity.updateOnChecked()
            activity.removeAllFragments()
        }

        productQuantity.text = "${product?.quantity}"
        productNameText.text = product?.name
        productCategoryText.text = product?.category

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
         * @param product is an instance of the ListWithProducts class
         * @return A new instance of fragment MainActivityListCellFragment.
         */

        @JvmStatic
        fun newInstance(product: ListWithProducts) =
            ListProductCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCTS_LIST, product)
                }
            }
    }
}