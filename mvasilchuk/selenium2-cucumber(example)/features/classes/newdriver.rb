
require "rubygems"
require "selenium-webdriver"


class NewDriver < Selenium::WebDriver::Driver


  def find_element(how,what)
   tries=0
    begin

   super(how,what)
    rescue
      tries+=1
      sleep 1

      if tries <=2
      puts "#{tries} try"
      retry
      end


     # @browser.find_element(how, what)
    end
  end


end