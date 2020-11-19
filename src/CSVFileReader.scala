import scala.io.Source
import java.io._

object CSVFileReader{

 def writeFile(file: String, to_write: String): Unit ={
  val pw = new PrintWriter(new FileWriter(file,true))
  pw.write(to_write)
  pw.close
 }

 def readFile(file: String): List[Array[String]] = {
  var newList = List[Array[String]]()
  val bufferedSource = io.Source.fromFile(file)
  for (line <- bufferedSource.getLines) {
   val cols = line.split(",")
   newList = newList:+ cols
   println(s"${cols(0)}|${cols(1)}|${cols(2)}")
  }
  bufferedSource.close
  newList
 }

 def readUser(file:String):UserApp={
  val bufferedSource = io.Source.fromFile(file)
  var lines = List[String]()
  for (line <- bufferedSource.getLines) {
   lines = lines:+ line
  }
  val username:String = lines(0)
  val balance:Double = lines(1).toDouble
  val deposits:LazyList[Deposit] = lineToDepositList(lines(2))
  val expenses:LazyList[Expense] = lineToExpenseList(lines(3))
  val user:UserApp = UserApp(username,balance,deposits,expenses)
  user
 }

 def lineToExpenseList(line:String):LazyList[Expense]={
  val expenses = line.split(",")
  var expenseList:LazyList[Expense] = LazyList[Expense]()
  for(expense <- expenses){
   val parameters= expense.split("/")
   var aux:Expense = Expense(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
   expenseList = aux+:expenseList
  }
  expenseList
 }


 def lineToDepositList(line:String):LazyList[Deposit]={
  val deposits = line.split(",")
  var depositList:LazyList[Deposit] = LazyList[Deposit]()
  for(deposit <- deposits){
   val parameters= deposit.split("/")
   var aux:Deposit = Deposit(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
   depositList = aux+:depositList
  }
  depositList
 }
}