package com.bankingmobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferMoneyScreen(
    onBack: () -> Unit
) {

    // =========================================
    // VARIABLES
    // =========================================

    var accounts by remember {
        mutableStateOf<List<Account>>(emptyList())
    }

    var isLoadingAccounts by remember {
        mutableStateOf(true)
    }

    var selectedFromAccount by remember {
        mutableStateOf<Account?>(null)
    }

    var selectedToAccount by remember {
        mutableStateOf<Account?>(null)
    }

    var amount by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isProcessing by remember {
        mutableStateOf(false)
    }

    var fromExpanded by remember {
        mutableStateOf(false)
    }

    var toExpanded by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()


    // =========================================
    // LOAD ACCOUNTS
    // =========================================

    LaunchedEffect(Unit) {

        try {

            accounts = RetrofitClient.api.getAllAccounts()

        } catch (e: Exception) {

            message = "Could not load accounts."

        } finally {

            isLoadingAccounts = false
        }
    }


    // =========================================
    // SCREEN
    // =========================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Transfer Money",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Transfer money securely between accounts.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // =========================================
        // LOADING ACCOUNTS
        // =========================================

        if (isLoadingAccounts) {

            CircularProgressIndicator()

        } else if (accounts.isEmpty()) {

            Text(
                text = "No accounts available."
            )

        } else {

            // =========================================
            // TRANSFER CARD
            // =========================================

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {


                    // =====================================
                    // FROM ACCOUNT
                    // =====================================

                    Text(
                        text = "From Account",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = fromExpanded,
                        onExpandedChange = {
                            fromExpanded = !fromExpanded
                        }
                    ) {

                        OutlinedTextField(
                            value = selectedFromAccount?.let {
                                "${it.accountHolderName} - ${it.accountNumber}"
                            } ?: "Select account",

                            onValueChange = {},

                            readOnly = true,

                            label = {
                                Text("Source Account")
                            },

                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = fromExpanded
                                )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )


                        ExposedDropdownMenu(
                            expanded = fromExpanded,
                            onDismissRequest = {
                                fromExpanded = false
                            }
                        ) {

                            accounts.forEach { account ->

                                DropdownMenuItem(

                                    text = {
                                        Text(
                                            "${account.accountHolderName} - ${account.accountNumber}"
                                        )
                                    },

                                    onClick = {

                                        selectedFromAccount = account
                                        fromExpanded = false
                                        message = ""
                                    }
                                )
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )


                    // =====================================
                    // TO ACCOUNT
                    // =====================================

                    Text(
                        text = "To Account",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = toExpanded,
                        onExpandedChange = {
                            toExpanded = !toExpanded
                        }
                    ) {

                        OutlinedTextField(
                            value = selectedToAccount?.let {
                                "${it.accountHolderName} - ${it.accountNumber}"
                            } ?: "Select account",

                            onValueChange = {},

                            readOnly = true,

                            label = {
                                Text("Destination Account")
                            },

                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = toExpanded
                                )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )


                        ExposedDropdownMenu(
                            expanded = toExpanded,
                            onDismissRequest = {
                                toExpanded = false
                            }
                        ) {

                            accounts.forEach { account ->

                                DropdownMenuItem(

                                    text = {
                                        Text(
                                            "${account.accountHolderName} - ${account.accountNumber}"
                                        )
                                    },

                                    onClick = {

                                        selectedToAccount = account
                                        toExpanded = false
                                        message = ""
                                    }
                                )
                            }
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )


                    // =====================================
                    // AMOUNT
                    // =====================================

                    OutlinedTextField(

                        value = amount,

                        onValueChange = {
                            amount = it
                            message = ""
                        },

                        label = {
                            Text("Transfer Amount")
                        },

                        prefix = {
                            Text("R ")
                        },

                        singleLine = true,

                        modifier = Modifier.fillMaxWidth()
                    )


                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )


                    // =====================================
                    // TRANSFER BUTTON
                    // =====================================

                    Button(

                        onClick = {

                            val fromAccount = selectedFromAccount
                            val toAccount = selectedToAccount
                            val transferAmount = amount.toDoubleOrNull()


                            // -----------------------------
                            // VALIDATION
                            // -----------------------------

                            if (fromAccount == null) {

                                message = "Please select the account to transfer from."

                                return@Button
                            }


                            if (toAccount == null) {

                                message = "Please select the account to transfer to."

                                return@Button
                            }


                            if (fromAccount.id == toAccount.id) {

                                message = "You cannot transfer money to the same account."

                                return@Button
                            }


                            if (transferAmount == null || transferAmount <= 0) {

                                message = "Please enter a valid transfer amount."

                                return@Button
                            }


                            if (transferAmount > fromAccount.balance) {

                                message = "Insufficient funds."

                                return@Button
                            }


                            // -----------------------------
                            // START TRANSFER
                            // -----------------------------

                            isProcessing = true
                            message = ""


                            scope.launch {

                                try {

                                    val response =
                                        RetrofitClient.api.transfer(

                                            TransferRequest(
                                                fromAccountId = fromAccount.id!!,
                                                toAccountId = toAccount.id!!,
                                                amount = transferAmount
                                            )
                                        )


                                    if (response.isSuccessful) {

                                        message = "Transfer successful! ✓"

                                        amount = ""

                                        selectedFromAccount = null
                                        selectedToAccount = null

                                    } else {

                                        val error =
                                            response.errorBody()
                                                ?.string()

                                        message =
                                            error
                                                ?: "Transfer could not be completed."

                                    }

                                } catch (e: Exception) {

                                    message =
                                        "Transfer could not be completed."

                                } finally {

                                    isProcessing = false
                                }
                            }
                        },

                        modifier = Modifier.fillMaxWidth(),

                        enabled = !isProcessing
                    ) {

                        if (isProcessing) {

                            CircularProgressIndicator()

                        } else {

                            Text("Transfer Money")
                        }
                    }


                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )


                    // =====================================
                    // MESSAGE
                    // =====================================

                    if (message.isNotEmpty()) {

                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // =========================================
        // BACK BUTTON
        // =========================================

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("← Back")
        }
    }
}