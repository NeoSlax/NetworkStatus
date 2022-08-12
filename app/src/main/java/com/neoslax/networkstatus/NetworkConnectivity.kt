package com.neoslax.networkstatus

import kotlinx.coroutines.flow.Flow

interface NetworkConnectivity {
    fun getStatus(): Flow<Status>

    enum class Status {
        Connected, Losing, Lost, Unavailable
    }
}