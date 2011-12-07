
class Task5





def factorial n
  n > 1 ? n * factorial(n - 1) : 1

end

def size(number)
  n=0
  while(number>0)
    n+=1
    number=number/10
  end
  return n


end

def get_digits(number)

    size=size(number)
     i=0
    a=[]
  while (i<size)
    a[i]=+number%10
    number=number/10
    i+=1


  end
    puts a.inspect
    return a



end


  def sum_digits(number)

    a=get_digits(number)
    sum=0
    a.each do |i|
    sum +=i

    end
    puts sum


  end





end