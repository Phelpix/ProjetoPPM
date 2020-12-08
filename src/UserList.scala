trait UserList{
 val value: Double
 val category: String
 val description: String
 val date: String

 def setValue(sum:UserList, newvalue:Double): UserList ={
  new UserList {
   override val value: Double = newvalue
   override val category: String = sum.category
   override val description: String = sum.description
   override val date: String = sum.date
  }
 }

 def setDescription(sum:UserList,desc:String):UserList={
  new UserList {
   override val value: Double = sum.value
   override val category: String = sum.category
   override val description: String = desc
   override val date: String = sum.date
  }
 }

 def setCategory(sum:UserList,cat:String):UserList={
  new UserList {
   override val value: Double = sum.value
   override val category: String = cat
   override val description: String = sum.description
   override val date: String = sum.date
  }
 }

}