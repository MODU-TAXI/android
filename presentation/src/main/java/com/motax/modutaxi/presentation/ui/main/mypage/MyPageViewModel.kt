package com.motax.modutaxi.presentation.ui.main.mypage

import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModel
import com.motax.modutaxi.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class MyPageUiState(
    val isReported: Boolean = false
)

@HiltViewModel
class MyPageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    fun onProfileImageClick() {
        Log.d("MyPageViewModel", "프로필 이미지 클릭!!!!!!!!!!")
    }

    fun onProfileChangeClick(view: View) {
        Log.d("MyPageViewModel", "프로필 체인지 클릭!!!!!!!!!!")
        view.context?.let {context ->
            showPopupMenu(context, view)
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
}