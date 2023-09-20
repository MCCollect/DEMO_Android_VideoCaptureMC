package com.mc.demovideocapturemc

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val permission by lazy {
        Permission(this@MainActivity)
    }

    private lateinit var webViewVideoCapture: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLegacyBinding()
        requestFirsTimePermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (!permission.checkPermissions(verifyNoGranted = false)) {
            createDialog(
                "Permisos necesarios", "No has otorgado todos los permisos",
                "Solicitar de nuevo"
            )
        } else {
            configureWebViewVideoCapture()
        }

    }

    private fun initLegacyBinding() {
        setContentView(R.layout.activity_main)
        webViewVideoCapture = findViewById(R.id.webView)
    }

    private fun requestFirsTimePermission() {


        if (!permission.checkPermissions(verifyNoGranted = false)) {
            permission.checkPermissions(verifyNoGranted = true)
        } else {
            configureWebViewVideoCapture()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebViewVideoCapture() {
        webViewVideoCapture.settings.javaScriptEnabled = true
        webViewVideoCapture.settings.mediaPlaybackRequiresUserGesture = false
        webViewVideoCapture.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                runOnUiThread {
                    val permissions = arrayOf(
                        PermissionRequest.RESOURCE_AUDIO_CAPTURE,
                        PermissionRequest.RESOURCE_VIDEO_CAPTURE
                    )
                    request.grant(permissions)
                }
            }

        }



        webViewVideoCapture.loadUrl("https://dev.mcnoc.mx/Credifiel_Originacion/camara.html?V_Nombre=Juanito&V_Entidad=Credifiel&V_Monto=3500&V_Frecuencia=55%20Quincenas&V_Retencion=Nomina&V_ID=25")

    }


    private fun createDialog(
        title: String,
        message: String,
        positiveButtonText: String
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.setPositiveButton(positiveButtonText) { dialog, which ->
            permission.checkPermissions(verifyNoGranted = true)
        }

        /*builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNeutralButton("Maybe") { dialog, which ->
            Toast.makeText(
                applicationContext,
                "Maybe", Toast.LENGTH_SHORT
            ).show()
        }*/
        builder.show()

    }

}