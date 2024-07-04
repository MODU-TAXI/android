package com.motax.modutaxi.presentation.ui.main.mypage

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
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
    data object NavigateToEditNick : MyPageEvent()
    data object NavigateToUpdateProfile : MyPageEvent()
    data object NavigateToEditEmail : MyPageEvent()
    data object NavigateToNotification : MyPageEvent()
    data object NavigateToInquiry : MyPageEvent()
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
            val memberId = dataStoreManager.getMemberId()
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
            val gender = dataStoreManager.getMemberGender()
            val phoneNumber = dataStoreManager.getMemberPhoneNumber()

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

    //s3 업로드
    fun uploadImageToS3(uri: Uri) {
        viewModelScope.launch {
            val file = uriToFile(uri)
            mainRepository.uploadFile(file).onSuccess { response ->
                val imageUrl = response.imageUrl

                _uiState.update { state ->
                    state.copy(imageUrl = imageUrl)
                }

                updateMemberProfile(imageUrl)
            }.onFailure { }
        }
    }

    private fun updateMemberProfile(imageUrl: String) {
        viewModelScope.launch {

            val memberName = dataStoreManager.getMemberName()
            val gender = dataStoreManager.getMemberGender()
            val phoneNumber = dataStoreManager.getMemberPhoneNumber()

            val profileData = mapOf(
                "name" to (memberName ?: ""),
                "gender" to (gender ?: ""),
                "phoneNumber" to (phoneNumber ?: ""),
                "imageUrl" to imageUrl
            )

            mainRepository.updateMemberProfile(profileData).onSuccess {
                _event.emit(MyPageEvent.ShowToastMessage("프로필 변경 성공"))
            }.onFailure { }
        }
    }

    private fun uriToFile(uri: Uri): File {
        val contentResolver: ContentResolver = getApplication<Application>().contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val tempFile =
            File.createTempFile("temp_image", ".jpg", getApplication<Application>().cacheDir)
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

    fun navigateToEditNick() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToEditNick)
        }
    }

    fun navigateToUpdateProfile() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToUpdateProfile)
        }
    }

    fun navigateToEditEmail() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToEditEmail)
        }
    }

    fun navigateToNotification() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToNotification)
        }
    }

    fun navigateToInquiry() {
        viewModelScope.launch {
            _event.emit(MyPageEvent.NavigateToInquiry)
        }
    }
}