


class Expense(xid:String,xvalue: Double, xcategory: String, xdescription:String, xdate:String) extends UserList {
  override val id: String = xid
  override  val value :Double = xvalue
  override  val category :String = xcategory
  override  val description: String = xdescription
  override  val date :String = xdate

  def getValue(expense: Expense): Double ={
    return expense.value;
  }

  def setValue(expense: Expense, value:Double){
    new Expense(value, expense.category,expense.description, expense.date)
  }

  def getCategory (expense: Expense): String={
    return expense.category
  }

  def setCategory( expense: Expense, category: String): Unit ={
    new Expense(expense.value, category, expense.description, expense.date)
  }

  def getDescription(expense: Expense): String={
    return expense.description
  }

  def setDescription ( expense: Expense, description: String): Unit ={
    new Expense(expense.value, expense.category, description, expense.date)
  }

  def getDate(expense: Expense): String={
    return expense.date
  }

  def setDate(expense: Expense, date:String): Unit ={
    new Expense(expense.value, expense.category, expense.description, date)
  }




}
