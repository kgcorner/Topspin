Feature: Should be able to fetch all available users

  @FetchAllAvailableUserPositive
  Scenario: All available users should be returned
    When request is made for fetching all available user
    Then response should be returned with all users and status '200'