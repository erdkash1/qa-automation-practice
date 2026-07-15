# QA Automation Practice

![QA Tests](https://github.com/erdkash1/qa-automation-practice/actions/workflows/test.yml/badge.svg)

A comprehensive QA automation portfolio built with Selenium WebDriver, Playwright, RestAssured, and Cucumber/BDD — demonstrating real-world test automation skills used by QA Engineers and SDETs at enterprise companies.

---

## Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 21 | Primary language |
| Selenium WebDriver | 4.18.1 | UI automation |
| Playwright | 1.44.0 | Modern UI automation |
| JUnit 5 | 5.10.2 | Test framework |
| TestNG | 7.9.0 | Enterprise test framework |
| RestAssured | 5.4.0 | API testing |
| Cucumber | 7.15.0 | BDD framework |
| AssertJ | 3.25.3 | Fluent assertions |
| Allure Reports | 2.27.0 | Test reporting |
| Maven | 3.x | Build tool |
| GitHub Actions | — | CI/CD |

---

## Test Suite — 70 Tests Total

| Framework | Tests | Sites / APIs |
|-----------|-------|-------------|
| Selenium WebDriver | 42 | the-internet.herokuapp.com, saucedemo.com |
| Playwright | 5 | saucedemo.com |
| RestAssured | 10 | JSONPlaceholder API, Live Ecommerce API |
| Cucumber/BDD | 13 | saucedemo.com |
| **Total** | **70** | |

---

## Practice Sites

| Site | Focus Area |
|------|-----------|
| [the-internet.herokuapp.com](https://the-internet.herokuapp.com) | Alerts, iFrames, Windows, Drag & Drop, Hover, File Upload, Dynamic Loading |
| [saucedemo.com](https://saucedemo.com) | Login, Products, Cart, Checkout, Sort Dropdown |
| [jsonplaceholder.typicode.com](https://jsonplaceholder.typicode.com) | REST API testing |
| [ecommerce-api-e24i.onrender.com](https://ecommerce-api-e24i.onrender.com/swagger-ui/index.html) | Live Ecommerce API with JWT auth |

---

## Skills Demonstrated

### Selenium WebDriver
- ✅ Locator strategies (id, cssSelector, xpath, linkText, data-test)
- ✅ Explicit waits (WebDriverWait + ExpectedConditions)
- ✅ Fluent Wait with custom polling interval
- ✅ Page Object Model (POM)
- ✅ Page Factory pattern (@FindBy annotations)
- ✅ findElements — list handling with .get(index)
- ✅ Select class — dropdown handling
- ✅ JavaScript Alerts — switchTo().alert(), accept(), dismiss()
- ✅ Multiple Windows — getWindowHandles(), switchTo().window()
- ✅ iFrames — switchTo().frame(), defaultContent()
- ✅ JavascriptExecutor — executing JS directly on elements
- ✅ Actions class — hover (moveToElement), drag and drop
- ✅ Dynamic loading — visibilityOfElementLocated vs presenceOfElementLocated
- ✅ File upload testing
- ✅ Screenshot on failure (TestExecutionExceptionHandler)
- ✅ Soft Assertions (AssertJ SoftAssertions)

### Testing Frameworks
- ✅ JUnit 5 — @ParameterizedTest, @CsvSource, @Tag, @Order
- ✅ TestNG — @BeforeMethod, @AfterMethod, @DataProvider
- ✅ Cucumber/BDD — Feature files, Step definitions, Scenario Outline
- ✅ Shared context with PicoContainer dependency injection

### API Testing
- ✅ RestAssured — GET, POST, PUT, DELETE
- ✅ JWT token extraction and reuse
- ✅ Response body assertions
- ✅ CRUD flow testing against live API

### Playwright
- ✅ Browser/Page lifecycle management
- ✅ Auto-wait built-in (no explicit waits needed)
- ✅ Page Object Model in Playwright
- ✅ Built-in assertions (assertThat)
- ✅ URL and element assertions

### Reporting & CI/CD
- ✅ Allure Reports — @Epic, @Feature, @Story, @Severity
- ✅ Screenshot automatically saved on test failure
- ✅ Flaky test management (@Tag + excludedGroups)
- ✅ GitHub Actions CI/CD — auto-run on every push

---

## Project Structure

```
src/test/java/com/iggy/
├── selenium/
│   ├── theinternet/
│   │   ├── fileupload/          - File upload tests
│   │   ├── hover/               - Hover action tests
│   │   ├── iframe/              - iFrame tests
│   │   ├── multiple/            - Multiple windows tests
│   │   ├── draganddrop/         - Drag and drop tests
│   │   ├── LoginTests.java
│   │   ├── CheckboxesTests.java
│   │   ├── DynamicLoadingTests.java
│   │   └── JavaScriptAlertsTests.java
│   ├── saucedemo/
│   │   ├── SauceDemoLoginTests.java
│   │   ├── SauceDemoLoginParameterizedTests.java
│   │   ├── SauceDemoProductsTests.java
│   │   ├── SauceDemoCartTests.java
│   │   └── SauceDemoCheckoutTests.java
│   ├── pagefactory/             - Page Factory pattern tests
│   └── testng/                  - TestNG tests
├── playwright/
│   ├── SauceDemoLoginPlaywrightTests.java
│   ├── SauceDemoLoginPagePlaywright.java
│   └── SauceDemoLoginPagePlaywrightTests.java
├── cucumber/
│   ├── CucumberRunner.java
│   └── steps/
│       ├── SharedContext.java
│       ├── LoginSteps.java
│       ├── ProductsSteps.java
│       ├── CartSteps.java
│       └── CheckoutSteps.java
├── api/
│   ├── ProductApiTests.java     - JSONPlaceholder API tests
│   └── ProductApiTests2.java    - Live Ecommerce API tests
└── utils/
    └── ScreenshotOnFailureExtension.java

src/test/resources/
├── features/
│   ├── login.feature
│   ├── products.feature
│   ├── cart.feature
│   └── checkout.feature
└── test-upload.txt
```

---

## How To Run

```bash
# Run all stable tests
mvn test

# Run specific test class
mvn test -Dtest=SauceDemoLoginTests

# Run Cucumber scenarios only
mvn test -Dtest=CucumberRunner

# Run TestNG suite
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Run flaky tests (checkout)
mvn test -Dgroups=flaky

# Generate and view Allure report
mvn test
allure serve target/allure-results
```

---

## CI/CD

Every push to `main` automatically:
1. Sets up Java 21
2. Installs Playwright Chromium
3. Runs all stable tests
4. Uploads Allure results as artifact
5. Uploads Surefire reports as artifact

View workflow runs in the [Actions tab](https://github.com/erdkash1/qa-automation-practice/actions).

---

## Author

**Erdenesuren Shirmen**  
CS Graduate — Missouri State University (2026)  
Targeting: QA Automation Engineer / SDET / Backend Java Developer  
OPT (STEM eligible — 3 years)

[GitHub](https://github.com/erdkash1) | [LinkedIn](https://linkedin.com/in/erdenesuren-shirmen-dev)