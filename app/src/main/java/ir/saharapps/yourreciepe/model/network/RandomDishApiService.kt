package ir.saharapps.yourreciepe.model.network

import io.reactivex.rxjava3.core.Single
import ir.saharapps.yourreciepe.utils.Constants
import ir.saharapps.yourreciepe.utils.RandomDish
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RandomDishApiService {

    private val api = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL) // Set the API base URL.
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(RandomDishApi::class.java)

    fun getRandomDish(): Single<RandomDish.Recipes> {
        return api.getRandomDishes(
            Constants.API_KEY_VALUE,
            Constants.LIMIT_LICENSE_VALUE,
            Constants.TAGS_VALUE,
            Constants.NUMBER_VALUE
        )
    }
}