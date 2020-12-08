
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

    val username:String = searchUser2(emailInput,passwordInput,list).get(0)

    if(username == ""){
     loginError()
    } else{
     val userToApp:UserApp = readUser("CSVFiles/"+username+".csv",username,emailInput,passwordInput)
     userLoop(userToApp)
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