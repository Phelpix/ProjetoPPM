
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Calendar

import IO._
import CSVFileReader._

import scala.::
import scala.io.StdIn.readLine

object MenuUserUtils {

  //opcoes 1 e 2
  def transaction(user:UserApp, userList: LazyList[UserList], tipo:Int) :UserApp = {
    try {
      muchTransaction(userList)
      val newTransactionValue: Double = roundAt(getUserInput().toDouble)
      description()
      val newFormat = new SimpleDateFormat("d-M-y h-m")
      val newID:String = newFormat.format(Calendar.getInstance().getTime())
      val newDescription: String = getUserInput()
      val format = new SimpleDateFormat("M-y")
      val sameDate:String = format.format(Calendar.getInstance().getTime())
      val sameCategory = defineCategory(user)
      val newTransaction: UserList = new UserList {
        override val id: String = newID
        override val value: Double = newTransactionValue
        override val category: String = sameCategory
        override val description: String = newDescription
        override val date: String = sameDate
      }
      val newTransactionList : LazyList[UserList]= newTransaction#::userList
      if (tipo == 1) {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance + newTransactionValue, depositList = newTransactionList, expenseList = user.expenseList, userCategories = user.userCategories, monthlySavings = user.monthlySavings, catSavList= user.catSavList)
        }
        newUserApp
      }
      else {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance - newTransactionValue, depositList = user.depositList, expenseList = newTransactionList, userCategories = user.userCategories, monthlySavings = user.monthlySavings, catSavList= user.catSavList)
        }
        newUserApp
      }

    }
    catch{
      case ex: NumberFormatException => {
        error()
        transaction(user, userList,tipo)

      }
    }
  }

  //opcoes 4 e 6
  def higherfunction(x :UserApp, f: (LazyList[UserList], String) => Unit): Unit ={
    showOptions()
    val userOption :String  = getUserInput()
    if(userOption!="0") {
      showFilters(x.userCategories, 1)
      val filter: Int = getUserInput().toInt
      showYear()
      val year: String = getUserInput()
      showMonth()
      val month: String = getUserInput()
      userOption match {
        case "1" => {
          println("ENTREI")
          val list: LazyList[UserList] = x.depositList.filter(x => {
            x.date == (month + "-" + year)
          })
          if (filter != 0)
            f(list, x.userCategories(filter - 1))
          else
            f(list, "0")
        }
        case "2" => {
          println("ENTREI 2")
          val list: LazyList[UserList] = x.expenseList.filter(x => x.date == (month + "-" + year))
          if (filter != 0)
            f(list, x.userCategories(filter - 1))
          else
            f(list, "0")
        }
        case _ => higherfunction(x, f)
      }
    }
  }

  //opcao 6 do user
  def listTotal(list : LazyList[UserList], filter :String): Double= {
    if(filter == "0") {
      val total: Double = (list foldLeft 0.0) ((v1: Double, v2: UserList) => v1 + v2.value )
      println("Total: "+total)
      total
    }
    else{
      val total: Double = (list foldLeft 0.0) ((v1: Double, v2: UserList) => if (v2.category == filter) v1 + v2.value else v1)
      println("Total: "+ total)
      total
    }
  }

  //opcao 4 do user
  def showHistory(list :LazyList[UserList], filter:String): Unit = {
    list match{
      case x #::t => {
        if( filter == "0") {
          printValue("Valor: " + x.value + "| Descrição: "+ x.description + "| Categoria: " + x.category + " | Data: " + x.date)
          showHistory(t,filter)
        }else if(x.category == filter){
          printValue("Valor: " + x.value + "| Descrição: "+ x.description + "| Categoria: " + x.category + " | Data: " + x.date)
          showHistory(t,filter)
        }else showHistory(t,filter)
      }
      case LazyList() =>
    }
  }

  def showFilters(list: List[String],aux:Int): Unit ={
    list match {
      case x :: t => printValue(aux + "-" +x ); showFilters(t,aux+1)
      case x :: Nil => printValue(aux+"-"+x)
      case Nil => semFiltro()
    }

  }


  def addCategory(user: UserApp,s:String): UserApp ={
    val list:List[String] = s::user.userCategories
    val savList:List[categorySavings] = new categorySavings(s,0.0)::user.catSavList
    val newUserApp = {
      user.copy(name = user.name,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, list, user.monthlySavings,catSavList = savList )
    }
    newUserApp
  }

  //define category of income/expense
  def defineCategory(userApp: UserApp): String ={
    try {
      whatCategory()
      showCategories(userApp.userCategories,1)

      val input = getUserInput().toInt
      val cat:String = userApp.userCategories(input-1)
      cat
    } catch {
      case ex: Exception => {  //nao entra neste case
        error()
        defineCategory(userApp)
      }
    }
  }

  //show categories available
  def showCategories(list: List[String], aux :Int): Unit ={
    list match {
      case x :: t => printValue(aux + "-" +x ); showCategories(t,aux+1)
      case x :: Nil => printValue(aux+"-"+x)
      case Nil =>
    }
  }



  def profile(user: UserApp): UserApp = {
    showSettings(user)
    val input = getUserInput()
    input match {
      case "1"=>{
        newUsernamePrint()
        val newUserName = getUserInput()
        val lines = readFile(credentialsFile)
        changeUsername(user,newUserName,lines,"")
        val newUser = user.copy(name = newUserName,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories,user.monthlySavings)
        newUser
      }

      case "2"=>{
        newEmailPrint()
        val newEmail = getUserInput()
        val lines = readFile(credentialsFile)
        changeEmail(user,newEmail,lines,"")
        val newUser = user.copy(name = user.name,newEmail,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings)
        newUser
      }


      case "3"=>{
        newPasswordPrint()
        val newPassword = getUserInput()
        val lines = readFile(credentialsFile)
        changePassword(user,newPassword,lines,"")
        val newUser = user.copy(name = user.name,user.email,newPassword, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings)
        newUser
      }
      case "0"=> user
      case _ => profile(user)
    }

  }

  def changeValue(list:LazyList[UserList], deposit: UserList, value: Int,index:Int,newlist: LazyList[UserList]):LazyList[UserList] ={
    list match {
      case x#::t=>if(x==deposit) {
        val newDeposit = deposit.setValue(deposit, value)
        val newlist =list.patch(index,Seq(newDeposit),1)
        newlist
      }else {
        changeValue(t, deposit, value, index,newlist)
      }

      case LazyList() =>newlist

    }


  }

  def changeDeposit(userApp: UserApp,toChangeList:LazyList[UserList], index:Int):LazyList[UserList]={
   changeTransaction()
    val option = getUserInput()
    option match {
      case "1"=>{
        newValuePrint()
        val value = getUserInput().toInt
        val newDeposit = toChangeList(index).setValue(toChangeList(index), value)
        val newlist = toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 1

      case "2"=>{
        newDescriptionPrint()
        val value = getUserInput()
        val newDeposit = toChangeList(index).setDescription(toChangeList(index), value)
        val newlist =toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2

      case "3"=>{
        newCategoryPrint()
        val value = defineCategory(userApp)
        val newDeposit = toChangeList(index).setCategory(toChangeList(index), value)
        val newlist =toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 3

      case "0"=>toChangeList
      case _=>changeDeposit(userApp, toChangeList, index)
    }
  }

  def printDeposits(user: UserApp,toChangeList:LazyList[UserList]): LazyList[UserList] ={
    println("Qual quer alterar?")
    showHistory(toChangeList,"0")
    val input = getUserInput().toInt
    val list = changeDeposit(user,toChangeList,input-1)
    val total = listTotal(list,"0")
    println("NEW TOTAL "+ total )
    list
  }


  def changeThings(user: UserApp): UserApp ={
   changeThingsPrint()
    val input = getUserInput()
    input match {
      case "1"=>{
        val format = new SimpleDateFormat("M-y")
        val date: String = format.format(Calendar.getInstance().getTime())
        val list:LazyList[UserList] = user.depositList.filter(x =>{x.date==date})
        val newList =printDeposits(user,list)
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, newList, expenseList = user.expenseList, user.userCategories,user.monthlySavings)
        newUser
      }

      case "2"=>{
        val format = new SimpleDateFormat("M-y")
        val date: String = format.format(Calendar.getInstance().getTime())
        val list:LazyList[UserList] = user.depositList.filter(x =>{x.date==date})
        val newList =printDeposits(user, list)
        val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, user.depositList, newList, user.userCategories, user.monthlySavings, user.catSavList)
        newUser
      }
      case "0" => user
      case _=>changeThings(user)
    }
  }

  def makePlan(user: UserApp,tipo:Int):UserApp={
    val plan :PlanSoft = new PlanSoft(tipo,user.filter(x=>x.value!=0.0))
    val newPlan:PlanSoft = setPercentage(plan)
    val newUser = user.copy(name = user.name,user.email,user.password, balance = user.balance, user.depositList, expenseList = user.expenseList, user.userCategories,user.monthlySavings, user.catSavList,newPlan)
    newUser
  }
  def setPercentage(plan: PlanSoft): PlanSoft = {
    val newl = newList(plan.list,0.10,List[categorySavings]())
    val newp =new PlanSoft(10,newl)
    newp
  }

  def newList(list: List[categorySavings], tipo:Double, finalList:List[categorySavings]):List[categorySavings]={
    list match{
      case x::t=>{
        val newo :categorySavings = new categorySavings(x.category,(x.value-(x.value*tipo)))
        val lets = newo+:finalList
        newList(t,tipo,lets)
      }
      case Nil =>finalList
    }
  }





  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
