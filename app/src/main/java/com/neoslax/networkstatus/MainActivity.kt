package com.neoslax.networkstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.neoslax.networkstatus.ui.theme.NetworkStatusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val networkConnectivity = NetworkConnectivityImpl(this)
        super.onCreate(savedInstanceState)
        setContent {
            val netState = networkConnectivity.getStatus()
                .collectAsState(initial = NetworkConnectivity.Status.Unavailable)
            NetworkStatusTheme {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = netState.value.toString())
                }
            }
        }
    }
}