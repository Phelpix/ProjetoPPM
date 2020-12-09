import java.awt.TextArea

import javafx.fxml.FXML
import javafx.scene.control.{Button, ChoiceBox}

class ControllerHistorico {

  @FXML
  private var categoriasCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var okButton: Button =_
  @FXML
  private var mesCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var anoCB: ChoiceBox[String] = new ChoiceBox[String]()
  @FXML
  private var histTA: TextArea =_

  var tempUser: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))

  def setTempUser(tempUser: UserApp): Unit ={
    this.tempUser=tempUser
  }

  def setCategorias(list:List[String]): Unit = {
    list match {
      case Nil =>
      case x :: t => {
        categoriasCB.getItems.add(x)
        setCategorias(t)
      }
    }
  }

  def onOkClicked: Unit ={
    val list:LazyList[UserList] = tempUser.depositList.filter(x =>{x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem)})
      val lista:String=showHistory( list,categoriasCB.getSelectionModel.getSelectedItem)
      histTA.setText(lista)


  }

  def showHistory(list :LazyList[UserList], filter:String): String = {
    var tempLista=""
    list match{
      case x #::t => {

        if(x.category == filter){
          //printValue(x.value)
          tempLista+=x.value+"\n"
          showHistory(t,filter)
        }else showHistory(t,filter)
      }
      case LazyList() =>tempLista+""
    }
  }

}
