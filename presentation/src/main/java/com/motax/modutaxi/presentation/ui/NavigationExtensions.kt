package com.motax.modutaxi.presentation.ui

import androidx.navigation.NavController
import com.motax.modutaxi.presentation.MainNavDirections


internal fun NavController.toMatchDetail(id: Long){
    val action = MainNavDirections.globalActionToMatchDetailFragment(id)
    navigate(action)
}