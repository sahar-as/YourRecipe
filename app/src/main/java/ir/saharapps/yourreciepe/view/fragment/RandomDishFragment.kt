package ir.saharapps.yourreciepe.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.saharapps.yourreciepe.databinding.FragmentRandosmDishBinding

class RandomDishFragment : Fragment() {
    private var mBinding: FragmentRandosmDishBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRandosmDishBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}