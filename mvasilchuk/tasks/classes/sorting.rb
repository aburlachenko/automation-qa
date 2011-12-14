


  class Sorting

    @@flag=0   # 0 - main menu; 1 - menu wih sorting

    def menu

      case  @@flag
         when 0
             puts "Choose option: 1 - create array; 2 - quit"
             choice = gets().chomp

           case choice
            when "1"
              self.enter_numbers
            when "2"
              return 0

           end
        when 1

              puts "Choose option: 1 - create array; 2 - sorting; 3 - show input array; 4 - quit"
             choice = gets().chomp

           case choice
            when "1"
              self.enter_numbers
            when "2"
              self.sort
            when "3"
              self.show_input_array
            when "4"
              return 0
            else
               self.menu

           end


      end



    end


   def  enter_numbers

      amount=0
      @a = []


      while (amount<10)
        amount==0 ? puts("Plz enter numbers(0<=10^6). Amount should be not more than 10(Enter 'q' for finishing)") : puts("Plz enter next number or press 'q' for finishing):")
        number=gets().chomp

         if number.eql?("q")
           puts "Array is filled!"
          puts "Input Array:"

          @a.each do |i|
              print("{#{i}} ")
          end
        puts("")
          break
         end

        number=number.to_f
        if (number>0 && number<=1000000)
            @a[amount]= number
            amount+=1
        else
          puts "Number is invalid. Try with another value 0<number<1000000"
        end


        puts "\nInput Array:"

          @a.each do |i|
              print("{#{i}} ")
          end
        puts


      end
     @@flag=1
     puts "Array limit was reached!"
     self.menu

   end


    def sort
      @o=[]
      @o=@a
      puts "Choose sorting method"
      puts "1 - native ruby sorting"
      puts "2 - mysorting..."
      choice = gets.chomp
      case choice
        when "1"
          @o=@o.sort
          puts "Output Array:"

          @o.each do |i|
            print("{#{i}} ")
          end
        puts


        when "2"

          size=@o.size
          i=0

          while i<size-1
             j=1
            while j<size-i

              if @o[j-1]> @o[j]
                buff=@o[j]
                @o[j]=@o[j-1]
                @o[j-1]=buff

              end
            j+=1

            end
            i+=1

          end
           puts "Output Array:"
           @o.each do |i|
            print("{#{i}} ")
          end



      end
      @@flag=1
      self.menu


    end


    def show_input_array
         puts "Input Array:"

          @a.each do |i|
              print("{#{i}} ")
          end
         puts ""
      @@flag=1
      self.menu

    end




  end

