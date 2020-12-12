import CSVFileReader.saveUser
import IO._
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control._
import javafx.stage.{Modality, Stage}
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType

class ControllerMenu {
  var user: UserApp= new UserApp("","","",0.0,LazyList[UserList](),LazyList[UserList](),List[String](),List[(String,Double)](),List[categorySavings](),new PlanSoft(10,List[categorySavings]()))
  @FXML
  private var Button1: Button = _
  @FXML
  private var Button2: Button = _
  @FXML
  private var Button3: Button = _
  @FXML
  private var logoutButton: Button = _
  @FXML
  private var Button4: Button = _
  @FXML
  private var Button5: Button=_
  @FXML
  private var Button6: Button=_
  @FXML
  private var Button7: Button=_




  def onButton1Clicked: Unit ={
    //val c:ControllerTransaction=new ControllerTransaction
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTransaction.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerTransaction = fxmlLoader.getController
    controller.setTipo(1)
    controller.setTitle()
    controller.setTempUser(user)
    println("OI"+ user.name)
    controller.setParent(this)
    controller.setCategorias(user.userCategories)
    Button1.getScene.setRoot(mainViewRoot)
  }
  def onButton2Clicked: Unit ={
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTransaction.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerTransaction = fxmlLoader.getController
    println("TOU A FAZER")
    controller.setTipo(2)
    controller.setTitle()
    controller.setTempUser(user)
    controller.setParent(this)
    controller.setCategorias(user.userCategories)
    Button2.getScene.setRoot(mainViewRoot)
  }

  def onButton3Clicked: Unit ={
    val alert = new Alert(AlertType.NONE, "O seu balanço é: " + user.balance, ButtonType.OK)
    alert.showAndWait
  }

  def onButton4Clicked: Unit={
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerHistorico.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerHistorico = fxmlLoader.getController
    controller.setTempUser(user)
    controller.setParent(this)
    controller.setCategorias(user.userCategories)
    controller.startDates()
    Button4.getScene.setRoot(mainViewRoot)
  }

  def onButton5Clicked:Unit= {
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerNewCategory.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerNewCategory = fxmlLoader.getController
    controller.setParent(this)
    controller.setTempUser(user)
    Button5.getScene.setRoot(mainViewRoot)
  }

    def onButton6Clicked(): Unit={
      val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerTotal.fxml"))
      val mainViewRoot: Parent = fxmlLoader.load()
      val controller: ControllerTotal = fxmlLoader.getController
      controller.setTempUser(user)
      controller.setCategorias(user.userCategories)
      controller.startDates()
      Button6.getScene.setRoot(mainViewRoot)
    }

  def onButton7Clicked():Unit={
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerPerfil2.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val controller: ControllerPerfil = fxmlLoader.getController
    controller.setTempUser(user)
    controller.setParent(this)
    Button7.getScene.setRoot(mainViewRoot)
  }

  def onButton8Clicked():Unit={
    val newUser = makePlan(user,10)
    this.setUser(newUser)
    var str:String ="O seu plano é:\n"
    for(x <-user.plan.list){
      str += x.category + ":  " + x.value +"€" +"\n"
    }
    str += "Tente não exceder estes valores. Boa sorte!!!"
    val alert = new Alert(AlertType.NONE, str, ButtonType.OK)
    alert.showAndWait
  }


  def onButtonLogoutClicked:Unit={
    println("LOGOUT:"+user.userCategories)
    saveUser(user)
    logoutButton.getScene().getWindow.hide()

  }
//setter&getter
  def setUser(user: UserApp): Unit ={
    this.user=user
    println(user.userCategories)
  }
  def getUser():UserApp={
    user
  }

  def makePlan(user: UserApp,tipo:Int):UserApp={
    val plan :PlanSoft = new PlanSoft(tipo,user.catSavList.filter(x=>x.value!=0.0))
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


}
