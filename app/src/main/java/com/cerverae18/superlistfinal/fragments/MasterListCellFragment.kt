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
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.databinding.FragmentMasterListCellBinding
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_NAME = "productName"
private const val ARG_CATEGORY = "productCategory"

/**
 * A simple [Fragment] subclass.
 * Use the [MasterListCellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MasterListCellFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null
    private var category: String? = null
    private var _binding: FragmentMasterListCellBinding? = null
    private val binding get() = _binding!!

    lateinit var dialogBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMasterListCellBinding.inflate(inflater, parent, false)
        binding.masterListProductName.text = "$name"
        binding.masterListProductCategory.text = "$category"


        dialogBuilder = AlertDialog.Builder(requireActivity())
        dialogBuilder.setTitle("Delete Product")
        dialogBuilder.setMessage("Are you sure you want to delete this the product: $name?")

        dialogBuilder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, whichButton ->
            //activity?.supportFragmentManager?.popBackStack(requireActivity())
            //activity?.fragmentManager?.beginTransaction()?.remove(requireActivity() as Fragment!)?.commit()
          //  activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
            parentFragmentManager.beginTransaction().remove(this).commit()
            dialog.dismiss()
        })

        dialogBuilder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, whichButton ->
            // what ever you want to do with No option.
            dialog.dismiss()
        })

        binding.btnDelete.setOnClickListener {
            //getActivity().getFragmentManager().beginTransaction().remove(this).commit();

            Log.i("DARA", "Click Delete")
            dialogBuilder.show()

        }
        /*binding.root.background = ColorDrawable(
            Color.argb(255,
            Random.nextInt(128, 255),
            Random.nextInt(128, 255),
            Random.nextInt(128, 255)))*/
        /*binding.btnBigger.setOnClickListener {
            binding.txtFirstName.textSize = size
            binding.txtLastName.textSize = size
            binding.txtSex.textSize = size
            size += 1f
        }*/
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param category Parameter 2.
         * @return A new instance of fragment MasterListCellFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String, category: String) =
            MasterListCellFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}