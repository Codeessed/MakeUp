package com.makeupapp.makeup.product.network

import com.makeupapp.makeup.product.data.model.MakeUpResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface NetworkInterface {

    @GET("products.json")
    suspend fun getAllProduct(): Response<MakeUpResponseModel>
}