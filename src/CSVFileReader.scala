import scala.io.Source
import java.io._

object CSVFileReader{

 def writeFile(file: String, to_write: String,append: Boolean): Unit ={
  if(append==true){
   val pw = new PrintWriter(new FileWriter(file,true))
   pw.write(to_write)
   pw.close
  } else {
   val pw = new PrintWriter(new FileWriter(file,false))
   pw.write(to_write)
   pw.close
  }
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

 def readUser(file:String,usernameToWrite:String):UserApp={
  val bufferedSource = io.Source.fromFile(file)
  var lines = List[String]()
  for (line <- bufferedSource.getLines) {
   lines = lines:+ line
  }
  val username:String = lines(0)

  if(username=="newUser"){
   val userAppr=UserApp(usernameToWrite,0,LazyList[Deposit](),LazyList[Expense]())
   userAppr
  } else {
   val balance:Double = lines(1).toDouble
   val deposits:LazyList[Deposit] = lineToDepositList(lines(2))
   val expenses:LazyList[Expense] = lineToExpenseList(lines(3))
   val user:UserApp = UserApp(username,balance,deposits,expenses)
   user
  }
 }

 def lineToExpenseList(line:String):LazyList[Expense]={
  var expenseList:LazyList[Expense] = LazyList[Expense]()
  if(line==""){
   expenseList
  } else {
   val expenses = line.split(",")
   for(expense <- expenses){
    val parameters= expense.split("/")
    var aux:Expense = Expense(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
    expenseList = aux+:expenseList
   }
   expenseList
  }
 }


 def lineToDepositList(line:String):LazyList[Deposit]={
  var depositList:LazyList[Deposit] = LazyList[Deposit]()
  if(line==""){
   depositList
  } else {
   val deposits = line.split(",")
   for(deposit <- deposits){
    val parameters= deposit.split("/")
    var aux:Deposit = Deposit(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
    depositList = aux+:depositList
   }
   depositList
  }
 }

 def saveUser(user:UserApp): Unit ={
  val deposits:String = depositsToString(user.depositList,"")
  val expenses:String = expensesToString(user.expenseList,"")
  val s:String = user.name+"\n"+user.balance+"\n"+deposits+"\n"+expenses+"\n"
  writeFile("CSVFiles/"+user.name+".csv",s,false)
 }

 def depositsToString(list: LazyList[Deposit],s:String):String={
  list match{
   case x#::t =>{
    val newS:String =x.value.toString+"/"+x.category+"/"+x.description+"/"+x.date+","+s
    println("FIRST CASE: "+ newS)
    depositsToString(t,newS)
   }
   case LazyList() =>s
  }
 }

 def expensesToString(list: LazyList[Expense],s:String):String= {
  list match {
   case x #:: t => {
    val newS: String = x.value.toString + "/" + x.category + "/" + x.description + "/" + x.date + "," + s
    println("FIRST CASE: " + newS)
    expensesToString(t, newS)
   }
   case LazyList() => s
  }
 }


}