package com.makeupapp.makeup.product.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.Resource
import com.makeupapp.makeup.product.data.model.MakeUpResponseModelItem
import com.makeupapp.makeup.product.domain.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MakeUpViewModel @Inject constructor(private val repository: RepositoryInterface):ViewModel() {

    private val _makeUp = MutableStateFlow<MakeUpEvent>(MakeUpEvent.Empty)
    val makeUp = _makeUp.asStateFlow()

    private val _productType = MutableStateFlow<MakeUpEvent>(MakeUpEvent.Empty)
    val productType = _productType.asStateFlow()

    val productIds = ArrayList<Int>()
    val productList = ArrayList<MakeUpResponseModelItem>()
    val brandList = ArrayList<MakeUpResponseModelItem>()
    val productTypes = ArrayList<MakeUpResponseModelItem>()
    private val _brand = MutableStateFlow<List<MakeUpResponseModelItem>>(brandList)
    val brand = _brand.asStateFlow()

    fun getMakeUp(){
        viewModelScope.launch {
            _makeUp.value = MakeUpEvent.Loading
            try {
                when(val result = repository.getAllMakeUp()){
                    is Resource.Success ->{
                        val list = result.data!!
//                        get products and their ids in separate arraylist
                        brandList.clear()
                        brandList.add(list[0])
                        productList.add(list[0])
                        productIds.add(list[0].id)
                        for (i in 1..list.lastIndex){
                            productList.add(list[i])
                            productIds.add(list[i].id)
                            if (list[i].brand != list[i-1].brand){
                                brandList.add(list[i])
                            }
                        }
                        _brand.value = brandList
                        _makeUp.value = MakeUpEvent.MakeUpSuccess(result.data!!)
                    }
                    is Resource.Error -> _makeUp.value = MakeUpEvent.Error(result.message!!)
                }
            }catch (e: Exception){
                when(e){
                    is IOException -> _makeUp.value = MakeUpEvent.Error("Weak connection")
                    is CancellationException -> _makeUp.value = MakeUpEvent.Error("Timed out")
                    else -> _makeUp.value = MakeUpEvent.Error("An error occurred")
                }
            }
        }
    }

//    get all brand products types that are close
    fun updateProductTypes(id: Int, brand: String){
        viewModelScope.launch {
            _productType.value = (MakeUpEvent.Loading)
            productTypes.clear()
            for (i in 0..productList.lastIndex){
                if (productList[i].id == id){
                    productTypes.add(productList[i])
                }else if(productList[i].id < id){
                    if (productList[i].brand == brand){
                        productTypes.add(productList[i])
                    }else{
                        break
                    }
                }
            }
            _productType.value = (MakeUpEvent.ProductTypeSuccess)
        }
    }

}