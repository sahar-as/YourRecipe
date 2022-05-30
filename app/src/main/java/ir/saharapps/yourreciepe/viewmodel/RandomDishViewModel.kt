package ir.saharapps.yourreciepe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.saharapps.yourreciepe.model.network.RandomDishApiService
import ir.saharapps.yourreciepe.utils.RandomDish

class RandomDishViewModel: ViewModel(){
    private val randomDishApiServiceApiService = RandomDishApiService()
    private val compositeDisposable =  CompositeDisposable()

    val loadRandomDish = MutableLiveData<Boolean>()
    val randomDishResponse = MutableLiveData<RandomDish.Recipes>()
    val randomDishLoadingError = MutableLiveData<Boolean> ()

    fun getRandomRecipeFromApi(){
        loadRandomDish.value = true
        compositeDisposable.add(
            randomDishApiServiceApiService.getRandomDish()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<RandomDish.Recipes>(){
                    override fun onSuccess(value: RandomDish.Recipes) {
                        loadRandomDish.value = false
                        randomDishResponse.value = value
                        randomDishLoadingError.value = false
                    }
                    override fun onError(e: Throwable) {
                        loadRandomDish.value = false
                        randomDishLoadingError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }
}