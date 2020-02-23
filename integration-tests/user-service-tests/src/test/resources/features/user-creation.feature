Feature: Should be able to create user

  Scenario: User creates profile with correct params
    When user tries to create profile with name "Gaurav", username "gaurav", email "gaurav@domain.com", contact "00000000" and other infos "Random info"
    Then user service should create the user profile and return created user with name "Gaurav", username "gaurav", email "gaurav@domain.com", contact "00000000" and other infos "Random info" and status '201'

  Scenario: User creates profile with blank name
    When user tries to create profile with name "", username "gaurav", email "gaurav@domain.com", contact "00000000" and other infos "Random info"
    Then user service should return response with status '400' as name is blank

  Scenario: User creates profile with invalid email
    When user tries to create profile with name "Gaurav", username "gaurav", email "invalid email", contact "00000000" and other infos "Random info"
    Then user service should return response with status '400' as email is invalid

  Scenario: User creates profile with blank username
    When user tries to create profile with name "Gaurav", username "", email "invalid email", contact "00000000" and other infos "Random info"
    Then user service should return response with status '400' as username is blank