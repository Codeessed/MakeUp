package com.makeupapp.makeup.product.domain.repository

import com.makeupapp.makeup.common.Resource
import com.makeupapp.makeup.product.data.model.MakeUpResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RepositoryInterface {

    suspend fun getAllMakeUp(): Resource<MakeUpResponseModel>
}