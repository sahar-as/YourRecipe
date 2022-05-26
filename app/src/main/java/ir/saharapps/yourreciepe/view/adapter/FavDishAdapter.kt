package ir.saharapps.yourreciepe.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.saharapps.yourreciepe.databinding.ItemDishesLayoutBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.view.fragment.AllDishesFragment
import ir.saharapps.yourreciepe.view.fragment.FavoriteDishFragment

class FavDishAdapter(private val fragment: Fragment): RecyclerView.Adapter<FavDishAdapter.ItemViewHolder>(){

    private var dishes: List<FavDish> = listOf()

    class ItemViewHolder(view: ItemDishesLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val imgFavDishImage = view.imgItemDishLayoutDishImage
        val txtFavDishTitle = view.txtItemDishLayoutDishTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemDishesLayoutBinding = ItemDishesLayoutBinding
            .inflate(LayoutInflater.from(fragment.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dish = dishes[position]
        Glide.with(fragment)
            .load(dish.image)
            .into(holder.imgFavDishImage)
        holder.txtFavDishTitle.text = dish.title

        holder.itemView.setOnClickListener {
            if(fragment is AllDishesFragment){
                fragment.dishDetails(dish)
            }
            if(fragment is FavoriteDishFragment){
                fragment.dishDetails(dish)
            }
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    fun dishesList(list: List<FavDish>){
        dishes = list
        notifyDataSetChanged()
    }
}