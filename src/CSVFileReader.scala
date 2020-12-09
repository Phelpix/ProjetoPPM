import scala.io.Source
import java.io._

import CSVFileReader.changePassword
import ExpenseTrackerUtils.checkMonth

object CSVFileReader{
 val credentialsFile = "CSVFiles/UserCredentials.csv"

 def writeFile(file: String, to_write: String,append: Boolean): Unit ={
  println("WRITE FILE")
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
  }
  bufferedSource.close
  newList
 }

 def readUser(file:String,usernameToWrite:String,email:String,password:String):UserApp={
  val bufferedSource = io.Source.fromFile(file)
  var lines = List[String]()
  for (line <- bufferedSource.getLines) {
   lines = lines:+ line
  }
  val username:String = lines(0)

  if(username=="newUser"){
   val userAppr=UserApp(usernameToWrite,email,password,0,LazyList[Deposit](),LazyList[Expense](),List[String]("Comida","Carro","Universidade","Casa"),List[(String,Double)]() )
   userAppr
  } else {
   val balance:Double = lines(1).toDouble
   val deposits:LazyList[UserList] = lineToTransaction(lines(2))
   val expenses:LazyList[UserList] = lineToTransaction(lines(3))
   val categories:List[String] = linetoCategoryList(lines(4))
   val savings:List[(String,Double)] = linesToMonthlySavings(lines(5))
   val user:UserApp = UserApp(username,email,password,balance,deposits,expenses,categories,savings)
   val newUser =checkMonth(user)
   newUser
  }
 }

 def linesToMonthlySavings(line:String):List[(String,Double)]={
  var montlyList:List[(String,Double)] = List[(String,Double)]()
  if(line==""){
   println("VAZIO")
   montlyList
  } else {
   val tuplos = line.split(",").toList
   for(tuplo <- tuplos){
    val parameters= tuplo.split("/")
    //var aux:Expense = new Expense(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
    println(parameters(0))
    println(parameters(1).toDouble)
    var aux:(String,Double) = (parameters(0),parameters(1).toDouble)
    println("AUX: "+ aux._1 + "," + aux._2)
    montlyList = aux+:montlyList
   }
   montlyList
  }

 }

 def linetoCategoryList(line:String):List[String]={
  if(line==""){
   val categoryList:List[String] = List[String]()
   categoryList
  } else {
   line.split(",").toList
  }
 }


 def lineToTransaction(line:String):LazyList[UserList]={
  var expenseList:LazyList[UserList] = LazyList[UserList]()
  if(line==""){
   expenseList
  } else {
   lazy val expenses = line.split(",")
   for(expense <- expenses){
    val parameters= expense.split("/")
    //var aux:Expense = new Expense(parameters(0).toDouble,parameters(1),parameters(2),parameters(3))
    var aux:UserList = new UserList {
     override val value: Double = parameters(0).toDouble
     override val category: String = parameters(1)
     override val description: String = parameters(2)
     override val date: String = parameters(3)
    }
    expenseList = aux+:expenseList
   }
   expenseList
  }
 }

 def saveUser(user:UserApp): Unit ={
  val deposits:String = transactionToString(user.depositList,"")
  val expenses:String = transactionToString(user.expenseList,"")
  val categories:String = categoriesToString(user.userCategories)
  val savings:String = savingsToString(user.monthlySavings)
  println("DEBUG: "+ categories)
  val s:String = user.name+"\n"+user.balance+"\n"+deposits+"\n"+expenses+"\n" + categories+"\n" + savings+"\n"
  writeFile("CSVFiles/"+user.name+".csv",s,false)
 }

 def savingsToString(list: List[(String, Double)]): String ={
  list match {
   case x::t=> x._1 + "/" + x._2 + "," + savingsToString(t)
   case x::Nil=> x._1+"/"+x._2
   case Nil=> ""
  }

 }

 def categoriesToString(list:List[String]):String={
  list match {
   case x::t => x +"," + categoriesToString(t)
   case x::Nil=> x
   case Nil => ""
  }
 }

 def transactionToString(list: LazyList[UserList],s:String):String={
  list match{
   case x#::t =>{
    lazy val newS:String =x.value.toString+"/"+x.category+"/"+x.description+"/"+x.date+","+s
    transactionToString(t,newS)
   }
   case LazyList() =>s
  }
 }

 def changeUsername(user: UserApp,newUserName:String,lines:List[Array[String]],text:String): Unit ={
  lines match {
   case x::t => if( x(0)==user.name ){
    val newText = text+ newUserName + ","+x(1)+","+x(2)+"\n"
    changeUsername(user,newUserName,t,newText)
   } else {
    val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
    changeUsername(user,newUserName,t,newText)
   }

   case x::Nil=> if( x(0)==user.name ){
    val newText = text+newUserName + ","+x(1)+","+x(2)+"\n"
    changeUsername(user,newUserName,Nil,newText)
   } else {
    val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
    changeUsername(user,newUserName,Nil,newText)
   }
   case Nil=> writeFile(credentialsFile,text,false)
  }
 }

 def changePassword(user: UserApp, newPassword:String, lines:List[Array[String]], text:String): Unit = {
  lines match {
   case x :: t => if (x(0) == user.name) {
    val newText = text + x(0) + "," + newPassword + "," + x(2) + "\n"
    changePassword(user, newPassword, t, newText)
   } else {
    val newText = text + x(0) + "," + x(1) + "," + x(2) + "\n"
    changePassword(user, newPassword, t, newText)
   }

   case x :: Nil => if (x(0) == user.name) {
    val newText = text + x(0) + "," + newPassword + "," + x(2) + "\n"
    changePassword(user, newPassword, Nil, newText)
   } else {
    val newText = text + x(0) + "," + x(1) + "," + x(2) + "\n"
    changePassword(user, newPassword, Nil, newText)
   }
   case Nil => writeFile(credentialsFile, text, false)
  }
 }

  def changeEmail(user: UserApp, newEmail:String, lines:List[Array[String]], text:String): Unit ={
   lines match {
    case x::t => if( x(0)==user.name ){
     val newText = text+ x(0)+ ","+x(1)+","+newEmail+"\n"
     changeEmail(user,newEmail,t,newText)
    } else {
     val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
     changeEmail(user,newEmail,t,newText)
    }

    case x::Nil=> if( x(0)==user.name ){
     val newText = text+x(0)+ ","+x(1)+","+newEmail+"\n"
     changeEmail(user,newEmail,Nil,newText)
    } else {
     val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
     changeEmail(user,newEmail,Nil,newText)
    }
    case Nil=> writeFile(credentialsFile,text,false)
   }
  }
}