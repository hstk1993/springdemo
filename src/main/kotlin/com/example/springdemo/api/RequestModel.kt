package com.example.springdemo.api

data class RequestModel(
    val appId: String,
    val orderNo: String,
    val name: String,
    val idNo: String,
    val userId: String,
    val version: String,
    val sign: String,
    val nonce: String,
    val liveInterType: String,
)
