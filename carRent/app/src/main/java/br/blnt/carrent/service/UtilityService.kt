package br.blnt.carrent

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import br.blnt.carrent.model.Car
import br.blnt.carrent.model.Rent
import br.blnt.carrent.model.Stat
import br.blnt.carrent.model.User
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private var database : SQLiteDatabase? = null
private val dates = SimpleDateFormat("yyyy/MM/dd")
private var users = ArrayList<User>()
private var rents = ArrayList<Rent>()

var stats = ArrayList<Stat>()
var cars = ArrayList<Car>()
var userCars = ArrayList<Car>()
var actUserId = -1



/**
 * Setup run only the application starting.
 * This fun setup the stat. If something changed in stat need to restart app to see updated data.
 */
fun setup(db: SQLiteDatabase) {
    database = db
    users = ArrayList()
    cars = ArrayList()
    rents = ArrayList()

    val rsUsers = db.rawQuery("SELECT * FROM USERS", null)
    val rsCars = db.rawQuery("SELECT * FROM CARS", null)
    val rsRents = db.rawQuery("SELECT * FROM RENTS", null)

    while (rsUsers.moveToNext()) {
        users.add(User(
            rsUsers.getString(0).toInt(),
            rsUsers.getString(1),
            rsUsers.getString(2)))
    }
    while (rsCars.moveToNext()) {
        cars.add(Car(
            rsCars.getString(0).toInt(),
            rsCars.getString(1),
            rsCars.getString(2).toInt(),
            rsCars.getString(3).toInt(),
            rsCars.getString(4),
            rsCars.getString(5)))
    }
    while (rsRents.moveToNext()) {
        rents.add(Rent(
            rsRents.getString(0).toInt(),
            rsRents.getString(1).toInt(),
            rsRents.getString(2).toInt(),
            rsRents.getString(3),
            rsRents.getString(4),
            rsRents.getString(5).toBoolean()))
    }

    for (rent in rents) {
        var cont = false
        for (stat in stats) {
            if (stat.carId == rent.carId) {
                cont = true
            }
        }
        if (cont) {
            for (stat in stats) {
                if (stat.carId == rent.carId) {
                    stat.rentsNumber +=1
                }
            }
        } else {
            stats.add(Stat(rent.carId,1))
        }
    }
}

/**
 * Login function, auth. the user and fill the user cars array.
 */
fun login(username: String, password: String): Int {
    for (temp in users) {
        if (temp.userName.lowercase() == username.lowercase() && temp.password == password) {
            actUserId = temp.id
            for (car in cars) {
                try {
                    if (car.available.toInt() == actUserId)
                        userCars.add(car)
                } catch (e: NumberFormatException) {
                    //if give back error need to check the code and database
                }
            }
            return temp.id
        }
    }
    actUserId = -1
    return -1
}

/**
 * Check user exist in the app.
 * If exist registration give back warning.
 */
fun userExist(username: String): Boolean {
    for (temp in users) {
        if (temp.userName.lowercase() == username.lowercase()) {
            return true
        }
    }
    return false
}

/**
 * Add user to the app and insert new record to the db.
 */
fun addUser(username: String, password: String) {
    val id = users[users.size-1].id+1
    users.add(User(id, username, password))

    val cv = ContentValues()
        cv.put("ID", id)
        cv.put("USERNAME", username)
        cv.put("PASSWORD", password)
    database?.insert("USERS", null, cv)
}

/**
 * Exit function. Set actual user id to -1 and clear user's car array.
 */
fun exit() {
    actUserId = -1
    userCars = ArrayList()
}

/**
 * Return rent start date in String format. If rent does not exist return 'na'.
 */
fun getDateStart(ID: Int) : String {
    for (rent in rents) {
        if(rent.id == ID)
            return rent.dateStart
    }
    return "na"
}

/**
 * Return rent end date in String format.
 * If rent does not exist or the rent is active (user still own the car) return 'na'.
 */
fun getDateEnd(ID: Int) : String {
    for (rent in rents) {
        if(rent.id == ID)
            return rent.dateEnd
    }
    return "na"
}

/**
 * Calculate the price of the rent.
 * Basic price + day price * day since the rental date.
 */
fun getPay(userID: Int, carID: Int) : Int{
    var price = 0
    var dayprice = 0
    var days = 0
    for (car in cars) {
        if(carID == car.id) {
            dayprice = car.dayPrice
            price += car.basePrice
        }
    }

    val id = getActualRent(userID, carID)
    val date2: Date
    val date1: Date = dates.parse(getDateStart(id))
    var endDate = getDateEnd(id)
    if (endDate == "na") {
        endDate = dates.format(Date())
    }
    date2 = dates.parse(endDate)

    val difference: Long = kotlin.math.abs(date1.time - date2.time)
    val differenceDates = difference / (24 * 60 * 60 * 1000)
    days = differenceDates.toInt()

    return (price + (dayprice * days))
}

