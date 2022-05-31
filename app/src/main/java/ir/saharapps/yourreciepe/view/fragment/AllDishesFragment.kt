package ir.saharapps.yourreciepe.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.DialogCustomListAddactivityBinding
import ir.saharapps.yourreciepe.databinding.FragmentAllDishesBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.utils.Constants
import ir.saharapps.yourreciepe.view.activity.AddUpdateDishActivity
import ir.saharapps.yourreciepe.view.activity.MainActivity
import ir.saharapps.yourreciepe.view.adapter.CustomListAdapter
import ir.saharapps.yourreciepe.view.adapter.FavDishAdapter
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory
import ir.saharapps.yourreciepe.viewmodel.HomeViewModel

class AllDishesFragment : Fragment() {
    private var mBinding: FragmentAllDishesBinding? = null
    private lateinit var mFavDishAdapter: FavDishAdapter
    private lateinit var mCustomDialog: Dialog

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
        return mBinding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.rvFragmentAllDishesList.layoutManager = GridLayoutManager(requireActivity(),2)
        mFavDishAdapter = FavDishAdapter(this@AllDishesFragment)
        mBinding!!.rvFragmentAllDishesList.adapter = mFavDishAdapter

        mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
            dishes ->
            dishes.let {
                if(it.isNotEmpty()){
                    mBinding!!.rvFragmentAllDishesList.visibility = View.VISIBLE
                    mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.GONE

                    mFavDishAdapter.dishesList(it)
                }else{
                    mBinding!!.rvFragmentAllDishesList.visibility = View.GONE
                    mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.VISIBLE
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
            R.id.menuItem_filter_allDishes -> {
                filterDishesListDialog()
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

    fun deleteDish(favDish: FavDish){
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.delete_dish))
            .setMessage("Are you sure you want delete ${favDish.title}?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(getString(R.string.yes)) { dialogInterface, _ ->
                mFavDishViewModel.delete(favDish)
                dialogInterface.dismiss() // Dialog will be dismissed
            }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()

        if(requireActivity() is MainActivity){
            (activity as MainActivity?)?.showBottomNavigationView()
        }
    }

    private fun filterDishesListDialog(){
        mCustomDialog = Dialog(requireActivity())
        val binding: DialogCustomListAddactivityBinding = DialogCustomListAddactivityBinding.inflate(layoutInflater)
        mCustomDialog.setContentView(binding.root)

        binding.txtTitle.text = getString(R.string.select_item_to_filter)
        val dishType = Constants.dishTypes()
        dishType.add(0, Constants.ALL_TIMES)

        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = CustomListAdapter(requireActivity(), this@AllDishesFragment, dishType, Constants.FILTER_SELECTION)
        binding.rvList.adapter = adapter
        mCustomDialog.show()
    }

    fun filterSelection(filterItemSelection: String){
        mCustomDialog.dismiss()
        (activity as AppCompatActivity).supportActionBar?.title = filterItemSelection

        if(filterItemSelection == Constants.ALL_TIMES){
            mFavDishViewModel.allDishesList.observe(viewLifecycleOwner){
                    dishes ->
                dishes.let {
                    if(it.isNotEmpty()){
                        mBinding!!.rvFragmentAllDishesList.visibility = View.VISIBLE
                        mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.GONE

                        mFavDishAdapter.dishesList(it)
                    }else{
                        mBinding!!.rvFragmentAllDishesList.visibility = View.GONE
                        mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.VISIBLE
                    }
                }
            }
        }else{
            mFavDishViewModel.filterData(filterItemSelection).observe(viewLifecycleOwner){
                    dishes ->
                dishes.let {
                    if(it.isNotEmpty()){
                        mBinding!!.rvFragmentAllDishesList.visibility = View.VISIBLE
                        mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.GONE

                        mFavDishAdapter.dishesList(it)
                    }else{
                        mBinding!!.rvFragmentAllDishesList.visibility = View.GONE
                        mBinding!!.txtFragmentAllDishesNoDishes.visibility = View.VISIBLE
                        mBinding!!.txtFragmentAllDishesNoDishes.text = getString(R.string.no_dish_exist)
                    }
                }
            }
        }

    }
}