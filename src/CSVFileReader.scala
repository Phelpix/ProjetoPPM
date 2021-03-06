import scala.io.Source
import java.io._

import CSVFileReader.{changePassword, linesToCatSavings}
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
   val c: List[categorySavings]=linesToCatSavings(lines(6))
   val userAppr=UserApp(usernameToWrite,email,password,0,LazyList[Deposit](),LazyList[Expense](),List[String]("Comida","Carro","Universidade","Casa"),List[(String,Double)](),c,new PlanSoft(0,List[categorySavings]()))
   userAppr
  } else {
   val balance:Double = lines(1).toDouble
   val deposits:LazyList[UserList] = lineToTransaction(lines(2))
   val expenses:LazyList[UserList] = lineToTransaction(lines(3))
   val categories:List[String] = linetoCategoryList(lines(4))
   val savings:List[(String,Double)] = linesToMonthlySavings(lines(5))
   val catSavings:List[categorySavings] = linesToCatSavings(lines(6))
   val plan :PlanSoft= linesToPlan(lines(7))
   val user:UserApp = UserApp(username,email,password,balance,deposits,expenses,categories,savings,catSavings,plan)
   val newUser =checkMonth(user)
   newUser
  }
 }

 def linesToPlan(line:String):PlanSoft={
  if(line==""||line=="0:")new PlanSoft(0,List[categorySavings]())
  else{
   val plan = line.split(":").toList
   val tipo:Int = plan(0).toInt
   val list:List[categorySavings] = checkString(plan(1))
   val planSoft= new PlanSoft(tipo,list)
   planSoft
  }

 }

 def checkString(f:String): List[categorySavings] ={
  if(f=="")List[categorySavings]()
  else linesToCatSavings(f)
 }

 def linesToCatSavings(line:String):List[categorySavings]= {
  println(line)
  var catSavings: List[categorySavings] = List[categorySavings]()
  if (line == "") {
   println("VAZIO")
   catSavings
  } else {
   var savings = line.split(",")
   println("SAVINGS:    "+ savings)
   for (x <- savings) {
    println("X:   "+x)
    var parameters = x.split("/")
    var aux: categorySavings = new categorySavings(parameters(0), parameters(1).toDouble)
    catSavings = aux +: catSavings
   }
   catSavings
  }
 }

 def linesToMonthlySavings(line:String):List[(String,Double)]={
  var montlyList:List[(String,Double)] = List[(String,Double)]()
  if(line==""){
   println("VAZIO")
   montlyList
  } else {
   val tuplos = line.split(",")
   for(tuplo <- tuplos){
    val parameters= tuplo.split("/")
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
     override val id: String = parameters(0)
     override val value: Double = parameters(1).toDouble
     override val category: String = parameters(2)
     override val description: String = parameters(3)
     override val date: String = parameters(4)
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
  val catSavings:String = catSavToString(user.catSavList)
  val plan:String = planToString(user.plan)
  val s:String = user.name+"\n"+user.balance+"\n"+deposits+"\n"+expenses+"\n" + categories+"\n" + savings+"\n"+catSavings+"\n"+plan+"\n"
  writeFile("CSVFiles/"+user.name+".csv",s,false)
 }

 def savingsToString(list: List[(String, Double)]): String ={
  list match {
   case x::t=> x._1 + "/" + x._2 + "," + savingsToString(t)
   case x::Nil=> x._1+"/"+x._2
   case Nil=> ""
  }

 }

 def planToString(planSoft: PlanSoft):String={
  val str:String = planSoft.tipo.toString + ":" + catSavToString(planSoft.list)
  str
 }

 def catSavToString(value: List[categorySavings]):String={
  value match{
   case x::t=> x.category +"/" + x.value +","+catSavToString(t)
   case x::Nil => x.category +"/"+x.value
   case Nil=>""
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
    lazy val newS:String =x.id+"/"+x.value.toString+"/"+x.category+"/"+x.description+"/"+x.date+","+s
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
  println("CHANGE PASSWORD: " + user.name +" " + user.email+ " " + user.password)
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
   println("CHANGE EMAIL: " + user.name +" " + user.email+ " " + user.password)
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
    case Nil=>{
     writeFile(credentialsFile,text,false)
     println("TEXT:" +text)
    }

   }
  }
}