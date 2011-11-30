require 'benchmark'

array=Array.new
numbers_in_array=1000000
max_num=1000000
for i in (0..numbers_in_array)
  array.push(rand(max_num))
end

puts "Timing:"
puts Benchmark.measure {array.sort!}
#puts "________________________________"
=begin
puts "Sorted array:"
array.each {|element|
  print element.to_s+","
  }
=end
