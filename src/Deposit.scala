

case class Deposit(value: Double,category: String, description: String, date:String )

object Deposit {

  def getValue(deposit: Deposit): Double ={
    return deposit.value;
  }

  def setValue(deposit: Deposit, value:Double){
    new Deposit(value, deposit.category,deposit.description, deposit.date)
  }

  def getCategory (deposit: Deposit): String={
    return deposit.category
  }

  def setCategory( deposit: Deposit, category: String): Unit ={
    new Deposit(deposit.value, category, deposit.description, deposit.date)
  }

  def getDescription(deposit: Deposit): String={
    return deposit.description
  }

  def setDescription ( deposit: Deposit, description: String): Unit ={
    new Deposit(deposit.value, deposit.category, description, deposit.date)
  }

  def getDate(deposit: Deposit): String={
    return deposit.date
  }

  def setDate(deposit: Deposit, date:String): Unit ={
    new Deposit(deposit.value, deposit.category, deposit.description, date)
  }





}

