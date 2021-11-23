package com.cerverae18.superlistfinal.fragments

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.GeneralApplication
import com.cerverae18.superlistfinal.MasterListActivity
import com.cerverae18.superlistfinal.R
import com.cerverae18.superlistfinal.databinding.FragmentMasterListCellBinding
import com.cerverae18.superlistfinal.logic.ProductViewModel
import com.cerverae18.superlistfinal.logic.ProductViewModelFactory
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
import kotlin.random.Random


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PRODUCT = "ARG_PRODUCT"


/**
 * A simple [Fragment] subclass.
 * Use the [MasterListCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MasterListCellFragment : Fragment() {

    private var product: ProductWithCategory? = null

    private var _binding: FragmentMasterListCellBinding? = null
    private val binding get() = _binding!!

    lateinit var dialogBuilder: AlertDialog.Builder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT) as ProductWithCategory

        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMasterListCellBinding.inflate(inflater, parent, false)
        binding.masterListProductName.text = "${product?.name}"
        binding.masterListProductCategory.text = "${product?.category}"


        dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setTitle(getString(R.string.delete_product))
        dialogBuilder.setMessage(getString(R.string.delete_product_message) + " ${product?.name}?")

        dialogBuilder.setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, _ ->


            val activity =  activity as MasterListActivity
            product?.let { activity.productViewModel.delete(product!!.id) }
            //parentFragmentManager.beginTransaction().remove(this).commit()


            dialog.dismiss()
        })

        dialogBuilder.setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ ->

            dialog.dismiss()
        })

        binding.btnDelete.setOnClickListener {
            dialogBuilder.show()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param product Parameter 1.

         * @return A new instance of fragment MasterListCellFragment.
         */

        @JvmStatic
        fun newInstance(product: ProductWithCategory) =
            MasterListCellFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PRODUCT, product)

                }
            }
    }
}