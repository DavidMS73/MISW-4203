package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vinilos.VinilosApplication
import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.CollectorRepository
import com.example.vinilos.ui.state.CollectorsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.filter

class CollectorsViewModel(
    private val collectorsRepository: CollectorRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CollectorsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCollectors()
    }

    fun getCollectors() {
        _uiState.update {
            it.copy(
                collectorsResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val collectors = collectorsRepository.getCollectors()
                _uiState.update {
                    it.copy(
                        collectorsResponse = Response.Success(collectors)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        collectorsResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    fun filterCollectors(collectors: List<Collector>): List<Collector> {
        val collectorSearchTerm = _uiState.value.collectorSearchTerm
        if (collectorSearchTerm.isEmpty()) return collectors

        return collectors.filter {
            it.name.contains(collectorSearchTerm, ignoreCase = true)
        }
    }

    fun setCollectorSearchTerm(searchTerm: String) {
        _uiState.update {
            it.copy(
                collectorSearchTerm = searchTerm
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinilosApplication)
                val collectorsRepository = application.container.collectorsRepository
                CollectorsViewModel(collectorsRepository = collectorsRepository)
            }
        }
    }
}