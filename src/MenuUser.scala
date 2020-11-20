import java.text.SimpleDateFormat
import java.io.IOException
import MenuUserUtils._
import CSVFileReader._
import scala.io.StdIn.readLine
import java.util.Calendar

import scala.::
case class Deposit(value:Double, category:String, description:String, date:String)
case class Expense(value: Double, category: String,description:String, date:String)
case class UserApp(name: String, balance:Double, depositList:LazyList[Deposit], expenseList: LazyList[Expense], userCategories: List[String])

object MenuUser extends App{


  def userLoop(user: UserApp): Unit ={


    showPrompt(user)

    val userInput = getUserInput()

    userInput match {
      case "1" => {
        val newUserApp:UserApp = income(user)
        userLoop(newUserApp)
      }
      case "2" => {
        val newUserApp:UserApp = expense(user)
        userLoop(newUserApp)
      }
      case "3"=>{
        println("\n\n\n\n **** O VALOR DA SUA BALANCA E " + user.balance +" ****\n")
        Thread.sleep(3000)
        userLoop(user)
      }
      case "4" =>{
        history(user)
        Thread.sleep(3000)
        userLoop(user)
      }

      case "5" =>{
        println("Qual a categoria que quer adicionar?")
        val categoria = getUserInput()
        val newUserApp = addCategory(user,categoria)
        userLoop(newUserApp)
      }
      case "0" => saveUser(user)
    }


  }
}
