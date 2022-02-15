package com.jibin.codeTest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.jibin.codeTest.R
import com.jibin.codeTest.database.model.Employee
import com.jibin.codeTest.utils.emptyString
import com.jibin.codeTest.utils.loadImage
import kotlinx.android.synthetic.main.layout_recyclerview_item_employee.view.*
import java.util.*
import kotlin.collections.ArrayList

class EmployeeAdapter(
    var listOfEmployees: List<Employee>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<EmployeeAdapter.ActivitiesViewHolder>(),
    Filterable {

    var filteredListOfEmployees = ArrayList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_recyclerview_item_employee, parent, false)
        return ActivitiesViewHolder(itemView, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return filteredListOfEmployees.size
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        holder.bind(filteredListOfEmployees[position])
    }

    class ActivitiesViewHolder(
        itemView: View,
        private var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(employee: Employee) {

            employee.name?.let {
                itemView.profileName.text = employee.name
            }

            employee.company?.name?.let {
                itemView.companyName.text = it
            }

            itemView.profileImage?.loadImage(employee.profile_image)

            itemView.setOnClickListener {
                onItemClickListener.onItemClickListener(employee,itemView.profileImage,itemView.profileName)
            }
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                filteredListOfEmployees = when {
                    charString.isEmpty() -> {
                        listOfEmployees as ArrayList<Employee>
                    }
                    else -> {
                        val filteredList: MutableList<Employee> = ArrayList()
                        for (employee in listOfEmployees) {
                            val name = employee.name ?: emptyString()
                            val email = employee.email ?: emptyString()
                            when {
                                name.toLowerCase(Locale.getDefault()).contains(charString.toLowerCase(Locale.getDefault())) || email.toLowerCase(Locale.getDefault()).contains(charSequence) -> {
                                    filteredList.add(employee)
                                }
                            }
                        }
                        filteredList as ArrayList<Employee>
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredListOfEmployees
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                (filterResults.values as ArrayList<Employee>).also { filteredListOfEmployees = it }
                notifyDataSetChanged()
            }
        }
    }
}