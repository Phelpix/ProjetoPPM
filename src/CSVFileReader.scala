import scala.io.Source
import java.io._

object CSVFileReader{

 def writeFile(file: String, to_write: String): Unit ={
  val pw = new PrintWriter(new FileWriter(file,true))
  pw.write(to_write)
  pw.close
 }

 def readFile(file: String): List[Array[String]] = {
  var newList = List[Array[String]]()
  val bufferedSource = io.Source.fromFile(file)
  for (line <- bufferedSource.getLines) {
   val cols = line.split(",")
   newList = newList:+ cols
   println(s"${cols(0)}|${cols(1)}|${cols(2)}")
  }
  bufferedSource.close
  newList
 }
}