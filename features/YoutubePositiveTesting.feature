
Feature: Youtube Home Page Scenarios

  @P1
  Scenario: Search For Cucumber Tests on YouTube
    Given I am on the YouTube home page
    When I search for Cucumber Tests
    Then I should find a link for Introduction to Cucumber

  @P2
  Scenario: Click on Video Link and Verify Date Posted
    Given I am on the Cucumber Tests search results page
    When I click on the link for the video
    Then I should see brought to the video page where it shows the date posted as May 14, 2017

  @P3
  Scenario: Click on Share Button and Verify Embed Code
    Given I am on the Cucumber Tests video page
    When I click on the share button
    And I click on the Embed button
    Then I should see a window with the following HTML code
      |<iframe width="560" height="315" src="https://www.youtube.com/embed/lC0jzd8sGIA?si=Cc0L_ckJLsh8vcg5" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe> |

  @P4
  Scenario: Close Modal and Sort Comments by Newest First
    Given I am on the embed share modal page
    When I close the modal to get back to the main video page
    And sort the comments by Newest First
    Then I should see the most recent comment posted

  @P5
  Scenario: Sign in with valid credentials
    Given I am on a YouTube video page in the Chrome browser
    And I am not signed in
    When I sign in with email<...>\@gmail.com and password <...>
    Then I should be signed in