package ir.saharapps.yourreciepe.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.ActivityAddUpdateDishBinding

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityAddUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()
        binding.imgAddUpdateDishAddImageButton.setOnClickListener(this)
    }

    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarAddUpdateDish)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarAddUpdateDish.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.img_addUpdateDish_addImageButton ->{
                    Toast.makeText(this, "you pressed it", Toast.LENGTH_SHORT).show()
                    return
                }
            }
        }
    }
}