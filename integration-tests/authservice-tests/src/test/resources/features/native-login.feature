@NativeLogin
Feature: Native Login
  Scenario: User tries to login with valid credential
    Given User exists with user name "user" and password "password"
    Then Service should revert with a token object and status '200'