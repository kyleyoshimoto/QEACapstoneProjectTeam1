# 📺 YouTube Functional Testing - QEA Capstone Project

This is an automated testing framework built with **Selenium WebDriver**, **Cucumber**, and **Java** to test various YouTube functionalities. The goal of this capstone project is to validate both **positive** and **negative** user scenarios, ensuring a smooth and accurate user experience.

---

## 🚀 Technologies Used

- **Java 17+**
- **Selenium WebDriver**
- **Cucumber (Gherkin syntax)**
- **JUnit**
- **Maven**
- **IntelliJ IDEA**
- **Git & GitHub**

---

## 📁 Project Structure

<pre>
QEACapstoneProjectTeam1/
├── pom.xml
├── README.md
├── .gitignore
├── src/
│   └── features/
│	└── negativeTesting.feature
│	└── positiveTesting.feature
│   └── test/
│       └── java/
│            ├── steps/
│	     │	  ├── CommonSteps.java
│            │    ├── YTNegativeTestSteps.java
│	     │	  └── YTPositiveTestSteps.java
│            ├── actions/
│	     │ 	  ├── CommonActions.java
│            │    ├── YTNegativeActions.java
│	     │ 	  └── YTPositiveActions.java
│            └── elements/
│                 ├── YTNegativeTestElements.java
|		  └── YTPositiveTestElements.java
└── target/
    └── cucumber-reports.html
</pre>

---

## ✅ Test Scenarios

### 🔹 Positive Testing

1. **Search for "Cucumber Tests"**
   - Confirms presence of "Introduction to Cucumber" in results.
2. **Click Video & Verify Date**
   - Validates date posted is `May 14, 2017`.
3. **Share > Embed**
   - Confirms modal opens and displays correct iframe embed code.
4. **Sort Comments**
   - Verifies "Newest First" sorting shows the most recent comment.
5. **Sign In (Valid Credentials)**
   - Confirms a successful login.

### 🔸 Negative Testing

6. **Invalid Passwords**
   - Triggers warning messages for incorrect passwords.
7. **Invalid Emails**
   - Detects malformed or unregistered emails and shows appropriate error messages.

---

## 🧪 How to Run the Tests

### 1. Clone the Repository

```bash
git clone https://github.com/YourUsername/QEACapstoneProjectTeam1.git
cd QEACapstoneProjectTeam1
```

### 2. Install Dependencies

Ensure you have Maven installed. Then run:

```bash
mvn test
```

### 3. Run All Tests

```bash
mvn test
```

## 📋 Reports

After tests complete, an HTML report will be generated at:

target/Report1/index.html

## 🔐 Notes on Credentials
	•	For login tests, test credentials should be used.
	•	Do not commit personal Gmail passwords. Use mock/demo accounts if possible.

⚠️ Troubleshooting
	•	ChromeDriver errors?
Ensure ChromeDriver is installed and on your system path: brew install chromedriver
	•	Security popups on macOS?
Approve ChromeDriver via System Settings > Privacy & Security.
	•	Tests passing but nothing happens?
Check for dryRun = true in TestRunner.java and set it to false.


## 👥 Team

This project was developed as part of the QEA Capstone New Hire Team at Cognizant
- Kyle Yoshimoto (@kyleyoshimoto)
- Kalani Dissanayake (@kalani323)
- Sidharth Amarnath (@sidamarnath)
- Namit Srivastava (@namit-s)

### 📄 License

MIT License — free to use and modify for educational purposes.
