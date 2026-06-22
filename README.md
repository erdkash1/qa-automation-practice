# QA Automation Practice

A practice repository for learning Selenium WebDriver and Playwright automation testing using real-world demo sites.

## Purpose

This repo is dedicated to building hands-on QA automation skills through daily practice on industry-standard testing playgrounds.

## Tech Stack

- Java 21
- Selenium WebDriver 4.18.1
- JUnit 5
- Maven
- WebDriverManager (automatic driver management)

## Practice Sites

| Site | Focus Area |
|------|-----------|
| [the-internet.herokuapp.com](https://the-internet.herokuapp.com) | Dynamic loading, alerts, file upload/download, drag-drop |
| saucedemo.com | Login flows, e-commerce cart, checkout (coming soon) |
| automationexercise.com | Full e-commerce + REST API testing (coming soon) |
| demoqa.com | Forms, tables, sliders, date pickers (coming soon) |

## Project Structure

```
src/test/java/com/iggy/selenium/
├── theinternet/         - Tests for the-internet.herokuapp.com
├── saucedemo/           - Tests for saucedemo.com (coming soon)
├── automationexercise/  - Tests for automationexercise.com (coming soon)
└── demoqa/              - Tests for demoqa.com (coming soon)
```

## How To Run Tests

```bash
mvn test
```

## Learning Roadmap

- [x] WebDriver basics (navigation, locators, assertions)
- [ ] Explicit waits and ExpectedConditions
- [ ] Actions class (drag-drop, hover, keyboard)
- [ ] Handling multiple windows/tabs
- [ ] Handling iframes
- [ ] Handling alerts/popups
- [ ] Page Object Model (POM) architecture
- [ ] Data-driven testing
- [ ] TestNG integration
- [ ] Test reporting (ExtentReports/Allure)
- [ ] CI/CD integration with GitHub Actions
- [ ] Playwright fundamentals

## Goal

Build toward a comprehensive automated test suite using Page Object Model architecture, applying QA Automation Engineer best practices used in real-world production testing environments.

## Author

Erdenesuren Shirmen
[GitHub](https://github.com/erdkash1) | [LinkedIn](https://linkedin.com/in/erdenesuren-shirmen-dev)