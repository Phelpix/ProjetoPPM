import javafx.fxml.FXML
import javafx.scene.control.{Button, ChoiceBox, RadioButton, TextArea}

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
  @FXML
  private var depositoRB: RadioButton =_
  @FXML
  private var compraRB: RadioButton =_
  @FXML
  private var fecharButton:  Button =_

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

  def startDates(): Unit ={
    anoCB.getItems().add("2020")
    anoCB.getItems().add("2019")
    anoCB.getItems().add("2018")
    mesCB.getItems().add("1")
    mesCB.getItems().add("2")
    mesCB.getItems().add("3")
    mesCB.getItems().add("4")
    mesCB.getItems().add("5")
    mesCB.getItems().add("6")
    mesCB.getItems().add("7")
    mesCB.getItems().add("8")
    mesCB.getItems().add("9")
    mesCB.getItems().add("10")
    mesCB.getItems().add("11")
    mesCB.getItems().add("12")
  }

  def onOkClicked: Unit ={
    if(depositoRB.isSelected){
      val list:LazyList[UserList] = tempUser.depositList.filter(x =>{x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem)})
      val textoDeposito:String=showHistory( list,categoriasCB.getSelectionModel.getSelectedItem,"")
      println("Lista:"+textoDeposito)
      histTA.setText(textoDeposito)
    }else if(compraRB.isSelected){
      val list:LazyList[UserList] = tempUser.expenseList.filter(x => x.date==(mesCB.getSelectionModel.getSelectedItem+"-"+anoCB.getSelectionModel.getSelectedItem))
      val textoCompra:String=showHistory( list,categoriasCB.getSelectionModel.getSelectedItem,"")

      histTA.setText(textoCompra)
    }



  }

  def showHistory(list :LazyList[UserList], filter:String, tempLista:String): String = {
    println("categoria:"+filter)
    list match{
      case x #::t => {

        if(x.category == filter){
          println(x.value)
          val novatempLista=tempLista+x.value+"\n"
          showHistory(t,filter,novatempLista)
        }else showHistory(t,filter,tempLista)
      }
      case LazyList() =>tempLista+""
    }
  }

  def onFecharClicked: Unit ={
    fecharButton.getScene().getWindow.hide()
  }

}
