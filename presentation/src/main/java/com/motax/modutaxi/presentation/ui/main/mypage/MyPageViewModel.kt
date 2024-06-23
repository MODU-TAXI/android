package com.motax.modutaxi.presentation.ui.main.mypage

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

data class MyPageUiState(
    val isReported: Boolean = true,
    val imageUrl: String = "",
    val nickname: String = "",
    val name: String = "",
    val certified: Boolean = false,
)

sealed class MyPageEvent {

    data class ShowToastMessage(val msg: String) : MyPageEvent()
}

@HiltViewModel
class MyPageViewModel @Inject constructor(
    application: Application,
    private val dataStoreManager: DataStoreManager,
    private val mainRepository: MainRepository,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<MyPageEvent>()
    val event: SharedFlow<MyPageEvent> = _event.asSharedFlow()

    fun loadMemberData() {
        viewModelScope.launch {
            val memberId = dataStoreManager.getMemberId()?.toLongOrNull()
            if (memberId != null) {
                mainRepository.getMemberDetail(memberId).onSuccess {
                    _uiState.update { state ->
                        state.copy(
                            name = dataStoreManager.getMemberName().toString(),
                            nickname = it.nickname,
                            imageUrl = it.imageUrl,
                            certified = it.certified
                        )
                    }
                }.onFailure {}
            }
        }
    }

    //기본 프로필 이미지 설정
    fun setDefaultProfileImage() {
        viewModelScope.launch {

            val memberName = dataStoreManager.getMemberName()
            val gender = dataStoreManager.getGender()
            val phoneNumber = dataStoreManager.getPhoneNumber()

            val profileData = mapOf(
                "name" to (memberName ?: ""),
                "gender" to (gender ?: ""),
                "phoneNumber" to (phoneNumber ?: ""),
                "imageUrl" to ""
            )

            mainRepository.updateMemberProfile(profileData).onSuccess {
                _uiState.update { state ->
                    state.copy(
                        imageUrl = it.imageUrl
                    )
                }
                _event.emit(MyPageEvent.ShowToastMessage("프로필 변경 성공"))
            }.onFailure {

            }
        }
    }


    fun uploadImageToS3(uri: Uri) {
        viewModelScope.launch {
            val file = uriToFile(uri)
            mainRepository.uploadFile(file).onSuccess { response ->
                val imageUrl = response.imageUrl
                Log.d("debugging", imageUrl)
            }.onFailure {
                // 에러 처리 로직 작성
            }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val contentResolver: ContentResolver = getApplication<Application>().contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp_image", ".jpg", getApplication<Application>().cacheDir)
        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return tempFile
    }

}