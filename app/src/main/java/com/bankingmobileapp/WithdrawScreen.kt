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
fun WithdrawScreen(
    account: Account,
    onWithdrawComplete: () -> Unit
) {

    var amount by remember {
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
            text = "Withdraw Money"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Account: ${account.accountNumber}"
        )

        Text(
            text = "Holder: ${account.accountHolderName}"
        )

        Text(
            text = "Current Balance: R${"%.2f".format(account.balance)}"
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            label = {
                Text("Withdrawal Amount")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {

                val withdrawalAmount = amount.toDoubleOrNull()

                if (withdrawalAmount == null || withdrawalAmount <= 0) {

                    message = "Please enter a valid amount."

                } else if (withdrawalAmount > account.balance) {

                    message = "Insufficient funds."

                } else {

                    scope.launch {

                        try {

                            val response = RetrofitClient.api.withdraw(
                                account.id ?: 0L,
                                withdrawalAmount
                            )

                            if (response.isSuccessful) {

                                message = "Withdrawal successful!"

                                amount = ""

                                onWithdrawComplete()

                            } else {

                                message =
                                    "Withdrawal failed. Error: ${response.code()}"

                            }

                        } catch (e: Exception) {

                            message =
                                "Could not connect to the banking server."

                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Withdraw")
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