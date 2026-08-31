package com.bankingmobileapp

data class Account(
    val id: Long? = null,
    val accountNumber: String,
    val accountHolderName: String,
    val balance: Double
)