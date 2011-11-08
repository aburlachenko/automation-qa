#!/usr/bin/ruby

ARGV.each do |str|
  str = str.squeeze()
  str2 = String.new
  str.chars.to_a.each do |ch|
    str2 << ch unless str2.include?(ch) 
  end
  puts str2
end
