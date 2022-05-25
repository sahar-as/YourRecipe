package ir.saharapps.yourreciepe.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.FragmentAllDishesBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.view.activity.AddUpdateDishActivity
import ir.saharapps.yourreciepe.view.activity.MainActivity
import ir.saharapps.yourreciepe.view.adapter.FavDishAdapter
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory
import ir.saharapps.yourreciepe.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {
    private lateinit var mBinding: FragmentAllDishesBinding

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((requireActivity().application as FavDishApplication).repository )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var _binding: FragmentAllDishesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAllDishesBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvFragmentAllDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        val adapter = FavDishAdapter(this@AllDishesFragment)
        mBinding.rvFragmentAllDishesList.adapter = adapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
                if(it.isNotEmpty()){
                    mBinding.rvFragmentAllDishesList.visibility = View.VISIBLE
                    mBinding.txtFragmentAllDishesNoDishes.visibility = View.GONE

                    adapter.dishesList(it)
                }else{
                    mBinding.rvFragmentAllDishesList.visibility = View.GONE
                    mBinding.txtFragmentAllDishesNoDishes.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_for_all_dishes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuItem_add_all_dishes -> {
                startActivity(Intent(requireActivity(),AddUpdateDishActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun dishDetails(favDish: FavDish){
        findNavController().navigate(AllDishesFragmentDirections.actionNavigationAllDishesToDishDetailsFragment(favDish))
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