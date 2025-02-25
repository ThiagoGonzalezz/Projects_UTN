
class ExceptionNoSuite < StandardError
  def initialize(msg="La clase que se esta intentando testear no es un Suite")
    super(msg)
  end
end
