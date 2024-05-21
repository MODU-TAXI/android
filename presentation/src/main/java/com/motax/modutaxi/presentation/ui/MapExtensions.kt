package com.motax.modutaxi.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat

internal fun Context.requestLocationPermission(
    permissionList: Array<String>,
    onLauncherStart: () -> Unit,
    moveToCurLocation: (Boolean) -> Unit
) {
    var permissionFlag = false
    permissionList.forEach { permission ->
        permissionFlag = ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    if (permissionFlag) {
        checkLocationIsOn() {
            moveToCurLocation(it)
        }
    } else {
        onLauncherStart()
        Toast.makeText(this, "위치권한을 허용해주세요", Toast.LENGTH_SHORT).show()
    }
}


internal fun Context.checkLocationIsOn(
    moveToCurLocation: (Boolean) -> Unit
) {
    val locationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        moveToCurLocation(true)
    } else {
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        Toast.makeText(this, "휴대폰 GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        moveToCurLocation(false)
    }
}