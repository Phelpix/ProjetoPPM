
import ExpenseTrackerUtils._
import CSVFileReader._

import scala.annotation.tailrec

case class User(username: String, password: String, email: String)
//teste para o picoito
object ExpenseTracker extends App{

 val file = "UserCredentials.csv"

 mainLoop()

 @tailrec
 def mainLoop(): Unit ={

  showMenu()
  val userInput = getUserInput()


  userInput match {
   case "1" =>{
    print("Username:")
    val usernameInput = getUserInput()
    print("Password:")
    val passwordInput = getUserInput()
    mainLoop()
   }

   case "2" =>{
    print("Username:")
    val usernameInput = getUserInput()
    print("Password:")
    val passwordInput = getUserInput()
    print("Email:")
    val emailInput = getUserInput()

    val list = readFile(file)

    if(findUser(usernameInput,emailInput,list) == false){
     val s:String = usernameInput+","+passwordInput+","+emailInput
     writeFile(file,s)
    }

    mainLoop()
   }

   case _ => println("######GOODBYE#######")
  }
 }


}