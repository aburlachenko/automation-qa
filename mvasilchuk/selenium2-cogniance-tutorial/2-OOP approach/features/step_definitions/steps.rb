Given /^I just test my env\.rb file$/ do
  puts "If you have seen opened browser, env.rb works"
end
When /^I go to "([^\"]*)"$/ do |url|

  $browser.get("http://www.#{url}")
end
When /^I click "([^\"]*)" link$/ do |name|
  $browser.find_element(:link, "#{name}").click

end

  Then /^I should see "(.*)" text$/ do |text|
 # @browser.find_element(:xpath,"//*[contains(text(),'#{text}')]")
    Textlabel.new(text).find()

 end
