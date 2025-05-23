package com.example.vinilos.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.models.Response
import com.example.vinilos.data.repositories.AlbumesRepository
import com.example.vinilos.ui.state.AlbumDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AlbumDetailViewModel(
    private val albumesRepository: AlbumesRepository,
    private var id: Int,
): ViewModel() {

    private val _uiState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getAlbumDetail(id)
    }

    fun getAlbumDetail(id: Int) {
        _uiState.update {
            it.copy(
                albumDetailResponse = Response.Loading()
            )
        }
        viewModelScope.launch {
            try {
                val albumes = albumesRepository.getAlbumDetail(id)
                _uiState.update {
                    it.copy(
                        albumDetailResponse = Response.Success(albumes)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        albumDetailResponse = Response.Error(e.message.toString())
                    )
                }
            }
        }
    }

    companion object {
        // Factory now requires the albumId as a parameter
        fun provideFactory(
            albumesRepository: AlbumesRepository,
            albumId: Int
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlbumDetailViewModel(albumesRepository, albumId) as T
                }
            }
        }
    }
}