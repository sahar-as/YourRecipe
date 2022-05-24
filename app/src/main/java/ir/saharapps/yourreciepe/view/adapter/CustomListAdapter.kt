package ir.saharapps.yourreciepe.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.saharapps.yourreciepe.databinding.ItemCustomListAddactivityBinding
import ir.saharapps.yourreciepe.view.activity.AddUpdateDishActivity
import java.util.zip.Inflater

class CustomListAdapter(private val activity: Activity,
                        private val list: ArrayList<String>, private val selection: String)
    : RecyclerView.Adapter<CustomListAdapter.ItemViewHolder>(){

        class ItemViewHolder(view: ItemCustomListAddactivityBinding) : RecyclerView.ViewHolder(view.root){
            val textView = view.txtTextView
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemCustomListAddactivityBinding = ItemCustomListAddactivityBinding
            .inflate(LayoutInflater.from(activity), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[position]
        holder.textView.text = item

        holder.itemView.setOnClickListener {
            if(activity is AddUpdateDishActivity){
                activity.selectedListItem(item, selection)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}