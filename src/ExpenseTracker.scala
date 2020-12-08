
import ExpenseTrackerUtils._
import MenuUser._
import CSVFileReader._
import IO._

import scala.io.StdIn.readLine
import scala.annotation.tailrec

case class User(username: String, password: String, email: String)

object ExpenseTracker extends App{

 val file = "CSVFiles/UserCredentials.csv"

 mainLoop()

 @tailrec
 def mainLoop(): Unit ={

  showMenu()
  val userInput:String = getUserInput()


  userInput match {
   case "1" =>{
    email()
    val emailInput:String = getUserInput()
    password()
    val passwordInput:String = getUserInput()

    val list = readFile(file)

    val username:Option[String] = searchUser2(emailInput,passwordInput,list)
    username match{
     case Some(b) => {
      val userToApp:UserApp = readUser("CSVFiles/"+b+".csv",b,emailInput,passwordInput)
      userLoop(userToApp)
     }
     case None =>{
      loginError()
     }
    }
    mainLoop()


   }

   case "2" =>{
    username()
    val usernameInput = getUserInput()
    password()
    val passwordInput :String= getUserInput()
    email()
    val emailInput :String= getUserInput()

    if(usernameInput == "" || passwordInput=="" || emailInput=="") {
     error()
     mainLoop()
    } else{
     val list = readFile(file)

     if(RegisterUser(usernameInput,emailInput,list) == false){
      val s:String = usernameInput+","+passwordInput+","+emailInput+"\n"
      writeFile(file,s,true)
      writeFile("CSVFiles/"+usernameInput+".csv","newUser",true)
     }

     mainLoop()
    }
   }

   case _ => closeApp()
  }
 }


}