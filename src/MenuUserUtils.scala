
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
    println("7-mostrar Perfil")
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
      val newDeposit: Deposit= new Deposit(newDepositedValue,category,depositDescription,date)
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
      val newExpense: Expense = new Expense(newExpenseValue,category,ExpenseDescription,date)
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

  def higherfunction(x :UserApp, f: (LazyList[UserList], String) => Unit): Unit ={
    showOptions()
    val userOption :String  = getUserInput()
    showFilters(x.userCategories,1)
    val filter :Int = getUserInput().toInt
    userOption match {
      case "1" => {
        f( x.depositList,x.userCategories(filter-1))
      }
      case "2" =>{
        f( x.expenseList,x.userCategories(filter-1))
      }
    }
  }

  //opcao 6 do user
  def listTotal(list : LazyList[UserList], filter :String): Unit = {
    val total :Double = (list foldLeft 0.0)(( v1 :Double, v2:UserList) => if(v2.category == filter) v1+v2.value else v1    )
    println(total)
  }

  //opcao 4 do user
  def showHistory(list :LazyList[UserList], filter:String): Unit = {
    list match{
      case x #::t => {
        if( filter == "0") {
          println(x.value)
          showHistory(t, filter)
        }else if(x.category == filter){
          println(x.value)
          showHistory(t,filter)
        }
      }
      case LazyList() => println("\n\n\n")
    }
  }


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
      case Nil => println("0-Nao aplicar filtro!")
    }

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
      println("########## INDIQUE A CATEGORIA ##########")
      showCategories(userApp.userCategories,1)

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
  def showCategories(list: List[String], aux :Int): Unit ={
    list match {
      case x :: t => println(aux + "-" +x ); showCategories(t,aux+1)
      case x :: Nil => println(aux+"-"+x)
      case Nil =>
    }
  }







  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
