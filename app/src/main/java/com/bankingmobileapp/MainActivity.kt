package com.bankingmobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bankingmobileapp.ui.theme.BankingMobileAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BankingMobileAppTheme {

                var currentScreen by remember {
                    mutableStateOf("dashboard")
                }

                var selectedAccount by remember {
                    mutableStateOf<Account?>(null)
                }

                when (currentScreen) {

                    // =====================================
                    // DASHBOARD
                    // =====================================

                    "dashboard" -> {

                        BankingDashboard(

                            onCreateAccountClick = {
                                currentScreen = "createAccount"
                            },

                            onViewAccountsClick = {
                                currentScreen = "accounts"
                            },

                            onTransferClick = {
                                currentScreen = "transfer"
                            }
                        )
                    }


                    // =====================================
                    // CREATE ACCOUNT
                    // =====================================

                    "createAccount" -> {

                        Scaffold(
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->

                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(24.dp)
                            ) {

                                Button(
                                    onClick = {
                                        currentScreen = "dashboard"
                                    }
                                ) {

                                    Text("← Back")
                                }

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                CreateAccountScreen()
                            }
                        }
                    }


                    // =====================================
                    // VIEW ACCOUNTS
                    // =====================================

                    "accounts" -> {

                        Scaffold(
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->

                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(24.dp)
                            ) {

                                Button(
                                    onClick = {
                                        currentScreen = "dashboard"
                                    }
                                ) {

                                    Text("← Back")
                                }

                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )

                                AccountsScreen(

                                    onDepositSelected = { account ->

                                        selectedAccount = account
                                        currentScreen = "deposit"
                                    },

                                    onWithdrawSelected = { account ->

                                        selectedAccount = account
                                        currentScreen = "withdraw"
                                    }
                                )
                            }
                        }
                    }


                    // =====================================
                    // DEPOSIT
                    // =====================================

                    "deposit" -> {

                        selectedAccount?.let { account ->

                            DepositScreen(

                                account = account,

                                onDepositComplete = {

                                    currentScreen = "accounts"
                                }
                            )
                        }
                    }


                    // =====================================
                    // WITHDRAW
                    // =====================================

                    "withdraw" -> {

                        selectedAccount?.let { account ->

                            WithdrawScreen(

                                account = account,

                                onWithdrawComplete = {

                                    currentScreen = "accounts"
                                }
                            )
                        }
                    }


                    // =====================================
                    // TRANSFER
                    // =====================================

                    "transfer" -> {

                        TransferMoneyScreen(

                            onBack = {

                                currentScreen = "dashboard"
                            }
                        )
                    }
                }
            }
        }
    }
}


// =====================================================
// PROFESSIONAL BANKING DASHBOARD
// =====================================================

@Composable
fun BankingDashboard(
    onCreateAccountClick: () -> Unit,
    onViewAccountsClick: () -> Unit,
    onTransferClick: () -> Unit
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


    // =====================================
    // LOAD ACCOUNTS
    // =====================================

    LaunchedEffect(Unit) {

        try {

            accounts = RetrofitClient.api.getAllAccounts()

        } catch (e: Exception) {

            errorMessage = "Could not load balance."

        } finally {

            isLoading = false
        }
    }


    // =====================================
    // CALCULATE TOTAL BALANCE
    // =====================================

    val totalBalance = accounts.sumOf {
        it.balance
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(
            modifier = Modifier.height(32.dp)
        )


        // =====================================
        // WELCOME
        // =====================================

        Text(
            text = "Welcome back 👋",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Manage your money securely",
            style = MaterialTheme.typography.bodyLarge
        )


        Spacer(
            modifier = Modifier.height(32.dp)
        )


        // =====================================
        // BALANCE CARD
        // =====================================

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(24.dp)
            ) {

                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                if (isLoading) {

                    CircularProgressIndicator()

                } else if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage
                    )

                } else {

                    Text(
                        text = "R${"%.2f".format(totalBalance)}",
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "${accounts.size} account(s)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }


        Spacer(
            modifier = Modifier.height(30.dp)
        )


        // =====================================
        // QUICK ACTIONS
        // =====================================

        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // =====================================
        // VIEW ACCOUNTS
        // =====================================

        Button(
            onClick = onViewAccountsClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("💳  View Accounts")
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // =====================================
        // TRANSFER
        // =====================================

        Button(
            onClick = onTransferClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("💸  Transfer Money")
        }


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // =====================================
        // CREATE ACCOUNT
        // =====================================

        Button(
            onClick = onCreateAccountClick,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("➕  Create Account")
        }
    }
}
