package com.makeupapp.makeup.product.domain.repository

import com.makeupapp.makeup.common.Resource
import com.makeupapp.makeup.product.data.model.MakeUpResponseModel
import com.makeupapp.makeup.product.network.NetworkInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


class RepositoryImplementation @Inject constructor(private val networkInterface: NetworkInterface): RepositoryInterface {

//    make network call with retrofit
    override suspend fun getAllMakeUp(): Resource<MakeUpResponseModel> {
    val response = networkInterface.getAllProduct()
    val result = response.body()
    return try {
        if (response.isSuccessful && result != null) {
            Resource.Success(result)
        } else {
            Resource.Error("Error")
        }
    } catch (e: Exception) {
        Resource.Error("An error occurred")
    }
}
}