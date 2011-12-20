Feature: Searching

  I wanna search some information and check result


Scenario: search

  Given I am on "google.com.ua" page
  When I enter "cucumber" text in the "q" field
#  And I click "Пошук Google" button
  Then I should see "cukes.info/" cite


Scenario: scenario should fail

  Given I am on "google.com.ua" page
  When I enter "cucumber" text in the "q" field
#  And I click "Пошук Google" button
  Then I should see "kukurukuk" cite
