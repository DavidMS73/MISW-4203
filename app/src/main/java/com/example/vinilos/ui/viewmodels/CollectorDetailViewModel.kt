package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.CollectorRepository
import com.example.vinilos.ui.state.CollectorDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class CollectorDetailViewModel(
    private val collectorRepository: CollectorRepository,
    private var id: Int,
): ViewModel() {

    private val _uiState = MutableStateFlow<CollectorDetailUiState>(CollectorDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCollectorDetail(id)
    }

    fun getCollectorDetail(id: Int) {
        _uiState.update {
            it.copy(
                collectorDetailResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val albumes = collectorRepository.getCollectorDetail(id)
                _uiState.update {
                    it.copy(
                        collectorDetailResponse = Response.Success(albumes)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        collectorDetailResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    companion object {
        // Factory now requires the albumId as a parameter
        fun provideFactory(
            collectorRepository: CollectorRepository,
            collectorId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CollectorDetailViewModel(collectorRepository, collectorId) as T
                }
            }
        }
    }
}