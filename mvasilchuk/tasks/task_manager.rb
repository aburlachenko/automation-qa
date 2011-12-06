
require "#{File.dirname(__FILE__)}/classes/sorting"
require "#{File.dirname(__FILE__)}/classes/task5"



def task_manager()

puts "Choose task:\n"
puts "1- sorting\n"
puts "2 -\n"
puts "3 -\n"
puts "4 -\n"
choice = gets().chomp()
  case choice
    when "1"
      task1=Sorting.new
      task1.menu
    when "5"
      task5=Task5.new
      task5.factorial(10)
    else
      return 1
  end
end

