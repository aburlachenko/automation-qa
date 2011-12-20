
require "#{File.dirname(__FILE__)}/../classes/page"

###########
# Actions #
###########



#When /^I open "([^\"]*)" page$/ do |arg1|
# MainPage.new
#end



When /^I enter "([^\"]*)" text in the "(.*)" field$/ do |text,name|

Page.new(@browser).fill(name,text)


end

When /^I click "([^\"]*)" button$/ do |what|
   Page.new(@browser).click("button",what)

end

##############
# Assertions #
##############



Then /^I should see "([^\"]*)" cite$/ do |text|
  Page.new(@browser).find_cite(text)
end

Given /^I (am|am not) on "([^\"]*)*" page$/ do |action,page|

Page.new(@browser).open(page)

page_url = "http://www.#{page}/"
  case action
     when "am"
       @browser.current_url.should == "#{page_url}"
     when "am not"
       @browser.current_url.should != "#{page_url}"
  end

end