import CSVFileReader.{readFile, readUser}
import ExpenseTrackerUtils.searchUser
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.{Button, TextField}
import javafx.stage.{Modality, Stage}

class Controller {
  @FXML
  private var registerButton: Button = _
  @FXML
  private var loginButton: Button = _
  @FXML
  private var emailTF: TextField = _
  @FXML
  private var passwordTF: TextField = _

  def onLoginClicked(): Unit = {
    val file = "CSVFiles/UserCredentials.csv"
    val list = readFile(file)
    val username:String = searchUser(emailTF.getText(),passwordTF.getText(),list)(0)
    if(username == ""){
      println("Login nao dado")
    } else {
      val userToApp: UserApp = readUser("CSVFiles/" + username + ".csv", username)
      print("user encontrado")
    }
  }

  def onRegisterClicked(): Unit = {
    val secondStage: Stage = new Stage()
    secondStage.initModality(Modality.APPLICATION_MODAL)
    secondStage.initOwner(registerButton.getScene().getWindow)
    val fxmlLoader = new FXMLLoader(getClass.getResource("ControllerRegisto.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    secondStage.setScene(scene)
    secondStage.show()
  }
}
