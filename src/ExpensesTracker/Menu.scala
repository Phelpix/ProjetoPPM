package ExpensesTracker

import ExpensesTracker.MenuUtils._

import scala.io.StdIn.readLine


import scala.::

case class UserApp(name: String, balance:Double, depositList: List[Double], expenseList: List[Double])

object Menu extends App{

  val x = new UserApp("Hugo",0,Nil,Nil)

  mainLoop(x)

  def mainLoop(user: UserApp): Unit ={


    showPrompt()

    val userInput = getUserInput()

    userInput match {
      case "1" => {
        println("\n\n\n\n **** QUANTO VAI DEPOSITAR? ****\n")
        val newDepositedValue:Double = getUserInput().toDouble
        val newBalance: Double = user.balance + newDepositedValue
        val newDepositList: List[Double] = List(newDepositedValue) ::: user.depositList
        val newUserApp = {
          user.copy(name = user.name, balance = newBalance, depositList = newDepositList, expenseList = user.expenseList)
        }
        println("\n\n\n\n DEPOSITO REALIZADO COM SUCESSO \n\n TEM AGORA "+ newBalance+" NA SUA CONTA")
        println(newDepositList)
        Thread.sleep(3000)
        mainLoop(newUserApp)
      }
      case "2" => {
        println("\n\n\n\n **** QUAL FOI O VALOR DA SUA COMPRA? ****\n")
        val newExpenseValue: Double = getUserInput().toDouble
        val newBalance: Double= user.balance - newExpenseValue
        val newExpenseList: List[Double] = (List(newExpenseValue) ::: user.expenseList).reverse
        val newUserApp = {
          user.copy(name = user.name, balance = newBalance, depositList = user.depositList, expenseList = newExpenseList)
        }
        println("\n\n\n\n COMPRA REALIZADA COM SUCESSO \n\n TEM AGORA "+ newBalance+" NA SUA CONTA")
        println(newExpenseList)
        Thread.sleep(3000)
        mainLoop(newUserApp)
      }
      case "3"=>{
        println("\n\n\n\n **** O VALOR DA SUA BALANCA E " + user.balance +" ****\n")
        Thread.sleep(3000)
        mainLoop(user)
      }
      case "4" =>{
        filtros(user)
        Thread.sleep(3000)
        mainLoop(user)
      }
    }


  }
}