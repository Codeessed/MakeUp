package com.makeupapp.makeup.common

import com.makeupapp.makeup.product.data.model.MakeUpResponseModel

sealed class MakeUpEvent(){
    data class MakeUpSuccess(val makeUp: MakeUpResponseModel): MakeUpEvent()
    object ProductTypeSuccess: MakeUpEvent()
    data class Error(val message: String): MakeUpEvent()
    object Loading: MakeUpEvent()
    object Empty: MakeUpEvent()
}