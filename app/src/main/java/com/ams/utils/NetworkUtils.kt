package com.ams.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import kotlin.apply

object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnectedOrConnecting ?: false
        }
    }

    fun askToEnableInternet(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle("No Internet Connection")
            setMessage("Please enable your internet connection to proceed")
            setPositiveButton("Settings") { dialog, which ->
                context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dialog.dismiss()
            }
            setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
