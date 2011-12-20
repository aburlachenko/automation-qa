
require "selenium-webdriver"
require "rspec-expectations"
require 'yaml'
require 'pathname'
require "#{File.dirname(__FILE__)}/../classes/newdriver"





# loading backend environment properties
#ENV - array of input parameters for cucumber
# if TEST_ENV=nil then TEST_ENV='config'
ENV['TEST_ENV'] ||= 'config1'

#load parameters of "config2" section from config.yml into ENV_CONFIG array
ENV_CONFIG = YAML.load_file("#{File.dirname(__FILE__)}/../../config.yml")[ENV['TEST_ENV']]

BROWSER=ENV_CONFIG['browser'].to_sym

CLOSE=ENV_CONFIG['close']
WAIT=ENV_CONFIG['wait']



case BROWSER
  when :firefox
     browser = Selenium::WebDriver.for BROWSER
     # configuring firefox profile
#profile = Selenium::WebDriver::Firefox::Profile.new
#profile['browser.download.dir'] = "#{File.dirname(__FILE__)}/../../downloads/"
#profile['browser.download.folderList'] = 2
#profile['browser.helperApps.neverAsk.saveToDisk'] = "application/zip"

  when :chrome
     browser = Selenium::WebDriver.for BROWSER

end



#set waiting timeout for searching of elements on page
browser.manage.timeouts.implicit_wait = WAIT # seconds

scenario_number=0
#before tests
Before do
 #browser is not visible in other project files
# create @browser, which is the link on "browser" object and visible

  @browser=browser
    sleep 1
 scenario_number+=1

end
#
After do |scenario|
 if(scenario.failed?)
    path=Pathname.new("#{File.dirname(__FILE__)}/../test_results")
    screenshot="#{path.realpath.to_s}/screenshot_#{scenario_number}.png"
    @browser.save_screenshot(screenshot)
    embed("screenshot_#{scenario_number}.png",'image/png')
  end


end
#
at_exit do
CLOSE==true ? browser.close : 0
end