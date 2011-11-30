def factorial_iterative(n)
   (2..n-1).each {|i| n*= i}
   n
end

n=factorial_iterative(100)
sum=0
n.to_s.split("").each {|digit|
  sum=sum+digit.to_i}
puts "Sum of digits is: #{sum}"
