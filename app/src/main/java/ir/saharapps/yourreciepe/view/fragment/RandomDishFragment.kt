package ir.saharapps.yourreciepe.view.fragment

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.FragmentRandosmDishBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.utils.Constants
import ir.saharapps.yourreciepe.utils.RandomDish
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory
import ir.saharapps.yourreciepe.viewmodel.RandomDishViewModel

class RandomDishFragment : Fragment() {
    private var mBinding: FragmentRandosmDishBinding? = null

    private lateinit var mRandomDishViewModel: RandomDishViewModel
    private var mProgressDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRandosmDishBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRandomDishViewModel = ViewModelProvider(this).get(RandomDishViewModel::class.java)
        mRandomDishViewModel.getRandomRecipeFromApi()
        randomDishViewModelObserver()

        mBinding!!.srlRandomDishSwipeRefresh.setOnRefreshListener {
            mRandomDishViewModel.getRandomRecipeFromApi()
        }

    }

    private fun randomDishViewModelObserver(){
        mRandomDishViewModel.randomDishResponse.observe(viewLifecycleOwner) { randomDishResponse ->
            randomDishResponse?.let {
                if(mBinding!!.srlRandomDishSwipeRefresh.isRefreshing){
                    mBinding!!.srlRandomDishSwipeRefresh.isRefreshing = false
                }
                setRandomDishResponseInUi(randomDishResponse.recipes[0])
            }
        }

        mRandomDishViewModel.randomDishLoadingError.observe(viewLifecycleOwner) {randomDishLoadingError ->
            randomDishLoadingError?.let {
                Log.d("TAG", "randomDishViewModelObserver: 222222222222222 $randomDishLoadingError")
            }
        }

        mRandomDishViewModel.loadRandomDish.observe(viewLifecycleOwner) { loadRandomDish ->
            loadRandomDish?.let {
                Log.d("TAG", "randomDishViewModelObserver: 3333333333333333 $loadRandomDish")
                if(loadRandomDish && !mBinding!!.srlRandomDishSwipeRefresh.isRefreshing){
                    showProgressDialog()
                }else{
                    hideProgressBar()
                }
            }
        }
    }

    private fun setRandomDishResponseInUi(recipe: RandomDish.Recipe){
        Glide.with(requireActivity())
            .load(recipe.image)
            .centerCrop()
            .into(mBinding!!.imgRandomFragmentDishImage)
        mBinding!!.txtRandomFragmentTitle.text = recipe.title
        var dishType: String = "other"
        if(recipe.dishTypes.isNotEmpty()){
            dishType = recipe.dishTypes[0]
            mBinding!!.txtRandomFragmentType.text = dishType
        }
        mBinding!!.txtRandomFragmentCategory.text = "Other"

        var ingridients =""
        for (value in recipe.extendedIngredients){
            if(ingridients.isEmpty()){
                ingridients = value.original
            }else{
                ingridients = ingridients + ", \n" + value.original
            }
        }
        mBinding!!.txtRandomFragmentIngredients.text = ingridients

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mBinding!!.txtRandomFragmentCookingDirection.text = Html.fromHtml(
                recipe.instructions,
                Html.FROM_HTML_MODE_COMPACT
            )
        }else{
            mBinding!!.txtRandomFragmentCookingDirection.text = Html.fromHtml(recipe.instructions)
        }
        mBinding!!.txtRandomFragmentCookingTime.text = resources.getString(R.string.lbl_estimate_cooking_time,
            recipe.readyInMinutes.toString())

        mBinding!!.imgRandomFragmentFavoriteDish.setImageDrawable(
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_favorite_unselected)
        )
        var addedToFavorites = false

        mBinding!!.imgRandomFragmentFavoriteDish.setOnClickListener{
            if(addedToFavorites){
                Toast.makeText(requireActivity(), getString(R.string.added_to_favorite),
                    Toast.LENGTH_LONG).show()
            }else{
                val randomDishDetails = FavDish(
                    recipe.image, Constants.DISH_IMAGE_SOURCE_ONLINE, recipe.title,
                    dishType, "Other", ingridients, recipe.readyInMinutes.toString(),
                    recipe.instructions, true
                )

                val mFavDishViewModel: FavDishViewModel by viewModels {
                    FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
                }
                mFavDishViewModel.insert(randomDishDetails)
                addedToFavorites = true
                mBinding!!.imgRandomFragmentFavoriteDish.setImageDrawable(
                    ContextCompat.getDrawable
                        (requireActivity(),R.drawable.ic_favorite_selected))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    private fun showProgressDialog(){
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog?.let{
            it.setContentView(R.layout.dialog_custom_progressbar)
            it.show()
        }
    }

    private fun hideProgressBar(){
        mProgressDialog?.let {
            it.dismiss()
        }
    }
}