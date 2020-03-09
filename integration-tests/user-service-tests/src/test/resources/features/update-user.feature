Feature: A logged in user can update its details

  Scenario: User is logged in and updates details
    Given User exists with username "gaurav" and password "password"
    When user tries to change his email to "changed@email.com"
    Then User should be able to see email changed to "changed@email.com" when user detail is fetched

  Scenario: User is not logged in and updates details
    Given User exists with username "gaurav1" and password "password"
    When user tries to change his email to "changed@email.com" without logging in
    Then service should respond with status '403'