import java.text.SimpleDateFormat
import java.util.Calendar
import Menu.{mainLoop, x}

import scala.io.StdIn.readLine

object MenuUtils {
 def showPrompt(): Unit = {
  println("\n escolha o número:")
  println("\n 1-depositar")
  println(" 2-compra")
  println(" 3-balanço")
  println(" 4-filtro")
 }

 def getUserInput(): String = readLine.trim.toUpperCase

 def filtros(x: UserApp): Unit = {
  showFilters()
  val userFilterInput: String = getUserInput()

  userFilterInput match {
   case "1" => {
    showElements(x.expenseList)
   }
   case "2" => {
    showElements(x.depositList)
   }
  }
 }

 def showFilters(): Unit = {
  println("\n escolha o número:")
  println(" 1-compras")
  println(" 2-depositos")
  println(" 3-comida")
  println(" 4-carro")
 }

 def income(user: UserApp):UserApp = {
  try {
   println("\n\n\n\n **** QUANTO VAI DEPOSITAR? ****\n")
   val newDepositedValue: Double = getUserInput().toDouble
   print("\n DESCRIÇAO DO SEU DEPOSITO:")
   val DepositDescription: String = getUserInput()
   val newBalance: Double = user.balance + newDepositedValue
   val format = new SimpleDateFormat("d-M-y H:m")
   val newDepositList: List[(Double, String, Any)] = List((newDepositedValue, DepositDescription, format.format(Calendar.getInstance().getTime()))) ::: user.depositList
   val newUserApp = {
    user.copy(name = user.name, balance = newBalance, depositList = newDepositList, expenseList = user.expenseList)
   }
   println("\n\n\n\n DEPOSITO REALIZADO COM SUCESSO \n\n TEM AGORA " + newBalance + " NA SUA CONTA")
   Thread.sleep(3000)
   newUserApp
  } catch {
   case ex: NumberFormatException => {
    println("Insira um valor válido")
    income(user)

   }
  }
 }

 def expense(user: UserApp):UserApp = {
  println("\n\n\n\n **** QUAL FOI O VALOR DA SUA COMPRA? ****\n")

  try {
   val newExpenseValue: Double = getUserInput().toDouble
   print("\n DESCRIÇAO DA COMPRA:")
   val ExpenseDescription: String = getUserInput()
   val newBalance: Double = user.balance - newExpenseValue
   val format = new SimpleDateFormat("d-M-y H:m")
   val newExpenseList: List[(Double, String, Any)] = ((List((newExpenseValue, ExpenseDescription, format.format(Calendar.getInstance().getTime()))) ::: user.expenseList).reverse).reverse
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

 def showElements(list: List[(Double, String, Any)]): Unit = {
  list match {
   case x :: t => {
    println(x)
    showElements(t)
   }
   case x :: Nil => {
    println(x)
   }
   case Nil => {
    println("\n\n\n")
   }
  }
 }


}
