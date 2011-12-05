
require "#{File.dirname(__FILE__)}/classes/sorting"



def task_manager(task)

  case
    when 1
      task1=Sorting.new
      task1.menu
    when 2
    else
      return 1
  end
end

