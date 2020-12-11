import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import MenuUserUtils.roundAt
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, ChoiceBox, ComboBox, Labeled, TextField}
class ControllerNewCategory {
 @FXML
 private var titleLable: Labeled =_
 @FXML
 private var novaCategoria: TextField =_
 @FXML
 private var novaLabel : Labeled =_
 @FXML
 private var okButton : Button=_
 @FXML
 private var voltarButton:Button =_

 var tempUser:UserApp =new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
 var parent:ControllerMenu = new ControllerMenu

 def getUser():UserApp={
  tempUser
 }

 def setParent(parent: ControllerMenu): Unit ={
  this.parent=parent
 }
 def setTempUser(tempUser: UserApp): Unit ={
  this.tempUser=tempUser
 }

 def addCategory(user: UserApp,s:String): UserApp ={
  val list:List[String] = s::user.userCategories
  val savList:List[categorySavings] = new categorySavings(s,0.0)::user.catSavList
  val newUserApp = {
   user.copy(name = user.name,user.email,user.password, balance = user.balance, depositList = user.depositList, expenseList = user.expenseList, list, user.monthlySavings,catSavList = savList )
  }
  newUserApp
 }


 def onOkClicked():Unit={
  parent.setUser(addCategory(tempUser,novaCategoria.getText()))
  okButton.getScene().getWindow.hide()
  println("OK CLICKED:"+parent.getUser().userCategories)
 }

 def onVoltarClicked: Unit ={
  val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerMenu.fxml"))
  val mainViewRoot: Parent = fxmlLoader.load()
  voltarButton.getScene.setRoot(mainViewRoot)
 }





}
