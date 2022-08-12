package com.neoslax.networkstatus

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NetworkConnectivityImpl(private val context: Context) : NetworkConnectivity {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun getStatus(): Flow<NetworkConnectivity.Status> {

        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(NetworkConnectivity.Status.Connected) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(NetworkConnectivity.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(NetworkConnectivity.Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(NetworkConnectivity.Status.Unavailable) }
                }
            }
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()
    }
}