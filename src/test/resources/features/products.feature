Feature: SauceDemo Products Page

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"

  Scenario: Verify products page title
    Then I should see the page title "Products"

  Scenario: Verify product count
    Then I should see 6 products

  Scenario: Verify first product name
    Then the first product should be "Sauce Labs Backpack"

  Scenario: Sort products by price low to high
    When I sort products by "Price (low to high)"
    Then the first product price should be "$7.99"