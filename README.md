# Test Automation Framework Documentation

## Project Overview

This project automates core banking scenarios for the **ParaBank demo site** using **Selenium**, **TestNG**, **Java**.

The framework covers a wide range of functionalities:

- **Account Registration** (as a precondition to other tests)
- **Login Verification** (Valid & Invalid Scenarios)
- **Open New Account**
- **Transfer Funds Between Accounts**
- **Bill Pay**

Other features include:

- Logging using **Log4j**
- Test Reporting using **Allure**
- RetryAnalyzer for flaky test stability
- Data-Driven Testing using: CSV, JSON, Properties, and `@DataProvider`
- Scalable and modular structure

---

## Installation and Prerequisites

### Prerequisites:

- **Java** (JDK 17 or higher) – [Download here](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Maven** – [Download here](https://maven.apache.org/download.cgi)
- **TestNG** – Included via Maven
- **Allure CLI** – [Installation Guide](https://docs.qameta.io/allure/)
- **Log4j** – For logging
- **Browser Drivers** – e.g., ChromeDriver [Download](https://sites.google.com/a/chromium.org/chromedriver/)

---

## Technologies Used

1. **Java**
2. **Selenium WebDriver**
3. **TestNG**
4. **Allure Report**
5. **Log4j**
6. **Apache POI** – For Excel
7. **Apache Commons CSV** – For CSV

---

### 📁 Project Structure
```plaintext
BaraBank/
├── src/
│ ├── main/
│ │ └── java/
│ │ ├── pages/
│ │ │ ├── AccountOverviewPage.java
│ │ │ ├── BillPayPage.java
│ │ │ ├── LoginPage.java
│ │ │ ├── OpenNewAccountPage.java
│ │ │ ├── RegisterPage.java
│ │ │ └── TransferFundsPage.java
│ │ └── utilities/
│ │ ├── ConfigReader.java
│ │ ├── CSVFileManager.java
│ │ ├── ExcelFileManager.java
│ │ ├── JsonFileManager.java
│ │ └── FileUtils.java
│
├── src/
│ └── test/
│ └── java/
│ ├── tests/
│ │ ├── BillPayTest.java
│ │ ├── LoginTest.java
│ │ ├── OpenNewAccountTest.java
│ │ └── Base.java
│ └── utilities/
│ ├── RetryAnalyzer.java
│ └── RetryListener.java
│
├── src/
│ └── test/
│ └── resources/
│ ├── test-data/
│ │ ├── data.json
│ │ ├── invalidPillDatajson.json
│ │ └── validPillData.json
│ └── config.properties
│
├── allure-results/
├── allure-report/
├── pom.xml
└── README.md
```
## Allure Report

To generate and view the Allure report, follow these steps:

1. Run the tests using Maven:
mvn clean test
2. Generate the report:
allure generate
3. Serve the report locally on your machine:
 allure serve -h localhost

#  Setup

## 1. Clone the Repository
```bash
git clone https://github.com/your-repo/Demoblaze_Project.git
```
```bash
cd BaraBank
```
```bash
mvn clean test
```

# ParaBank Test Scenarios (Registration as Precondition)

**Website:** [https://parabank.parasoft.com](https://parabank.parasoft.com)  
**Testing Framework:** TestNG  
**Precondition:** Account Registration  
**Objective:** Ensure that a valid account exists before executing any scenarios.

---

## 🔑 Precondition: Account Registration
### Steps:
1. Navigate to ParaBank homepage.
2. Click **Register**.
3. Fill all required fields:
   - First Name, Last Name
   - Address, City, State, Zip Code
   - Phone Number, SSN
   - Username, Password, Confirm Password
4. Click **Register**.
5. Validate confirmation message:  
   *“Your account was created successfully. You are now logged in.”*
6. Log out to prepare for subsequent scenarios.

---

## 📝 Scenario 1: Login Verification
**Objective:** Verify that a registered account can log in successfully and that invalid credentials are handled correctly.  

### Steps:
1. Navigate to ParaBank homepage.
2. Enter **valid username and password** (from precondition).
3. Click **Log In**.
4. Validate successful login redirects to **Accounts Overview**.
5. Log out.
6. Attempt login with **invalid credentials**.
7. Validate error message:  
   *“The username and password could not be verified.”*

### ✅ Positive Tests
- Login with valid username and password → User should be redirected to **Accounts Overview**.  

### ❌ Negative Tests
- Login with wrong username or password → Show error message: *“The username and password could not be verified.”*  
- Leave username/password empty → Validation error should appear.  

---

## 📝 Scenario 2: Open New Account
**Objective:** Verify that a new checking or savings account can be created.  

### Steps:
1. Login with precondition account credentials.
2. Navigate to **Open New Account**.
3. Select account type (**Checking** or **Savings**).
4. Choose an existing account for initial funding.
5. Click **Open New Account**.
6. Validate confirmation page shows the new account number.
7. Navigate to **Accounts Overview** and verify new account appears with correct type and balance.

### ✅ Positive Tests
- Open new **Checking** account → Account should appear in Accounts Overview.  
- Open new **Savings** account → Account should appear in Accounts Overview.  

### ❌ Negative Tests
- Try opening account **without selecting account type** → Show validation error.  
- Try opening account **without selecting funding account** → Show validation error.  


---

## 📝 Scenario 3: Transfer Funds Between Accounts
**Objective:** Verify that funds can be transferred between accounts and balances update correctly.  

### Steps:
1. Login with precondition account.
2. Navigate to **Transfer Funds**.
3. Select **From Account** and **To Account**.
4. Enter transfer amount (use DDT for multiple amounts).
5. Click **Transfer**.
6. Validate confirmation page displays correct from/to account and transfer amount.
7. Navigate to **Accounts Overview** and verify updated balances.

### ✅ Positive Tests
- Transfer valid amount → Balance should decrease in From Account and increase in To Account.  

### ❌ Negative Tests
- Enter **negative amount** → System should reject.   
- Leave **amount field empty** → Show validation error.   

---

## 📝 Scenario 4: Bill Pay
**Objective:** Verify that a bill can be paid successfully and appears in transaction history.  

### Steps:
1. Login with precondition account.
2. Navigate to **Bill Pay**.
3. Enter payee details (name, address, account number).
4. Enter payment amount (use DDT for multiple amounts).
5. Click **Send Payment**.
6. Validate confirmation page shows correct payment details.
7. Navigate to **Accounts Overview** and verify transaction is recorded correctly.

### ✅ Positive Tests
- Pay bill with valid payee details and sufficient balance → Transaction should appear in history.  

### ❌ Negative Tests
- Leave any mandatory field (payee name, account number, amount) empty → Validation error.  
- Enter **invalid account number** → Payment should fail.  











