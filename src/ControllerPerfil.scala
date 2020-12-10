import java.text.SimpleDateFormat
import java.util
import java.util.Calendar

import CSVFileReader.{credentialsFile, writeFile}
import MenuUserUtils.roundAt
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.FXML
import javafx.scene.control.{Button, ChoiceBox, ComboBox, Labeled, RadioButton, TextField}

class ControllerPerfil {
  @FXML
  private var textField: TextField = _
  @FXML
  private var usernameButton: RadioButton=_
  @FXML
  private var passButton: RadioButton=_
  @FXML
  private var emailButton: RadioButton=_
  @FXML
  private var novoLabel:Labeled=_
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
  }
/*
  def onConfirmButtonClicked():Unit={
    if(usernameButton.isSelected){
      changeUsername(parent.getUser(),textField.getText(),parent.)
    }else if(passButton.isSelected){

    }else if(emailButton.isSelected){

    }

  }
*/
  def changeUsername(user: UserApp,newUserName:String,lines:List[Array[String]],text:String): Unit ={
    lines match {
      case x::t => if( x(0)==user.name ){
        val newText = text+ newUserName + ","+x(1)+","+x(2)+"\n"
        changeUsername(user,newUserName,t,newText)
      } else {
        val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
        changeUsername(user,newUserName,t,newText)
      }

      case x::Nil=> if( x(0)==user.name ){
        val newText = text+newUserName + ","+x(1)+","+x(2)+"\n"
        changeUsername(user,newUserName,Nil,newText)
      } else {
        val newText = text+ x(0) + ","+x(1)+","+x(2)+"\n"
        changeUsername(user,newUserName,Nil,newText)
      }
      case Nil=> writeFile(credentialsFile,text,false)
    }
  }


}