\# 📱 Banking Mobile App



A professional Android banking application developed using \*\*Kotlin and Jetpack Compose\*\*, integrated with a \*\*Spring Boot REST API\*\* and \*\*MySQL\*\* database.



The application provides a mobile interface for managing bank accounts and performing common banking operations such as creating accounts, viewing accounts, depositing money, withdrawing money and transferring funds between accounts.



\---



\## 🚀 Project Overview



The Banking Mobile App was developed as a full-stack software development project to demonstrate the integration of a modern Android application with a backend REST API and relational database.



The Android application communicates with the Spring Boot backend through RESTful API requests using Retrofit.



\### Architecture



```text

Android Mobile Application

&#x20;         │

&#x20;         │ REST API

&#x20;         ▼

&#x20;    Spring Boot API

&#x20;         │

&#x20;         │ JPA / Hibernate

&#x20;         ▼

&#x20;       MySQL

```



\---



\## ✨ Features



\### 🏦 Account Management



\* Create new bank accounts

\* View existing accounts

\* Display account details

\* Display available account balances



\### 💰 Deposit Money



Users can select an account and deposit money into the selected account.



\### 💸 Withdraw Money



Users can select an account and withdraw money from the available balance.



\### 🔄 Transfer Money



Users can transfer money between existing accounts.



The transfer screen uses account-selection dropdowns instead of requiring users to manually enter account IDs, providing a more professional and user-friendly experience.



\### 📊 Account Information



The application displays account information retrieved from the backend database, including available balances.



\### ⚠️ Validation \& Error Handling



The application provides feedback for invalid operations and unsuccessful requests, helping users understand when an operation cannot be completed.



\---



\## 🛠️ Technologies Used



\### Android



\* Kotlin

\* Jetpack Compose

\* Android Studio

\* Material Design

\* Retrofit



\### Backend



\* Java

\* Spring Boot

\* Spring Data JPA

\* Hibernate

\* REST API

\* Maven



\### Database



\* MySQL



\### Development Tools



\* Git

\* GitHub

\* Android Emulator

\* Postman



\---



\## 📱 Application Screenshots



\### Create Account



!\[Create Account](Screenshots/img%201.png)



\### Accounts



!\[Accounts](Screenshots/img%202.png)



\### Deposit Money



!\[Deposit Money](Screenshots/img%203.png)



\### Withdraw Money



!\[Withdraw Money](Screenshots/img%204.png)



\### Transfer Money



!\[Transfer Money](Screenshots/img%205.png)



\---



\## 🔗 Backend API



The mobile application communicates with a Spring Boot banking REST API responsible for processing account operations and financial transactions.



The backend provides functionality for:



\* Account creation

\* Account retrieval

\* Account updates

\* Account deletion

\* Deposits

\* Withdrawals

\* Money transfers

\* Validation

\* Transaction processing

\* Exception handling



\---



\## 🗄️ Database



The backend uses \*\*MySQL\*\* to persist banking account information.



Spring Data JPA and Hibernate are used to manage communication between the Spring Boot application and the database.



\---



\## ⚙️ How to Run the Project



\### Prerequisites



Make sure the following are installed:



\* Android Studio

\* JDK 21

\* MySQL

\* Git

\* Spring Boot backend environment



\### 1. Clone the repository



```bash

git clone https://github.com/Nqobile-21/Banking-Mobile-App.git

```



\### 2. Open the project



Open the cloned project in \*\*Android Studio\*\*.



Allow Gradle to sync and complete the project setup.



\### 3. Start the Spring Boot backend



The Android application requires the Spring Boot backend to be running.



Start the backend using:



```bash

./mvnw spring-boot:run

```



On Windows:



```powershell

.\\mvnw.cmd spring-boot:run

```



\### 4. Configure the database



Create the MySQL database required by the Spring Boot backend and ensure the database credentials and connection URL are correctly configured in the backend application's `application.properties`.



\### 5. Run the Android application



Start an Android emulator through Android Studio's Device Manager and run the application.



\---



\## 📂 Project Structure



```text

BankingMobileApp/

│

├── app/

│   └── src/

│       └── main/

│           └── java/

│               └── com/

│                   └── bankingmobileapp/

│                       ├── Account.kt

│                       ├── AccountApiService.kt

│                       ├── AccountsScreen.kt

│                       ├── CreateAccountScreen.kt

│                       ├── DepositScreen.kt

│                       ├── MainActivity.kt

│                       ├── RetrofitClient.kt

│                       ├── TransferMoneyScreen.kt

│                       ├── TransferRequest.kt

│                       └── WithdrawScreen.kt

│

├── Screenshots/

│   ├── img 1.png

│   ├── img 2.png

│   ├── img 3.png

│   ├── img 4.png

│   └── img 5.png

│

├── build.gradle.kts

├── gradle.properties

├── settings.gradle.kts

└── README.md

```



\---



\## 🎯 Learning Objectives



This project provided practical experience in:



\* Android application development

\* Kotlin programming

\* Jetpack Compose UI development

\* REST API integration

\* Spring Boot development

\* Database integration

\* MySQL

\* Spring Data JPA

\* Hibernate

\* Retrofit

\* CRUD operations

\* Financial transaction processing

\* Input validation

\* Exception handling

\* Git and GitHub

\* Full-stack application development



\---



\## 🔮 Future Improvements



Potential future improvements include:



\* User authentication and login

\* Secure password management

\* Transaction history screen

\* Improved transaction receipts

\* Push notifications

\* Biometric authentication

\* Improved UI animations

\* Cloud deployment of the backend

\* Automated testing

\* CI/CD integration



\---



\## 👩🏽‍💻 Author



\*\*Nqobile Rose Mahlangu\*\*



BSc Information Technology — Software Engineering



Aspiring Software Engineer



GitHub:

https://github.com/Nqobile-21



LinkedIn:

https://www.linkedin.com/in/nqobile-rose-mahlangu-92b54b202



\---



\## 📄 License



This project was developed for educational and portfolio purposes.



