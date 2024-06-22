package com.motax.modutaxi.presentation.ui.main.mypage

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.motax.modutaxi.data.config.DataStoreManager
import com.motax.modutaxi.domain.repository.MainRepository
import com.motax.modutaxi.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyPageUiState(
    val isReported: Boolean = false,
    val imageUrl: String = "",
    val nickname: String = "",
    val name: String ="",
    val certified: Boolean = false,
)

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        loadMemberData()
    }

    fun onProfileImageClick() {
        Log.d("MyPageViewModel", "프로필 이미지 클릭!!!!!!!!!!")
    }

    fun onProfileChangeClick(view: View) {
        Log.d("MyPageViewModel", "프로필 체인지 클릭!!!!!!!!!!")
        view.context?.let {context ->
            showPopupMenu(context, view)
        }
    }

    private fun loadMemberData() {
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



    private fun showPopupMenu(context: Context, anchor: View) {
        val popupMenu = PopupMenu(context, anchor)
        popupMenu.menuInflater.inflate(R.menu.profile_image_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_set_default -> {
                    // 기본 이미지로 설정 처리
                    true
                }
                R.id.action_select_from_album -> {
                    // 앨범에서 선택 처리
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun logMemberInfo() {
        viewModelScope.launch {
            val memberId = dataStoreManager.getMemberId()
            val memberName = dataStoreManager.getMemberName()
            Log.d("debugging", "Member ID: $memberId, Member Name: $memberName")
        }
    }
}