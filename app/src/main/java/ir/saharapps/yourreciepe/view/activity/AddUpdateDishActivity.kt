package ir.saharapps.yourreciepe.view.activity

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import ir.saharapps.yourreciepe.Application.FavDishApplication
import ir.saharapps.yourreciepe.R
import ir.saharapps.yourreciepe.databinding.ActivityAddUpdateDishBinding
import ir.saharapps.yourreciepe.databinding.DialogChoosingImageBinding
import ir.saharapps.yourreciepe.databinding.DialogCustomListAddactivityBinding
import ir.saharapps.yourreciepe.model.entities.FavDish
import ir.saharapps.yourreciepe.utils.Constants
import ir.saharapps.yourreciepe.view.adapter.CustomListAdapter
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModel
import ir.saharapps.yourreciepe.viewmodel.FavDishViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityAddUpdateDishBinding

    private var mImagePath: String =""
    private lateinit var mCustomDialog: Dialog

    private val mFavDishViewModel: FavDishViewModel by viewModels{
        FavDishViewModelFactory((application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        binding.imgAddUpdateDishAddImageButton.setOnClickListener(this)
        binding.edtAddUpdateActivityType.setOnClickListener(this)
        binding.edtAddUpdateActivityCategory.setOnClickListener(this)
        binding.edtAddUpdateActivityCookingTime.setOnClickListener(this)
        binding.btnAddUpdateDishAddDish.setOnClickListener(this)

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
                    selectImageOfDish()
                    return
                }
                R.id.edt_addUpdateActivity_type ->{
                    customItemsDialog("Select Dish Type", Constants.dishTypes(), Constants.DISH_TYPE)
                    return
                }
                R.id.edt_addUpdateActivity_category ->{
                    customItemsDialog("Select Category", Constants.dishCategories(), Constants.DISH_CATEGORY)
                    return
                }
                R.id.edt_addUpdateActivity_cookingTime ->{
                    customItemsDialog("Select Cooking Time in Minutes", Constants.dishCookTime(), Constants.DISH_COOKING_TIME)
                    return
                }
                R.id.btn_addUpdateDish_addDish ->{
                    val title = binding.edtAddUpdateActivityTitle.text.toString().trim()
                    val type = binding.edtAddUpdateActivityType.text.toString().trim()
                    val category = binding.edtAddUpdateActivityCategory.text.toString().trim()
                    val ingredient = binding.edtAddUpdateActivityIngredient.text.toString().trim()
                    val cookingTime = binding.edtAddUpdateActivityCookingTime.text.toString().trim()
                    val cookingDirection = binding.edtAddUpdateActivityCookingDirection.text.toString().trim()

                    when{
                        TextUtils.isEmpty(mImagePath) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Select Dish Image", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(title) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Enter Dish Title", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(type) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Select Dish Type", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(category) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Select Dish Category", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(ingredient) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Enter Dish Ingredient", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(cookingTime) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Select Cooking Time", Toast.LENGTH_SHORT).show()
                        }
                        TextUtils.isEmpty(cookingDirection) ->{
                            Toast.makeText(this@AddUpdateDishActivity,"Enter Cooking Direction", Toast.LENGTH_SHORT).show()
                        }

                        else ->{
                            val favDishDetails: FavDish = FavDish(
                                mImagePath, Constants.DISH_IMAGE_SOURCE_LOCAL,
                                title, type, category, ingredient, cookingTime,
                                cookingDirection, false
                            )
                            mFavDishViewModel.insert(favDishDetails)
                            Toast.makeText(this@AddUpdateDishActivity,
                                "You Successfully added your favorite dish details.", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }

                    }

                }
            }
        }
    }


    private fun selectImageOfDish(){
        val dialog = Dialog(this)
        val dialogBinding: DialogChoosingImageBinding = DialogChoosingImageBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.txtDialogChooseImageGallery.setOnClickListener {
            Dexter.withContext(this@AddUpdateDishActivity)
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(object :PermissionListener {
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        resultLauncherGallery.launch(intent)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@AddUpdateDishActivity,
                            "onPermissionDenied from Gallery",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()
            dialog.dismiss()
        }
        dialogBinding.txtDialogChooseImageCamera.setOnClickListener {
            Dexter.withContext(this@AddUpdateDishActivity)
                .withPermissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {
                            if (report.areAllPermissionsGranted()) {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                resultLauncherCamera.launch(intent)
                            }
                        }

                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread()
                .check()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    var resultLauncherCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.extras?.let {
                val data: Bitmap = result.data!!.extras!!.get("data") as Bitmap
//                binding.imgAddUpdateDishFoodImage.setImageBitmap(data)
                Glide.with(this)
                    .load(data)
                    .centerCrop()
                    .into(binding.imgAddUpdateDishFoodImage)

                mImagePath = saveImageToInternalStorage(data)
                Log.d("TAG", "hhhhhhhhhhhhhhh path $mImagePath")

                binding.imgAddUpdateDishAddImageButton.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.ic_edit))
            }
        }
    }

    var resultLauncherGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val data = result.data!!.data
                Glide.with(this)
                    .load(data)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .addListener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                        ): Boolean {
                            Log.e("TAG", "onLoadFailed: Errroooorr occurred", e)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                        ): Boolean {
                            resource?.let {
                                val bitmap: Bitmap = resource.toBitmap()
                                mImagePath = saveImageToInternalStorage(bitmap)
                                Log.d("TAG", "onResourceReady: hhhhhhhhhhhhhhh path $mImagePath")
                            }
                            return false
                        }
                    })
                    .into(binding.imgAddUpdateDishFoodImage)
                binding.imgAddUpdateDishAddImageButton.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.ic_edit))
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    companion object {
        const val IMAGE_DIRECTORY = "FavDishImages"
    }

    private fun customItemsDialog(title: String, list: ArrayList<String>, selection: String){
        mCustomDialog = Dialog(this)
        val customItemDialogBinding: DialogCustomListAddactivityBinding = DialogCustomListAddactivityBinding.inflate(layoutInflater)
        mCustomDialog.setContentView(customItemDialogBinding.root)

        customItemDialogBinding.txtTitle.text = title

        val adapter = CustomListAdapter(this, list, selection)
        customItemDialogBinding.rvList.layoutManager = LinearLayoutManager(this)
        customItemDialogBinding.rvList.adapter = adapter

        mCustomDialog.show()
    }

    fun selectedListItem(item: String, selection: String){
        when(selection){
            Constants.DISH_TYPE ->{
                mCustomDialog.dismiss()
                binding.edtAddUpdateActivityType.setText(item)
            }

            Constants.DISH_CATEGORY ->{
                mCustomDialog.dismiss()
                binding.edtAddUpdateActivityCategory.setText(item)
            }
            Constants.DISH_COOKING_TIME ->{
                mCustomDialog.dismiss()
                binding.edtAddUpdateActivityCookingTime.setText(item)
            }
        }
    }
}


//icon colors #306880