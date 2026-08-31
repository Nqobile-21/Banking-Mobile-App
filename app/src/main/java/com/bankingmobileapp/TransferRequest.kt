package com.bankingmobileapp

data class TransferRequest(
    val fromAccountId: Long,
    val toAccountId: Long,
    val amount: Double
)