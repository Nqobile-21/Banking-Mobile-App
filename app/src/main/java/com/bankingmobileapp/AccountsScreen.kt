package com.bankingmobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AccountsScreen(
    onDepositSelected: (Account) -> Unit,
    onWithdrawSelected: (Account) -> Unit
) {

    var accounts by remember {
        mutableStateOf<List<Account>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }


    // =========================================
    // LOAD ACCOUNTS
    // =========================================

    LaunchedEffect(Unit) {

        try {

            accounts = RetrofitClient.api.getAllAccounts()

        } catch (e: Exception) {

            errorMessage = "Could not load accounts."

        } finally {

            isLoading = false
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // =========================================
        // HEADER
        // =========================================

        Text(
            text = "My Accounts",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Manage your banking accounts",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // =========================================
        // LOADING
        // =========================================

        if (isLoading) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressIndicator()
            }


            // =========================================
            // ERROR
            // =========================================

        } else if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage
            )


            // =========================================
            // EMPTY
            // =========================================

        } else if (accounts.isEmpty()) {

            Text(
                text = "No accounts found.",
                style = MaterialTheme.typography.bodyLarge
            )


            // =========================================
            // ACCOUNTS
            // =========================================

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(accounts) { account ->

                    ProfessionalAccountCard(
                        account = account,

                        onDepositClick = {
                            onDepositSelected(account)
                        },

                        onWithdrawClick = {
                            onWithdrawSelected(account)
                        }
                    )
                }
            }
        }
    }
}


// =====================================================
// PROFESSIONAL ACCOUNT CARD
// =====================================================

@Composable
fun ProfessionalAccountCard(
    account: Account,
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            // =========================================
            // ACCOUNT HOLDER
            // =========================================

            Text(
                text = account.accountHolderName,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )


            // =========================================
            // ACCOUNT NUMBER
            // =========================================

            Text(
                text = "Account Number",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = account.accountNumber,
                style = MaterialTheme.typography.bodyLarge
            )


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            // =========================================
            // BALANCE
            // =========================================

            Text(
                text = "Available Balance",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "R${"%.2f".format(account.balance)}",
                style = MaterialTheme.typography.headlineSmall
            )


            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = "Account ID: ${account.id}",
                style = MaterialTheme.typography.bodySmall
            )


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // =========================================
            // ACTION BUTTONS
            // =========================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = onDepositClick,
                    modifier = Modifier.weight(1f)
                ) {

                    Text("Deposit")
                }


                Button(
                    onClick = onWithdrawClick,
                    modifier = Modifier.weight(1f)
                ) {

                    Text("Withdraw")
                }
            }
        }
    }
}
