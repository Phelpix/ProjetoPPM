
import java.text.SimpleDateFormat
import java.util.Calendar

import scala.::
import scala.io.StdIn.readLine

object MenuUserUtils {

  //show User menu
  def showPrompt(user:UserApp): Unit ={
    println("########" +user.name+"##########")
    println("\n escolha o número:")
    println("\n1-depositar")
    println("2-compra")
    println("3-balanço")
    println("4-historico")
    println("5-adicionar categoria")
    println("6-mostrar totais")
    println(" 0-Exit")
  }

  //wait for user's input
  def getUserInput(): String = readLine

  //option income in user menu
  def income(user:UserApp):UserApp = {
    try {
      print("\n\n\n\n **** QUANTO VAI DEPOSITAR? ****\nValor:")
      val newDepositedValue: Double = roundAt(getUserInput().toDouble)
      print("\n DESCRIÇAO DO SEU DEPOSITO:")
      val depositDescription: String = getUserInput()
      val newBalance: Double = user.balance + newDepositedValue
      val format = new SimpleDateFormat("d-M-y H:m")
      val date:String = format.format(Calendar.getInstance().getTime())
      val category = defineCategory(user)
      //val newDepositList: LazyList[Deposito] = List((newDepositedValue,category, depositDescription, format.format(Calendar.getInstance().getTime()))) ::: user.depositList
      val newDeposit: Deposit= Deposit(newDepositedValue,category,depositDescription,date)
      val newDepositList: LazyList[Deposit] = newDeposit#::user.depositList
      val newUserApp = {
        user.copy(name = user.name, balance = newBalance, depositList = newDepositList, expenseList = user.expenseList, userCategories = user.userCategories)
      }
      println("\n\n\n\n DEPOSITO REALIZADO COM SUCESSO \n\n TEM AGORA " + newBalance + " NA SUA CONTA")
      Thread.sleep(3000)
      newUserApp
    }catch{
      case ex: NumberFormatException => {
        println("Insira um valor válido")
        income(user)

      }
    }
  }

  //option expense in user menu
  def expense(user:UserApp):UserApp= {
    println("\n\n\n\n **** QUAL FOI O VALOR DA SUA COMPRA? ****\n")

    try {
      val newExpenseValue: Double = roundAt(getUserInput().toDouble)
      print("\n DESCRIÇAO DA COMPRA:")
      val ExpenseDescription: String = getUserInput()
      val newBalance: Double = user.balance - newExpenseValue
      val format = new SimpleDateFormat("d-M-y H:m")
      val date:String = format.format(Calendar.getInstance().getTime())
      val category:String = defineCategory(user)
      //val newExpenseList: List[Expense] = ((List((newExpenseValue, category, ExpenseDescription, format.format(Calendar.getInstance().getTime()))) ::: user.expenseList).reverse).reverse
      val newExpense: Expense = Expense(newExpenseValue,category,ExpenseDescription,date)
      val newExpenseList:LazyList[Expense] = newExpense#::user.expenseList
      val newUserApp = {
        user.copy(name = user.name, balance = newBalance, depositList = user.depositList, expenseList = newExpenseList, userCategories = user.userCategories)
      }
      println("\n\n\n\n COMPRA REALIZADA COM SUCESSO \n\n TEM AGORA " + newBalance + " NA SUA CONTA")
      Thread.sleep(3000)
      newUserApp
    } catch {
      case ex: NumberFormatException => {
        println("Insira um valor válido")
        expense(user)

      }


    }
  }

  def higherfunction(x :UserApp, f: (LazyList[Any], String) => Unit): Unit ={
    showOptions()
    val userOption :Int = getUserInput().toInt

    showFilters(x.userCategories, userOption)
    val filter:String = getUserInput()

    x match {
      case 1 => {
        f( x.depositList,filter)
      }
      case 2 =>{
        f( x.expenseList,filter)
      }
    }

  }

  //option history in user menu
  def history(lazyList: LazyList[Any], filter :String): Unit ={
    x match{
      case "1" => {
        // val i = showElements(x.depositList) _
        showFilters(x.userCategories, 1) //print dos filters
        val userFilterInput: Int = getUserInput().toInt
        //if (userFilterInput == 0) i("")
      }
      case "2" =>{
        showFilters(x.userCategories,1) //print dos filters
        val userFilterInput:Int = getUserInput().toInt
        if(userFilterInput!=0) {
          showExpensesFiltered(x.expenseList, x.userCategories(userFilterInput - 1))
        }else {
          //showElements(x.expenseList)
        }
      }
    }
  }


  def listTotal(list : LazyList[Any], filter :String): Int ={
    (list foldLeft 0)(( v, list) => if(v.category == filter)  else lines)
  }

  def showDepositsFiltered(list: LazyList[Deposit], str: String){
    list match {
      case x#::t=> if(x.category == str) println(x); showDepositsFiltered(t,str)
      case LazyList() =>
    }
  }

  def showExpensesFiltered(list: LazyList[Expense], str: String){
    list match {
      case x#::t=> if(x.category == str) println(x); showExpensesFiltered(t,str)
      case LazyList() =>
    }
  }

 /* def showElements[A](list :LazyList[A])(str:String): Unit ={
    list match{
      case x #::t => {
        if( str=="") {
          println(x)
          showElements(t,str)
        }else if(x.category == str){
          println(x)
        }
      }
      case LazyList() => println("\n\n\n")
    }
  }

  */
  //show history
  def showOptions(): Unit ={
    println("\n escolha o número:")
    println(" 1-depositos")
    println(" 2-compras")
  }

  def showFilters(list: List[String],aux:Int): Unit ={
    list match {
      case x :: t => println(aux + "-" +x ); showFilters(t,aux+1)
      case x :: Nil => println(aux+"-"+x)
      case Nil =>
    }
    println("0-Nao aplicar filtro!")

  }

  //show incomes/expenses



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
      showCategories(userApp)

      val input = getUserInput().toInt
      val cat:String = userApp.userCategories(input-1)
      cat
    } catch {
      case ex: MatchError => {
        println("Insira um valor válido")
        defineCategory(userApp)
      }
    }
  }

  //show categories available
  def showCategories(userApp: UserApp): Unit ={
    println("*****  INIDIQUE A CATEGORIA:  ***** ")
    showFilters(userApp.userCategories,1)
  }







  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
