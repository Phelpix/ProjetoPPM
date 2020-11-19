
import java.text.SimpleDateFormat
import java.util.Calendar

import scala.::
import scala.io.StdIn.readLine

object MenuUserUtils {

  //show User menu
  def showPrompt(): Unit ={
    println("\n escolha o número:")
    println("\n 1-depositar")
    println(" 2-compra")
    println(" 3-balanço")
    println(" 4-filtro")
  }

  //wait for user's input
  def getUserInput(): String = readLine.trim.toUpperCase

  //option income in user menu
  def income(user:UserApp):UserApp = {
    try {
      println("\n\n\n\n **** QUANTO VAI DEPOSITAR? ****\n")
      val newDepositedValue: Double = roundAt(getUserInput().toDouble)
      print("\n DESCRIÇAO DO SEU DEPOSITO:")
      val depositDescription: String = getUserInput()
      val newBalance: Double = user.balance + newDepositedValue
      val format = new SimpleDateFormat("d-M-y H:m")
      val date:String = format.format(Calendar.getInstance().getTime())
      val category = defineCategory()
      //val newDepositList: LazyList[Deposito] = List((newDepositedValue,category, depositDescription, format.format(Calendar.getInstance().getTime()))) ::: user.depositList
      val newDeposit: Deposit= Deposit(newDepositedValue,category,depositDescription,date)
      val newDepositList: LazyList[Deposit] = newDeposit#::user.depositList
      val newUserApp = {
        user.copy(name = user.name, balance = newBalance, depositList = newDepositList, expenseList = user.expenseList)
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
      val category:String = defineCategory()
      //val newExpenseList: List[Expense] = ((List((newExpenseValue, category, ExpenseDescription, format.format(Calendar.getInstance().getTime()))) ::: user.expenseList).reverse).reverse
      val newExpense: Expense = Expense(newExpenseValue,category,ExpenseDescription,date)
      val newExpenseList:LazyList[Expense] = newExpense#::user.expenseList
      val newUserApp = {
        user.copy(name = user.name, balance = newBalance, depositList = user.depositList, expenseList = newExpenseList)
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

  //option filters in user menu
  def filters(x:UserApp): Unit ={
    showFilters()
    val userFilterInput:String = getUserInput()

    userFilterInput match{
      case "1" =>{
        showElements(x.depositList)
      }
      case "2" =>{
       showElements(x.expenseList)
      }
      case "3" =>{

      }

    }
  }

  //show filters
  def showFilters(): Unit ={
    println("\n escolha o número:")
    println(" 1-depositos")
    println(" 2-compras")
    println(" 3-comida")
    println(" 4-carro")
  }

  //show incomes/expenses
  def showElements[A](list :LazyList[A]): Unit ={
    list match{
      case x #::t => {println(x)
        showElements(t)
      }
      case LazyList() => println("\n\n\n")
    }
  }

  //object category
  object Category extends Enumeration{
    val food = "comida"
    val car = "carro"
    val university = "universidade"
    val home = "casa"
    val others = "outros"
  }


  //define category of income/expense
  def defineCategory(): String ={
    try {
      showCategories()

      val input = getUserInput()

      input match {
        case "1" => {
          val category = Category.food
          category
        }
        case "2" => {
          val category = Category.car
          category
        }
        case "3" => {
          val category = Category.university
          category
        }
        case "4" => {
          val category = Category.home
          category
        }
        case "5" => {
          val category = Category.others
          category
        }

      }
    } catch {
      case ex: MatchError => {
        println("Insira um valor válido")
        defineCategory()
      }
    }
  }

  //show categories available
  def showCategories(): Unit ={
    println("*****  INIDIQUE A CATEGORIA:  ***** ")
    println("\n escolha o número:")
    println("1-comida")
    println("2-carro")
    println("3-universidade")
    println("4-casa")
    println("5-outros")
  }






  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
