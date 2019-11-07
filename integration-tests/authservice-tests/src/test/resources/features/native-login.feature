@NativeLogin
Feature: Native Login

  Scenario Outline: User tries to login with valid credential
    Given User exists with user name "<user>" and password "<password>"
    Then Service should revert with a token object and status '<status>'
    Examples:
      | user    | status | password   |
      | user    | 200    |  password  |
      | user1   | 403    |  password1 |

  Scenario: User tries to get authenticated using bearer token
    Given User exists with user name "user" and password "password"
    Then User logs in in the system to obtain an access token
    Then User tries to validate that given access token
    Then server should revert the token with status '200'

  Scenario: User tries to get authenticated using invalid bearer token
    Given User tries to validate invalid bearer token
    Then server should revert the token with status '403'

