
Feature: YouTube Negative Testing

  @P6
  Scenario: Sign in with Invalid Password
    Given I am on a YouTube Video Page in the Chrome Browser
    And I am not signed in
    When I sign in with email <email> and password <password>
    Then I should recieve a <warning type>

  @P7
  Scenario: Sign in with Invalid Email
    Given I am on a YouTube Video Page in the Chrome Browser
    And I am not signed in
    When I sign in with email <email> and password <password>
    Then I should recieve a <warning type>