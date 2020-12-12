import java.text.SimpleDateFormat
import java.util.Calendar

import IO._
import MenuUserUtils._
import javax.lang.model.element.NestingKind
import sun.security.util.Password

import scala.io.StdIn.readLine
import scala.math

object  ExpenseTrackerUtils {




 def RegisterUser(username: String, email: String, lines: List[Array[String]]): Boolean = {
  lines match {
   case x :: t => if (x(0) == username) {
    usernameRegistered()
    true
   } else if (x(2) == email) {
    emailRegistered()
    true
   } else RegisterUser(username, email, t)

   case x :: Nil => if (x(0) == username) {
    usernameRegistered()
    true
   } else if (x(2) == email) {
    emailRegistered()
    true
   } else false
   case Nil => false
  }
 }


 def searchUser(email: String, password: String, lines: List[Array[String]]):Array[String]={
  (lines foldRight Array("","",""))(( v:Array[String], lines) => if(v(2) == email && v(1) == password) v else lines)
 }

 def searchUser2(email:String, password: String, lines: List[Array[String ]]): Option[String]={
   lines match{
    case Nil => None
    case x::xs => if( x(1) == password && x(2) == email) Some(x(0)) else searchUser2(email, password,xs)
   }
  }

 def checkMonth(user: UserApp): UserApp = {
  var str =Array("","")
  val format = new SimpleDateFormat("M-y")
  val date: String = format.format(Calendar.getInstance().getTime())
  val lastDeposit = user.depositList.head.date.split("-")
  val lastExpense = user.expenseList.head.date.split("-")
  val maior = maxMonth(lastDeposit, lastExpense)
  val maiorData = maior(0) + "-" + maior(1)
  println(user.monthlySavings)
  if (user.monthlySavings != List()) {
   str = user.monthlySavings.head._1.split("-")
  }
   if (date != lastDeposit && date != lastExpense && str(0)!=maior(0)) {
    val savings = monthSavings(user)(maiorData, "0")
    val tuplo = (maiorData, savings)
    val newCatSavList: List[categorySavings] = monthCategory(user.catSavList,List[categorySavings](), user, maiorData)
    val newUser = user.copy(name = user.name, user.email, user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, tuplo :: user.monthlySavings, newCatSavList)
    newUser
  } else {
   user
  }
 }

 def monthCategory(lines:List[categorySavings],value: List[categorySavings], user: UserApp, date:String):List[categorySavings]={

  lines match{
   case x::t => {
    val d =x.setValue(monthSavings(user)(date,x.category))
    println("CATEGORIA : "+ d.category + "\nVALOR:"+d.value)
    val lista:List[categorySavings] =(d::value.reverse).reverse
    monthCategory(t,lista, user, date)
   }
   case Nil => {
    for(a <- value){
     println("##### LISTA NOVA:"+a.value+"    #"+a.category)
     //Comida/0.0,Carro/0.0,Universidade/0.0,Casa/0.0,
    }
    value
   }
  }
 }

 def maxMonth(deposit: Array[String],expense: Array[String]): Array[String] ={
  if(deposit(1).toInt - expense(1).toInt > 1){
   deposit
  } else if (deposit(1).toInt - expense(1).toInt < 1){
   expense
  } else if(deposit(0).toInt - expense(0).toInt >= 1){
   deposit
   } else {
   expense
  }
 }

 def monthSavings(userApp: UserApp)( date:String, filter:String):Double={
  val listIncome :LazyList[UserList] = userApp.depositList.filter(x => x.date == date)
  val income :Double=listTotal(listIncome,filter)
  val listExpense:LazyList[UserList] = userApp.expenseList.filter(x => x.date == date)
  val expense:Double= listTotal(listExpense, filter)
  if(filter =="0")
   income-expense
  else
   expense
 }


}