
import java.text.SimpleDateFormat
import java.util.Calendar

import IO._

import scala.::
import scala.io.StdIn.readLine

object MenuUserUtils {

  def transaction(user:UserApp, userList: LazyList[UserList], tipo:Int) :UserApp = {
    try {
      muchTransaction(userList)
      val newTransactionValue: Double = roundAt(getUserInput().toDouble)
      description()
      val newDescription: String = getUserInput()
      val format = new SimpleDateFormat("d-M-y H:m")
      val sameDate:String = format.format(Calendar.getInstance().getTime())
      val sameCategory = defineCategory(user)
      val newTransaction: UserList = new UserList {
        override val value: Double = newTransactionValue
        override val category: String = sameCategory
        override val description: String = newDescription
        override val date: String = sameDate
      }
      val newTransactionList : LazyList[UserList]= newTransaction#::userList
      if (tipo == 1) {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance + newTransactionValue, depositList = newTransactionList, expenseList = user.expenseList, userCategories = user.userCategories)
        }
        newUserApp
      }
      else {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance - newTransactionValue, depositList = user.depositList, expenseList = newTransactionList, userCategories = user.userCategories)
        }
        newUserApp
      }

    }
    catch{
      case ex: NumberFormatException => {
        error()
        transaction(user, userList,tipo)

      }
    }
  }


  def higherfunction(x :UserApp, f: (LazyList[UserList], String) => Unit): Unit ={
    showOptions()
    val userOption :String  = getUserInput()
    showFilters(x.userCategories,1)
    val filter :Int = getUserInput().toInt
    userOption match {
      case "1" => {
        if (filter!=0)
          f( x.depositList,x.userCategories(filter-1))
        else
          f(x.depositList, "0")
      }
      case "2" =>{
        if(filter!=0)
          f( x.expenseList,x.userCategories(filter-1))
        else
          f(x.expenseList,"0")
      }
    }
  }

  //opcao 6 do user
  def listTotal(list : LazyList[UserList], filter :String): Unit = {
    val total :Double = (list foldLeft 0.0)(( v1 :Double, v2:UserList) => if(v2.category == filter) v1+v2.value else v1    )
    printValue(total)
  }

  //opcao 4 do user
  def showHistory(list :LazyList[UserList], filter:String): Unit = {
    list match{
      case x #::t => {
        if( filter == "0") {
          printValue(x.value)
          showHistory(t, filter)
        }else if(x.category == filter){
          printValue(x.value)
          showHistory(t,filter)
        }
      }
      case LazyList() =>
    }
  }


  //show history


  def showFilters(list: List[String],aux:Int): Unit ={
    list match {
      case x :: t => printValue(aux + "-" +x ); showFilters(t,aux+1)
      case x :: Nil => printValue(aux+"-"+x)
      case Nil => semFiltro()
    }

  }


  def addCategory(user: UserApp,s:String): UserApp ={
    val list:List[String] = s::user.userCategories
    val newUserApp = {
      user.copy(name = user.name, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, list)
    }
    newUserApp
  }

  //define category of income/expense
  def defineCategory(userApp: UserApp): String ={
    try {
      whatCategory()
      showCategories(userApp.userCategories,1)

      val input = getUserInput().toInt
      val cat:String = userApp.userCategories(input-1)
      cat
    } catch {
      case ex: MatchError => {
        error()
        defineCategory(userApp)
      }
    }
  }

  //show categories available
  def showCategories(list: List[String], aux :Int): Unit ={
    list match {
      case x :: t => printValue(aux + "-" +x ); showCategories(t,aux+1)
      case x :: Nil => printValue(aux+"-"+x)
      case Nil =>
    }
  }







  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
