package br.blnt.carrent.model

class Stat(val carId:Int, var rentsNumber:Int) {
  override fun toString(): String {
    return "Stat(carId=$carId, rentsNumber=$rentsNumber)"
  }
}