package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.PerformerRepository
import com.example.vinilos.ui.state.PerformerDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PerformerDetailViewModel(
    private val performerRepository: PerformerRepository,
    private var id: Int,
): ViewModel() {

    private val _uiState = MutableStateFlow<PerformerDetailUiState>(PerformerDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPerformerDetail(id)
    }

    fun getPerformerDetail(id: Int) {
        _uiState.update {
            it.copy(
                performerDetailResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val albumes = performerRepository.getPerformersDetail(id)
                _uiState.update {
                    it.copy(
                        performerDetailResponse = Response.Success(albumes)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        performerDetailResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            performerRepository: PerformerRepository,
            performerId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PerformerDetailViewModel(performerRepository, performerId) as T
                }
            }
        }
    }
}