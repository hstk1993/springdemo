package com.example.springdemo.api

data class AdvResponseModel(val result:Result)
data class Result(val orderNo: String, val faceId: String, val optimalDomain: String, val success: Boolean)
