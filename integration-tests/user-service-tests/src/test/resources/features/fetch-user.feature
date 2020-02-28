Feature: Should be able to fetch existing user

  @FetchUserPositiveScenario
  Scenario: User exists and fetching that user with ID
    When request is made for fetching the user
    Then server should revert with status '200' and User details

  Scenario: User doesn't exists and fetching user with ID 0
    When request is made for fetching the user with id '0'
    Then server should revert with status '404'
