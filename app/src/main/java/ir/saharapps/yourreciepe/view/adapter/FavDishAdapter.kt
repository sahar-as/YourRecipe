package ir.saharapps.yourreciepe.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.ItemDishesLayoutBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.view.activity.AddUpdateDishActivity
import ir.saharapps.yourreciepe.view.fragment.AllDishesFragment
import ir.saharapps.yourreciepe.view.fragment.FavoriteDishFragment
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel

class FavDishAdapter(private val fragment: Fragment): RecyclerView.Adapter<FavDishAdapter.ItemViewHolder>(){

    private var dishes: List<FavDish> = listOf()

    class ItemViewHolder(view: ItemDishesLayoutBinding) : RecyclerView.ViewHolder(view.root){
        val imgFavDishImage = view.imgItemDishLayoutDishImage
        val txtFavDishTitle = view.txtItemDishLayoutDishTitle
        val imgBtnMenuMore = view.imgBtnItemDishLayoutMenuMore
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

        holder.imgBtnMenuMore.setOnClickListener {
            val popup = PopupMenu(fragment.context, holder.imgBtnMenuMore)
            popup.menuInflater.inflate(R.menu.menu_more_dish_item, popup.menu)

            popup.setOnMenuItemClickListener {
                if(it.itemId == R.id.menuMore_edit){
                    var intent = Intent(fragment.requireActivity(), AddUpdateDishActivity::class.java)
                    intent.putExtra("dishInfo", dish)
                    fragment.requireActivity().startActivity(intent)

                }else if( it.itemId == R.id.menuMore_delete){
                    if(fragment is AllDishesFragment){
                        fragment.deleteDish(dish)
                    }
                }
                true
            }

            popup.show()
        }

        if(fragment is AllDishesFragment){
            holder.imgBtnMenuMore.visibility = View.VISIBLE
        }else if(fragment is FavoriteDishFragment){
            holder.imgBtnMenuMore.visibility = View.GONE
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