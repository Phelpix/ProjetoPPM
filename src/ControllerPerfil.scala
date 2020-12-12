import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import CSVFileReader.{changeEmail, changePassword, changeUsername, credentialsFile, readFile, writeFile}
import ExpenseTrackerUtils.RegisterUser
import IO.{email, getUserInput, newEmailPrint, newPasswordPrint, newUsernamePrint, showSettings}
import MenuUserUtils.roundAt
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, ChoiceBox, ComboBox, Labeled, RadioButton, TextField}

class ControllerPerfil {
  @FXML
  private var usernameTF: TextField = _
  @FXML
  private var passwordTF: TextField=_
  @FXML
  private var emailTF: TextField=_
  @FXML
  private var confirmButton:Button=_
  @FXML
  private var voltarButton:Button =_
  @FXML
  private var errorLabel :Labeled =_

  val credentialsFile = "CSVFiles/UserCredentials.csv"
  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
  var parent:ControllerMenu=new ControllerMenu
  //setter

  def getUser():UserApp={
    tempUser
  }

  def setParent(parent: ControllerMenu): Unit ={
    this.parent=parent
  }
  def setTempUser(tempUser: UserApp): Unit ={
    this.tempUser=tempUser
    usernameTF.setText(tempUser.name)
    passwordTF.setText(tempUser.password)
    emailTF.setText(tempUser.email)

  }

  def onConfirmButtonClicked():Unit={
      tempUser= (profile(tempUser))
  }


  def profile(user: UserApp): UserApp = {
    if (usernameTF.getText() =="" || passwordTF.getText() =="" || emailTF.getText() ==""){
      errorLabel.setText("Introduza as credenciais corretas")
      errorLabel.setVisible(true)
      user
    } else {
      val lines = readFile(credentialsFile)
      if(RegisterUser(usernameTF.getText(),emailTF.getText(),lines) == false) {
        errorLabel.setVisible(false)
        changeUsername(user, usernameTF.getText(), lines, "")
        val newUser1: UserApp = user.copy(name = usernameTF.getText(), user.email, user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings, user.catSavList, user.plan)
        println("NEW PASSWORD: " + passwordTF.getText())
        val lines1 = readFile(credentialsFile)
        changePassword(newUser1, passwordTF.getText(), lines1, "")
        val newUser2: UserApp = newUser1.copy(newUser1.name, newUser1.email, passwordTF.getText(), balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings, user.catSavList, user.plan)
        val lines2 = readFile(credentialsFile)
        changeEmail(newUser2, emailTF.getText(), lines2, "")
        val newUser3: UserApp = newUser2.copy(newUser2.name, emailTF.getText(), newUser2.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings, user.catSavList, user.plan)
        println("USER 3: " + newUser3.name + "" + newUser3.email + "" + newUser3.password)
        newUser3
      } else {
        errorLabel.setText("User ja existe com estas credenciais")
        errorLabel.setVisible(true)
        user
      }
    }
    /*if(usernameButton.isSelected) {
        val lines = readFile(credentialsFile)
        changeUsername(user,novoValue,lines,"")
        val newUser:UserApp = user.copy(name = novoValue,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories,user.monthlySavings,user.catSavList,user.plan)
        newUser
      } else if (passButton.isSelected){
        val lines = readFile(credentialsFile)
        changeEmail(user,novoValue,lines,"")
        val newUser:UserApp = user.copy(name = user.name,novoValue,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings,user.catSavList,user.plan)
        newUser
      }else if(emailButton.isSelected){
        val lines = readFile(credentialsFile)
        changePassword(user,novoValue,lines,"")
        val newUser :UserApp = user.copy(name = user.name,user.email,novoValue, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, userCategories=user.userCategories, monthlySavings = user.monthlySavings,catSavList =user.catSavList,user.plan)
        newUser
      }else user*/

    }

  def onVoltarClicked: Unit ={
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerMenu = fxmlLoader.getController
    controller.setUser(tempUser)
    voltarButton.getScene.setRoot(mainViewRoot)
  }


}