
import java.text.SimpleDateFormat
import java.util.Calendar

import IO._
import CSVFileReader._

object MenuUserUtils {

  //opcoes 1 e 2
  def transaction(user:UserApp, userList: LazyList[UserList], tipo:Int) :UserApp = {
    try {
      muchTransaction(userList)
      val newTransactionValue: Double = roundAt(getUserInput().toDouble)
      description()
      val newDescription: String = getUserInput()
      val format = new SimpleDateFormat("M-y")
      val sameDate:String = format.format(Calendar.getInstance().getTime())
      val sameCategory = defineCategory(user)
      val newTransaction: UserList = new UserList {
        override val value: Double = newTransactionValue
        override val category: String = sameCategory
        override val description: String = newDescription
        override val date: String = sameDate
      }
      val newTransactionList : LazyList[UserList]= newTransaction#::userList
      if (tipo == 1) {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance + newTransactionValue, depositList = newTransactionList, expenseList = user.expenseList, userCategories = user.userCategories)
        }
        newUserApp
      }
      else {
        val newUserApp = {
          user.copy(name = user.name, balance = user.balance - newTransactionValue, depositList = user.depositList, expenseList = newTransactionList, userCategories = user.userCategories)
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
    showFilters(x.userCategories,1)
    val filter :Int = getUserInput().toInt
    showYear()
    val year:String = getUserInput()
    showMonth()
    val month :String = getUserInput()
    userOption match {
      case "1" => {
        val list:LazyList[UserList] = x.depositList.filter(x =>{x.date==(month+"-"+year)})
        if (filter!=0)
          f( list,x.userCategories(filter-1))
        else
          f(list, "0")
      }
      case "2" =>{
        val list:LazyList[UserList] = x.expenseList.filter(x => x.date==(month+"-"+year))
        if(filter!=0)
          f(list,x.userCategories(filter-1))
        else
          f(list,"0")
      }
    }
  }

  //opcao 6 do user
  def listTotal(list : LazyList[UserList], filter :String): Double= {
    if(filter == "0") {
      val total: Double = (list foldLeft 0.0) ((v1: Double, v2: UserList) => v1 + v2.value )
      println(total)
      total
    }
    else{
      val total: Double = (list foldLeft 0.0) ((v1: Double, v2: UserList) => if (v2.category == filter) v1 + v2.value else v1)
      println(total)
      total
    }
  }

  //opcao 4 do user
  def showHistory(list :LazyList[UserList], filter:String): Unit = {
    list match{
      case x #::t => {
        if( filter == "0") {
          printValue(x.value)
          showHistory(t,filter)
        }else if(x.category == filter){
          printValue(x.value)
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
    val newUserApp = {
      user.copy(name = user.name,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, list)
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
      case ex: MatchError => {
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
        val newUser = user.copy(name = newUserName,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories)
        newUser
      }

      case "2"=>{
        newEmailPrint()
        val newEmail = getUserInput()
        val lines = readFile(credentialsFile)
        changeEmail(user,newEmail,lines,"")
        val newUser = user.copy(name = user.name,newEmail,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories)
        newUser
      }


      case "3"=>{
        newPasswordPrint()
        val newPassword = getUserInput()
        val lines = readFile(credentialsFile)
        changePassword(user,newPassword,lines,"")
        val newUser = user.copy(name = user.name,user.email,newPassword, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories)
        newUser
      }
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

  def changeDeposit(toChangeList:LazyList[UserList], index:Int):LazyList[UserList]={
    val option = getUserInput()
    option match {
      case "1"=>{
        val value = getUserInput().toInt
        val newDeposit = toChangeList(index).setValue(toChangeList(index), value)
        val newlist = toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 1

      case "2"=>{
        val value = getUserInput()
        val newDeposit = toChangeList(index).setDescription(toChangeList(index), value)
        val newlist =toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2

      case "3"=>{
        newCategoryPrint()
        val value = getUserInput()
        val newDeposit = toChangeList(index).setCategory(toChangeList(index), value)
        val newlist =toChangeList.patch(index,Seq(newDeposit),1)
        newlist
      } //case 2


    }
  }

  def printDeposits(toChangeList:LazyList[UserList],userApp: UserApp): LazyList[UserList] ={
    changeDepositPrint()
    showHistory(toChangeList,"0")
    val input = getUserInput().toInt
    val list = changeDeposit(toChangeList,input-1)
    list
  }


  def changeThings(user: UserApp): UserApp ={
    val input = getUserInput()
    input match {
      case "1"=>{
        val newList =printDeposits(user.depositList,user)
        val total = listTotal(newList,"0")
        val newUser = user.copy(name = user.name,user.email,user.password, balance = total, newList, expenseList = user.expenseList, user.userCategories)
        newUser
      }

      case "2"=>{
        val newList =printDeposits(user.expenseList,user)
        val total = listTotal(newList,"0")
        val newUser = user.copy(name = user.name,user.email,user.password, balance = total, user.depositList, newList, user.userCategories)
        newUser
      }
    }
  }

  def monthSavings(userApp: UserApp)( date:String, filter:String):Double={
    val listIncome :LazyList[UserList] = userApp.depositList.filter(x => x.date == date)
    val income :Double=listTotal(listIncome,filter)
    val listExpense:LazyList[UserList] = userApp.expenseList
    val expense:Double= listTotal(listExpense, filter)
    if(filter =="0")
      income-expense
    else
      expense
  }






  /*

  ******* Helping methods *******

   */
  //rounding numbers to 2 decimals places
  def roundAt(n: Double): Double = { val s = math pow (10, 2); (math round n * s) / s }


}
