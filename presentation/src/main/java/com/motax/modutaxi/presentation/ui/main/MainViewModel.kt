package com.motax.modutaxi.presentation.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


sealed class MainEvent{
    data object FullScreenMode: MainEvent()
    data object NotFullScreenMode: MainEvent()
    data object GoToGallery: MainEvent()
    data class CopyClipBoard(val account: String) : MainEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ImageRepository
): ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event: SharedFlow<MainEvent> = _event.asSharedFlow()

    private val _imageUrl = MutableSharedFlow<String>()
    val imageUrl: SharedFlow<String> = _imageUrl.asSharedFlow()



    fun setFullScreenMode(){
        viewModelScope.launch {
            _event.emit(MainEvent.FullScreenMode)
        }
    }

    fun setNotFullScreenMode(){
        viewModelScope.launch {
            _event.emit(MainEvent.NotFullScreenMode)
        }
    }

    fun goToGallery() {
        viewModelScope.launch {
            _event.emit(
                MainEvent.GoToGallery
            )
        }
    }

    fun setImage(
        uri: Uri,
        file: MultipartBody.Part
    ) {
        viewModelScope.launch {
            repository.imageToUrl(file).onSuccess {
                _imageUrl.emit(it.imageUrl)
            }.onFailure {

            }
        }
    }

    fun copyClipBoard(account: String){
        viewModelScope.launch {
            _event.emit(MainEvent.CopyClipBoard(account))
        }
    }

}