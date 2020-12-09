import java.util.Locale.Category
import MenuUserUtils.roundAt
case class categorySavings( category: String, value: Double) {
  val this.category = category
  val this.value = value


  def setValue(newValue: Double): categorySavings = {
    if (this.value ==0.0){
      val e: categorySavings = new categorySavings(this.category, newValue)
      e
    }else {
      val c: Double = roundAt((newValue + this.value) / 2)
      val d: categorySavings = new categorySavings(this.category, c)
      d
    }
  }
}

