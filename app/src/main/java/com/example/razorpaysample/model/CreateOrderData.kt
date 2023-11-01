package com.example.razorpaysample.model

data class CreateOrderData(
    val amount: Int,
    val currency: String,
    val notes: Notes,
    val receipt: String
)