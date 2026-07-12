Feature: SauceDemo Login

  Scenario: Successful login with valid credentials
    Given I am on the SauceDemo login page
    When I enter username "standard_user"
    And I enter password "secret_sauce"
    And I click the login button
    Then I should be redirected to the inventory page

  Scenario: Failed login with invalid credentials
    Given I am on the SauceDemo login page
    When I enter username "wrong_user"
    And I enter password "wrong_pass"
    And I click the login button
    Then I should see an error message containing "Epic sadface"

  Scenario Outline: Login with multiple user types
    Given I am on the SauceDemo login page
    When I enter username "<username>"
    And I enter password "<password>"
    And I click the login button
    Then I should see "<result>"

    Examples:
      | username         | password     | result       |
      | standard_user    | secret_sauce | inventory    |
      | locked_out_user  | secret_sauce | Epic sadface |
      | wrong_user       | wrong_pass   | Epic sadface |