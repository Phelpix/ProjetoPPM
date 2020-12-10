import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import CSVFileReader.{changeEmail, changePassword, changeUsername, credentialsFile, readFile, writeFile}
import IO.{email, getUserInput, newEmailPrint, newPasswordPrint, newUsernamePrint, showSettings}
import MenuUserUtils.roundAt
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
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
      parent.setUser(profile(tempUser))
      confirmButton.getScene().getWindow.hide()
  }


  def profile(user: UserApp): UserApp = {
    val lines = readFile(credentialsFile)
    changeUsername(user,usernameTF.getText(),lines,"")
    changeEmail(user,emailTF.getText(),lines,"")
    changePassword(user,passwordTF.getText(),lines,"")
    val newUser :UserApp = user.copy(name = usernameTF.getText(),emailTF.getText(),passwordTF.getText(), balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, user.userCategories, user.monthlySavings,user.catSavList,user.plan)
    newUser
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


}