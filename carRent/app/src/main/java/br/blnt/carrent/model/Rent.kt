package br.blnt.carrent.model

class Rent(val id:Int, val userId:Int, val carId:Int, val dateStart:String, var dateEnd:String, var payed:Boolean) {
  override fun toString(): String {
    return "Rent(id=$id, userId=$userId, carId=$carId, dateStart='$dateStart', dateEnd='$dateEnd', payed=$payed)"
  }
}