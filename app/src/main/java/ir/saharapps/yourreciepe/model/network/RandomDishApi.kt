package ir.saharapps.yourreciepe.model.network

import io.reactivex.rxjava3.core.Single
import ir.saharapps.yourreciepe.utils.Constants
import ir.saharapps.yourreciepe.utils.RandomDish
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomDishApi {
    @GET(Constants.API_ENDPOINT)
    fun getRandomDishes(
        @Query(Constants.API_KEY) apiKey: String,
        @Query(Constants.LIMIT_LICENSE) limitLicense: Boolean,
        @Query(Constants.TAGS) tags: String,
        @Query(Constants.NUMBER) number: Int
    ): Single<RandomDish.Recipes>
}