fun getPayByRent(rentID: Int, carID: Int) : Int {
    var price = 0
    var dayprice = 0
    var days = 0
    for (car in cars) {
        if (carID == car.id) {
            dayprice = car.dayPrice
            price += car.basePrice
        }
    }

    val id = rentID
    val date2: Date
    val date1: Date = dates.parse(getDateStart(id))
    var endDate = getDateEnd(id)
    if (endDate == "na") {
        endDate = dates.format(Date())
    }
    date2 = dates.parse(endDate)

    val difference: Long = kotlin.math.abs(date1.time - date2.time)
    val differenceDates = difference / (24 * 60 * 60 * 1000)
    days = differenceDates.toInt()

    return (price + (dayprice * days))
}

/**
 * Get actual rent. Because rent may have
 * more rent with the same car and user is give back the last.
 * The last item is the latest insert in the array.
 */
fun getActualRent(userID: Int, carID: Int) : Int {
    var id = 0
    for (rent in rents) {
        if (userID == rent.userId && carID == rent.carId){
            id = rent.id
        }
    }
    return id
}

/**
 * Set car status to available.
 * Set actual rent to finished (insert end date and change payment state/later/).
 * Remove car from user's car.
 */
fun giveBackCar(carId: Int) {
    for (car in cars) {
        if (car.id == carId) {
            car.available = "na"
            val cv = ContentValues()
                cv.put("ID", car.id)
                cv.put("CARNAME", car.carName)
                cv.put("BASEPRICE", car.basePrice)
                cv.put("DAYPRICE", car.dayPrice)
                cv.put("AVAILABLE", "na")
                cv.put("PICTURE", car.picture)
            database?.update("CARS", cv, "id = ?", arrayOf(car.id.toString()))
        }
    }

    for (rent in rents) {
        if(getActualRent(actUserId, carId) == rent.id && rent.dateEnd == "na") {
            val endDate = dates.format(Date())
            rent.dateEnd = endDate
            rent.payed = true
            val cv = ContentValues()
                cv.put("ID", rent.id)
                cv.put("USERID", rent.userId)
                cv.put("CARID", rent.carId)
                cv.put("DATESTART", rent.dateStart)
                cv.put("DATEEND", rent.dateEnd)
                cv.put("PAYED", rent.payed)
            database?.update("RENTS", cv, "id = ?", arrayOf(rent.id.toString()))
        }
    }
    for(i in 0 until userCars.size) {
        if (userCars[i].id == carId) {
            userCars.removeAt(i)
            break
        }
    }
    println("--------------------" + getCarEarn(carId) + "---------------------")
}

/**
 * Insert new rent with start date.
 */
fun rentCar(carId: Int) {
    for (car in cars) {
        if (car.id == carId) {
            car.available = actUserId.toString()
            val cv = ContentValues()
                cv.put("ID", car.id)
                cv.put("CARNAME", car.carName)
                cv.put("BASEPRICE", car.basePrice)
                cv.put("DAYPRICE", car.dayPrice)
                cv.put("AVAILABLE", actUserId.toString())
                cv.put("PICTURE", car.picture)
            database?.update("CARS", cv, "id = ?", arrayOf(car.id.toString()))
            userCars.add(car)
        }
    }
    val id : Int = rents.size +1
    rents.add(Rent(id, actUserId,carId,dates.format(Date()),"na",false))

    val cv = ContentValues()
        cv.put("ID", id)
        cv.put("USERID", actUserId)
        cv.put("CARID", carId)
        cv.put("DATESTART", dates.format(Date()))
        cv.put("DATEEND", "na")
        cv.put("PAYED", false)
    database?.insert("RENTS", null, cv)

}

/**
 * Get drawable for the car.
 */
fun drawRes(actCar: Car) : Int {
    val drawRes = when(actCar.picture) {
        "mercedes" -> R.drawable.mercedes
        "taycan" -> R.drawable.taycan
        "evo" -> R.drawable.evo
        "m4" -> R.drawable.m4
        "supra" -> R.drawable.supra
        else -> {
            R.drawable.nopic
        }
    }
    return drawRes
}

/**
 * Calculate total earn by car.
 */
fun getCarEarn(carID: Int) : Int {
    var sum = 0
    for (rent in rents) {
        if (rent.carId == carID) {
            sum += getPayByRent(rent.id, rent.carId)
        }
    }
    return sum
}
