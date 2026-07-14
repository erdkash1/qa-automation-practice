Feature: SauceDemo Checkout

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"

  @checkout
  Scenario: Complete full checkout flow
    When I add "Sauce Labs Backpack" to cart
    And I go to the cart page
    And I click checkout
    And I enter first name "Iggy"
    And I enter last name "Shirmen"
    And I enter postal code "65806"
    And I click continue
    And I click finish
    Then I should see the confirmation message "Thank you for your order!"