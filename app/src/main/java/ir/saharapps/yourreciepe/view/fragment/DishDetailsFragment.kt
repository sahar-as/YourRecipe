package ir.saharapps.yourreciepe.view.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.FragmentDishDetailsBinding
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory
import java.io.IOException
import java.util.*

class DishDetailsFragment : Fragment() {

    private var mBinding: FragmentDishDetailsBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDishDetailsBinding.inflate(inflater,container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DishDetailsFragmentArgs by navArgs()

        args.let {
            try {
                Glide.with(requireActivity())
                    .load(it.dishDetails.image)
                    .centerCrop()
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?, model: Any?, target: Target<Drawable>?,
                            isFirstResource: Boolean): Boolean {
                            Log.e("TAG", "onLoadFailed: Error for Loading Image", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?, model: Any?, target: Target<Drawable>?,
                            dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            resource.let {
                                Palette.from(resource!!.toBitmap()).generate(){
                                    Palette ->
                                    val paletteColor = Palette?.lightMutedSwatch?.rgb ?:0
                                    mBinding!!.relativeLayoutDishDetail.setBackgroundColor(paletteColor)
                                }
                            }
                            return false
                        }
                    } )
                    .into(mBinding!!.imgDishImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            mBinding!!.txtFavDetailsTitle.text = it.dishDetails.title
            mBinding!!.txtFavDetailsType.text =
                it.dishDetails.type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
            mBinding!!.txtFavDetailsCategory.text = it.dishDetails.Category
            mBinding!!.txtFavDetailsIngredients.text = it.dishDetails.ingredients
            mBinding!!.txtFavDetailsCookingDirection.text = it.dishDetails.directionToCook
            mBinding!!.txtFavDetailsCookingTime.text =
                resources.getString(R.string.lbl_estimate_cooking_time, it.dishDetails.cookingTime)

            if(args.dishDetails.favoriteDish){
                mBinding!!.imgFavoriteDish.setImageDrawable(ContextCompat.getDrawable
                    (requireActivity(),R.drawable.ic_favorite_selected))
            }else{
                mBinding!!.imgFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_unselected))
            }
        }

        mBinding!!.imgFavoriteDish.setOnClickListener{
            args.dishDetails.favoriteDish = !args.dishDetails.favoriteDish
            mFavDishViewModel.updateFavDishDetail(args.dishDetails)

            if(args.dishDetails.favoriteDish){
                mBinding!!.imgFavoriteDish.setImageDrawable(ContextCompat.getDrawable
                    (requireActivity(),R.drawable.ic_favorite_selected))
            }else{
                mBinding!!.imgFavoriteDish.setImageDrawable(ContextCompat.getDrawable(
                    requireActivity(),R.drawable.ic_favorite_unselected))
            }
        }
    }
}