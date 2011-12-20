
class Textlabel

  def initialize (text)
  @xpath ="//blockquote[contains(text(),'#{text}')] | //p[contains(text(),'#{text}')] |  //h2[contains(text(),'#{text}')] |  //span[contains(text(),'#{text}')]"
  @css ="p:contains(\"Click here\")"


  end

  def find(how = :xpath)

    how=how.to_sym
    case how
      when :css
             $browser.find_element(how,@css)    #TODO
      when :xpath
             $browser.find_element(how,@xpath)
    end


  end



end