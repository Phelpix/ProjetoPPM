import CSVFileReader.{readFile, writeFile}
import ExpenseTracker.file
import ExpenseTrackerUtils.RegisterUser
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, TextField}

class ControllerRegisto {
  @FXML
  private var registerButton2: Button = _
  @FXML
  private var usernameRegistoTF: TextField = _
  @FXML
  private var passwordRegistoTF: TextField = _
  @FXML
  private var emailRegistoTF: TextField = _
  @FXML
  private var backButton: Button = _

  def onRegisterClicked2(): Unit = {
    val file = "CSVFiles/UserCredentials.csv"
    val list = readFile(file)
    if(RegisterUser(usernameRegistoTF.getText(),emailRegistoTF.getText(),list) == false){
      print("entrei")
      val s:String = usernameRegistoTF.getText()+","+passwordRegistoTF.getText()+","+emailRegistoTF.getText()+"\n"
      writeFile(file,s,true)
      writeFile("CSVFiles/"+usernameRegistoTF.getText()+".csv","newUser"+"\n\n\n\n\n\nComida/0.0,Carro/0.0,Universidade/0.0,Casa/0.0",true)
    }
    registerButton2.getScene().getWindow.hide()
  }
  def backClicked: Unit ={
    backButton.getScene().getWindow.hide()
  }

}
