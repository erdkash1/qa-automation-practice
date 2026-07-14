Feature: SauceDemo Cart

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"

  Scenario: Add item to cart
    When I add "Sauce Labs Backpack" to cart
    Then the cart badge should show "1"

  Scenario: Add two items to cart
    When I add "Sauce Labs Backpack" to cart
    And I add "Sauce Labs Bike Light" to cart
    Then the cart badge should show "2"

  Scenario: Remove item from cart
    When I add "Sauce Labs Backpack" to cart
    And I go to the cart page
    And I remove the first item
    Then the cart should be empty