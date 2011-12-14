#require "rubygems"
require "selenium-webdriver"
require "rspec-expectations"
require 'yaml'
#require "#{File.dirname(__FILE__)}/../support/env.rb"



class Page

  def initialize browser
   @browser=browser

  end

  def open page

  @browser.get("http://#{page}")

  end

  def click(what, how)
    case how
      when "button"
        @browser.find_element(:value,"#{what}")

    end

  end

  def fill(name,text)

      @browser.find_element(:name,"#{name}").send_keys("#{text}")

  end

  def find_link(text)
       puts text.inspect
 #     text= /(.*)<em>(.*)<\/em>(.*)/.match text



         @browser.find_element(:xpath,"//a[text()=#{text}]")

  end
  def find_cite(text)
    how=:xpath
    case how
      when :xpath
        @browser.find_element(:xpath,"//cite[text()='#{text}']")
    end

  end



end