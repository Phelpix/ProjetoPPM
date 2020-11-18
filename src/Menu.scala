import java.text.SimpleDateFormat
import java.io.IOException
import MenuUtils._

import scala.io.StdIn.readLine
import java.util.Calendar

import scala.::

case class UserApp(name: String, balance:Double, depositList: List[(Double,String, String, Any)], expenseList: List[(Double,String, String, Any)])

object Menu extends App{

  val x = new UserApp("Hugo",0,Nil,Nil)

  mainLoop(x)

  def mainLoop(user: UserApp): Unit ={


    showPrompt()

    val userInput = getUserInput()

    userInput match {
      case "1" => {
        val newUserApp:UserApp = income(user)
        mainLoop(newUserApp)
      }
      case "2" => {
        val newUserApp:UserApp = expense(user)
        mainLoop(newUserApp)
      }
      case "3"=>{
        println("\n\n\n\n **** O VALOR DA SUA BALANCA E " + user.balance +" ****\n")
        Thread.sleep(3000)
        mainLoop(user)
      }
      case "4" =>{
        filters(user)
        Thread.sleep(3000)
        mainLoop(user)
      }
      case _ =>{
        mainLoop(user)
      }
    }


  }
}
