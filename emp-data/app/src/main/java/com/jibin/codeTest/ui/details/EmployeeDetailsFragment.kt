package com.jibin.codeTest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.jibin.codeTest.MainActivity
import com.jibin.codeTest.databinding.EmployeeDetailsFragmentBinding
import com.jibin.codeTest.utils.emptyString
import com.jibin.codeTest.utils.loadImage

class EmployeeDetailsFragment : Fragment() {

    private var _binding: EmployeeDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: EmployeeDetailsFragmentArgs by navArgs()

    private lateinit var employeeDetailsViewModel: EmployeeDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeeDetailsFragmentBinding.inflate(inflater, container, false)
        employeeDetailsViewModel = ViewModelProvider(this).get(EmployeeDetailsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            (activity as MainActivity).setSupportActionBar(toolbar)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

            profileImage.loadImage(args.employee.profile_image)

            args.employee.name?.let {
                profileName.text = it
            }

            args.employee.username?.let {
                textViewUsername.text = it
            }

            args.employee.email?.let {

                textViewEmail.text = it
            }


            args.employee.address?.let {
                textViewAddress.text = "${it.suite},\n${it.street}\n${it.city},\n${it.zipcode}"
            }

            textViewPhone.text = "${args.employee.phone}"
            textViewWebSite.text = "${args.employee.website}"

            args.employee.let {
                companyName.text =
                    " ${it.company?.name}\n ${it.company?.catchPhrase}\n ${it.company?.bs}"
            }
        }

            }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (android.R.id.home == item.itemId){
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}