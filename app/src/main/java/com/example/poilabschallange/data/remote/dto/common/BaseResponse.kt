package com.example.poilabschallange.data.remote.dto.common

data class BaseResponse <T>(
    val success: Boolean = false,
    val message: String? = null,
    val data: T? = null,
)