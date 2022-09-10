package com.makeupapp.makeup.product.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.makeupapp.makeup.common.MakeUpEvent
import com.makeupapp.makeup.common.observer
import com.makeupapp.makeup.databinding.BrandFragmentBinding
import com.makeupapp.makeup.product.presentation.viewmodel.MakeUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BrandFragment: Fragment() {

    private var _binding: BrandFragmentBinding? = null
    private val binding get() = _binding!!

    private val makeUpViewModel: MakeUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BrandFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeUpViewModel.getMakeUp()
        observer(makeUpViewModel.makeUp){ makeUp ->
            when(makeUp){
                is MakeUpEvent.MakeUpSuccess -> {
                    Log.d("TAG", makeUp.makeUp.toString())

                }
                is MakeUpEvent.Error -> {
                    Log.d("TAG", makeUp.message)

                }
                is MakeUpEvent.Loading -> {
                    Log.d("TAG", "loading")
                }
                else -> Unit
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}