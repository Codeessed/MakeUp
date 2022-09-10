package com.makeupapp.makeup.product.presentation.viewmodel

import androidx.lifecycle.*
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.Resource
import com.makeupapp.makeup.product.domain.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MakeUpViewModel @Inject constructor(private val repository: RepositoryInterface):ViewModel() {

    private val _makeUp = MutableStateFlow<MakeUpEvent>(MakeUpEvent.Empty)
    val makeUp = _makeUp.asStateFlow()

    fun getMakeUp(){
        viewModelScope.launch {
            _makeUp.value = MakeUpEvent.Loading
            try {
                when(val result = repository.getAllMakeUp()){
                    is Resource.Success -> _makeUp.value = MakeUpEvent.MakeUpSuccess(result.data!!)
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

}