import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.nychighschool.databinding.ItemNycDataBinding
import com.app.nychighschool.model.NYCData
import java.util.*
import kotlin.reflect.KFunction2


class NYCDataAdapter(
    var nycDataList: List<NYCData>,
    private val onItemClick: OnItemClick
) : RecyclerView.Adapter<NYCDataAdapter.ViewHolder>(), Filterable {


    private var schoolFilterList: List<NYCData> = nycDataList

    inner class ViewHolder(private val binding: ItemNycDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nycData: NYCData) {
            binding.textSchoolName.text = nycData.school_name

            binding.textEmail.text = nycData.school_email
            binding.textPhoneNo.text = nycData.phone_number
            binding.textLocation.text =
                nycData.primary_address_line_1 + ", " + nycData.city + " " + nycData.state_code + " " + nycData.zip

            binding.root.setOnClickListener {
                onItemClick.onclick(nycData.dbn, nycData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNycDataBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //  holder.bind(schoolFilterList[position])
        holder.bind(schoolFilterList[position])
    }


    override fun getItemCount(): Int {
        return schoolFilterList.size
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<NYCData>()
                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(nycDataList)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.ROOT).trim()
                    for (nycData in nycDataList) {
                        if (nycData.school_name!!.lowercase(Locale.ROOT)
                                .contains(filterPattern) || nycData.overview_paragraph?.lowercase(
                                Locale.ROOT
                            )!!.contains(filterPattern)
                        ) {
                            filteredList.add(nycData)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                filterResults.count = filteredList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                schoolFilterList = results!!.values as List<NYCData>
                notifyDataSetChanged()
            }
        }
    }

    fun getFilteredCount(): Int {
        return schoolFilterList.size
    }

    interface OnItemClick {
        fun onclick(dbnNo: String, nycData: NYCData)
    }
}


