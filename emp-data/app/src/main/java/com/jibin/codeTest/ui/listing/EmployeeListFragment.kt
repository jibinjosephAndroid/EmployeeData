package com.jibin.codeTest.ui.listing

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jibin.codeTest.MainActivity
import com.jibin.codeTest.R
import com.jibin.codeTest.adapter.EmployeeAdapter
import com.jibin.codeTest.adapter.OnItemClickListener
import com.jibin.codeTest.database.model.Employee
import com.jibin.codeTest.databinding.EmployeeListFragmentBinding
import com.jibin.codeTest.utils.emptyString



class EmployeeListFragment : Fragment(), OnItemClickListener {

    private var _binding: EmployeeListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var employeeListViewModel: EmployeeListViewModel
    private var searchView: SearchView? = null
    private var employeesAdapter: EmployeeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EmployeeListFragmentBinding.inflate(inflater, container, false)
            employeeListViewModel = ViewModelProvider(this).get(EmployeeListViewModel::class.java)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar.apply {
            (activity as MainActivity).setSupportActionBar(this)
        }

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        employeeListViewModel.employeeList.observe(viewLifecycleOwner, Observer { listOfEmployees ->
            if (listOfEmployees.isNotEmpty()) {
                employeesAdapter = EmployeeAdapter(listOfEmployees, this)
                binding.recyclerViewEmployees.apply {
                    layoutManager = linearLayoutManager
                    adapter = employeesAdapter
                    employeesAdapter?.filter?.filter(emptyString())
                }
            }
        })

        employeeListViewModel.loading.observe(viewLifecycleOwner, Observer {
            binding?.progressBar.visibility = when {
                it -> {
                    View.VISIBLE
                }
                else -> {
                    View.GONE
                }
            }
        })
    }

    override fun onItemClickListener(
        employee: Employee,
        imageViewProfile: ImageView,
        textViewName: TextView
    ) {
        val action =
            EmployeeListFragmentDirections.actionNavigationEmployeeListToNavigationEmployeeDetails(
                employee)
        NavHostFragment.findNavController(this@EmployeeListFragment).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        searchView = menu.findItem(R.id.action_search).actionView as SearchView?
        searchView?.setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
        searchView?.maxWidth = Int.MAX_VALUE

        searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                employeesAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                employeesAdapter?.filter?.filter(query)
                return false
            }
        })
    }

}