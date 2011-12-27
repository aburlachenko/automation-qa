require "rubygems"
require "selenium-webdriver"
require "test/unit"

class CarrersTestUnit < Test::Unit::TestCase

  def setup
    @driver = Selenium::WebDriver.for :firefox
    @driver.manage.timeouts.implicit_wait = 30
    @verification_errors = []
  end

  def teardown
    @driver.quit
    assert_equal [], @verification_errors
  end

  def test_carrers_test_unit
    count = 1
    while count<=10
      @driver.get "http://qa01-mgage.cogniance.com/azakordonets/andrew_index.html"
      @driver.find_element(:title, "Yandex").click
    end

  end

  def element_present?(how, what)
    @driver.find_element(how, what)
    true
  rescue Selenium::WebDriver::Error::NoSuchElementError
    false
  end

  def verify(&blk)
    yield
  rescue Test::Unit::AssertionFailedError => ex
    @verification_errors << ex
  end
end