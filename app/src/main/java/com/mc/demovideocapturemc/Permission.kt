package com.mc.demovideocapturemc

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission(private val activity: Activity) {

    private var globalPermissions: Array<String> = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    fun checkPermissions(verifyNoGranted: Boolean): Boolean {
        var countPermissions = 0
        /*
          Hacer un recorrido por los permisos globales para revisar que permisos se han otorgado,
          la variable countPermissions se incrementa si no tenemos otorgado el permiso.
         */for (globalPermission in globalPermissions) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    globalPermission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                countPermissions++
            }
        }

        /*
         * Arreglo de Strings del tamaño de permisos que no han sido otorgados.
         */
        val permissions = arrayOfNulls<String>(countPermissions)
        countPermissions = 0

        /*
         * Llenado del arreglo de Strings con los permisos que no fueron otorgados para
         * posteriormente pedirlos al usuario.
         */for (globalPermission in globalPermissions) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    globalPermission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions[countPermissions++] = globalPermission
            }
        }
        if (countPermissions > 0) {
            if (verifyNoGranted) {
                ActivityCompat.requestPermissions(activity, permissions, 1)
            }
            return false
        }
        return true
    }

}