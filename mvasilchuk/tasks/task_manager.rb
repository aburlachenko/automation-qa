
require "#{File.dirname(__FILE__)}/classes/sorting"
require "#{File.dirname(__FILE__)}/classes/task5"
require "#{File.dirname(__FILE__)}/classes/binder"



def task_manager()

puts "Choose task:\n"
puts "1- sorting\n"
puts "2 -\n"
puts "3 -\n"
puts "4 -\n"
puts "5 - digits sum\n"
puts ""
choice = gets().chomp()
  case choice
    when "1"
      task1=Sorting.new
      task1.menu
    when "2"
      puts "TDB:"
    when "3"
      puts "TDB:"
    when "4"
    puts "TDB:"
    when "5"
      task5=Task5.new
      fac= task5.factorial(10)
      task5.sum_digits(fac)
     when "6"
      task6 =Task6.new
      res=task6.p(20,1000000)
      puts res.class

    else
      return 1
  end
end

