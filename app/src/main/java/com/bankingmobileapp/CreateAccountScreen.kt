package com.bankingmobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CreateAccountScreen() {

    var accountNumber by remember {
        mutableStateOf("")
    }

    var accountHolderName by remember {
        mutableStateOf("")
    }

    var initialDeposit by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Create Bank Account"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = accountNumber,
            onValueChange = {
                accountNumber = it
            },
            label = {
                Text("Account Number")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = accountHolderName,
            onValueChange = {
                accountHolderName = it
            },
            label = {
                Text("Account Holder Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = initialDeposit,
            onValueChange = {
                initialDeposit = it
            },
            label = {
                Text("Initial Deposit")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

                if (
                    accountNumber.isBlank() ||
                    accountHolderName.isBlank() ||
                    initialDeposit.isBlank()
                ) {

                    message = "Please complete all fields."

                } else {

                    val balance = initialDeposit.toDoubleOrNull()

                    if (balance == null || balance < 0) {

                        message = "Please enter a valid deposit amount."

                    } else {

                        val account = Account(
                            accountNumber = accountNumber,
                            accountHolderName = accountHolderName,
                            balance = balance
                        )

                        scope.launch {

                            try {

                                val response =
                                    RetrofitClient.api.createAccount(account)

                                if (response.isSuccessful) {

                                    val createdAccount = response.body()

                                    message =
                                        "Account created successfully! " +
                                                "Account ID: ${createdAccount?.id}"

                                    accountNumber = ""
                                    accountHolderName = ""
                                    initialDeposit = ""

                                } else {

                                    message =
                                        "Failed to create account. " +
                                                "Error: ${response.code()}"

                                }

                            } catch (e: Exception) {

                                message =
                                    "Could not connect to the banking server."

                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (message.isNotEmpty()) {

            Text(
                text = message
            )
        }
    }
}