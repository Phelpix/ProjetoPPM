
import ExpenseTrackerUtils._
import MenuUser._
import CSVFileReader._

import scala.annotation.tailrec

case class User(username: String, password: String, email: String)
//teste para o picoito
object ExpenseTracker extends App{

 val file = "CSVFiles/UserCredentials.csv"

 mainLoop()

 @tailrec
 def mainLoop(): Unit ={

  showMenu()
  val userInput = getUserInput()


  userInput match {
   case "1" =>{
    print("email:")
    val emailInput = getUserInput()
    print("Password:")
    val passwordInput = getUserInput()

    val list = readFile(file)

    val username:String = loginUser(emailInput,passwordInput,list)

    if(username == ""){
     println("Login nao dado")
    } else{
     val usertoapp:UserApp = readUser("CSVFiles/"+username+".csv",username)
     userLoop(usertoapp)
    }
    mainLoop()


   }

   case "2" =>{
    print("Username:")
    val usernameInput = getUserInput()
    print("Password:")
    val passwordInput = getUserInput()
    print("Email:")
    val emailInput = getUserInput()

    if(usernameInput == "" || passwordInput=="" || emailInput=="") {
     println("\n Por favor insira dados válidos!!! \n")
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

   case _ => println("######GOODBYE#######")
  }
 }


}