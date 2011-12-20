require "selenium-webdriver"


browser = Selenium::WebDriver.for :firefox

#set waiting timeout for searching of elements on page
browser.manage.timeouts.implicit_wait = 10 # seconds

Before do
   $browser=browser
  #$ - pointing, that variable is accessible from any class, part of project
  # instead of @, pointing, variable should be invoke by using constructor of class: myclass.new(@browser)

end