package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vinilos.VinilosApplication
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.AlbumesRepository
import com.example.vinilos.ui.state.AlbumesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlbumesViewModel(
    private val albumesRepository: AlbumesRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow<AlbumesUiState>(AlbumesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAlbumes()
    }

    fun getAlbumes() {
        _uiState.update {
            it.copy(
                albumesResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val albumes = albumesRepository.getAlbumes()
                _uiState.update {
                    it.copy(
                        albumesResponse = Response.Success(albumes)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        albumesResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    fun filterAlbums(albums: List<Album>): List<Album> {
        val albumSearchTerm = _uiState.value.albumSearchTerm
        if (albumSearchTerm.isEmpty()) return albums

        return albums.filter {
            it.name.contains(albumSearchTerm, ignoreCase = true)
        }
    }

    fun setAlbumSearchTerm(searchTerm: String) {
        _uiState.update {
            it.copy(
                albumSearchTerm = searchTerm
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as VinilosApplication)
                val albumesRepository = application.container.albumesRepository
                AlbumesViewModel(albumesRepository = albumesRepository)
            }
        }
    }
}