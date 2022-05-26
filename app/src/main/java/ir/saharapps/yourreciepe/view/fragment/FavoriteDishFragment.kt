package ir.saharapps.yourreciepe.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.databinding.FragmentFavoriteDishBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.view.activity.MainActivity
import ir.saharapps.yourreciepe.view.adapter.FavDishAdapter
import ir.saharapps.yourreciepe.viewmodel.DashboardViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory

class FavoriteDishFragment : Fragment() {

    private var mBinding: FragmentFavoriteDishBinding? = null

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository)
    }

    private var _binding: FragmentFavoriteDishBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentFavoriteDishBinding.inflate(inflater,container,false)
        return mBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding!!.rvFragmentFavDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        val adapter = FavDishAdapter(this@FavoriteDishFragment)
        mBinding!!.rvFragmentFavDishesList.adapter = adapter

        mFavDishViewModel.favoriteDishList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
                if(it.isNotEmpty()){
                    mBinding!!.rvFragmentFavDishesList.visibility = View.VISIBLE
                    mBinding!!.txtFragmentFavDishesNoDishes.visibility = View.GONE
                    adapter.dishesList(dishes)
                }else{
                    mBinding!!.rvFragmentFavDishesList.visibility = View.GONE
                    mBinding!!.txtFragmentFavDishesNoDishes.visibility = View.VISIBLE
                }
            }
        }
    }

    fun dishDetails(favDish: FavDish){
        findNavController().navigate(FavoriteDishFragmentDirections.actionNavigationFavoriteDishesToDishDetailsFragment(favDish))
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.hideBottomNavigationView()
        }
    }

    override fun onResume() {
        super.onResume()
        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }
}