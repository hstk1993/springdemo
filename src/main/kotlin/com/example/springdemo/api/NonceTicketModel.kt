package com.example.springdemo.api

data class NonceTicketModel (val tickets:MutableList<TicketModel>)
data class TicketModel(val value:String)