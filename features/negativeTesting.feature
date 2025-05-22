
Feature: YouTube Negative Testing

  @P7
  Scenario: Sign in with Invalid Email
    Given I am on a YouTube Video Page in the Chrome Browser
    And I am not signed in
    When I sign in with email "<email>"
    Then I should see an error message
    Examples: 
    | email                           |
    | @2                              |
    | abcdefg@4e3r8voqnkd5djzg6iv910  |
    | ??????????@!!!!!!!!!!!!!!!.com  |
    | ???????@yahoo.!!!               |
