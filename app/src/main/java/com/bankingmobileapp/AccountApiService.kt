package com.bankingmobileapp

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountApiService {

    @GET("api/accounts")
    suspend fun getAllAccounts(): List<Account>

    @GET("api/accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Long
    ): Response<Account>

    @POST("api/accounts")
    suspend fun createAccount(
        @Body account: Account
    ): Response<Account>

    @POST("api/accounts/{id}/deposit")
    suspend fun deposit(
        @Path("id") id: Long,
        @Query("amount") amount: Double
    ): Response<Account>

    @POST("api/accounts/{id}/withdraw")
    suspend fun withdraw(
        @Path("id") id: Long,
        @Query("amount") amount: Double
    ): Response<Account>

    @POST("api/accounts/transfer")
    suspend fun transfer(
        @Body transferRequest: TransferRequest
    ): Response<Unit>
}