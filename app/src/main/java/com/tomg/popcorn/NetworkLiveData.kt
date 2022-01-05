package com.tomg.popcorn

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.UiThread
import androidx.lifecycle.LiveData

class NetworkLiveData(private val context: Context) : LiveData<Boolean>() {
  private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  private lateinit var networkReceiver: BroadcastReceiver

  @UiThread
  override fun setValue(value: Boolean?) {
    if (getValue() == value) {
      return
    }
    super.setValue(value)
  }

  @UiThread
  override fun onActive() {
    super.onActive()

    updateConnection()

    networkReceiver = object : BroadcastReceiver() {

      @UiThread
      override fun onReceive(context: Context, intent: Intent) {
        updateConnection()
      }
    }

    @Suppress("DEPRECATION")
    context.registerReceiver(
      networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    )
  }

  @UiThread
  override fun onInactive() {
    super.onInactive()
    //Timber.d("NETWORK_ unregister network receiver")
    context.unregisterReceiver(networkReceiver)
  }

  @UiThread
  @Suppress("DEPRECATION")
  private fun updateConnection() {
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    value = activeNetwork?.isConnected == true
  }
}