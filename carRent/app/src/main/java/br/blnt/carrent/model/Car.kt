package br.blnt.carrent.model

class Car(val id:Int, val carName:String, val basePrice:Int, val dayPrice:Int,
          var available:String, val picture:String) {
  override fun toString(): String {
    return "Car(id=$id, carName='$carName', basePrice=$basePrice, dayPrice=$dayPrice, available='$available', picture='$picture')"
  }
}