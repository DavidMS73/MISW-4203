package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vinilos.VinilosApplication
import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.PerformerRepository
import com.example.vinilos.ui.state.PerformersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerformersViewModel(
    private val performerRepository: PerformerRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<PerformersUiState>(PerformersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPerformers()
    }

    fun getPerformers() {
        _uiState.update {
            it.copy(
                performersResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val performers = performerRepository.getPerformers()
                _uiState.update {
                    it.copy(
                        performersResponse = Response.Success(performers)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        performersResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    fun filterPerformers(performers: List<Performer>): List<Performer> {
        val performerSearchTerm = _uiState.value.performerSearchTerm
        if (performerSearchTerm.isEmpty()) return performers

        return performers.filter {
            it.name.contains(performerSearchTerm, ignoreCase = true)
        }
    }

    fun setPerformerSearchTerm(searchTerm: String) {
        _uiState.update {
            it.copy(
                performerSearchTerm = searchTerm
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinilosApplication)
                val performersRepository = application.container.performersRepository
                PerformersViewModel(performerRepository = performersRepository)
            }
        }
    }
}