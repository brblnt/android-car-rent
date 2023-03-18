package br.blnt.carrent.model

class User(val id:Int,val userName:String,val password:String) {
  override fun toString(): String {
    return "User(id=$id, userName='$userName', password='$password')"
  }
}