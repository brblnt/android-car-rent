package br.blnt.carrent.persistance

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context): SQLiteOpenHelper(context, "CARRENT-DB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE USERS(ID INTEGER PRIMARY KEY, USERNAME TEXT, PASSWORD TEXT)"
        )
        db?.execSQL(
            "CREATE TABLE RENTS(ID INTEGER PRIMARY KEY, " +
                    "USERID INTEGER," +
                    "CARID INTEGER," +
                    "DATESTART TEXT," +
                    "DATEEND TEXT," +
                    "PAYED BOOLEAN)"
        )

        db?.execSQL(
            "CREATE TABLE CARS(ID INTEGER PRIMARY KEY, " +
                    "CARNAME TEXT," +
                    "BASEPRICE INTEGER," +
                    "DAYPRICE INTEGER," +
                    "AVAILABLE TEXT," +
                    "PICTURE TEXT)"
        )

        db?.execSQL("INSERT INTO USERS(ID, USERNAME, PASSWORD) VALUES (1, 'BOSS', 'pw')")
        db?.execSQL("INSERT INTO CARS(ID, CARNAME, BASEPRICE, DAYPRICE, AVAILABLE, PICTURE) " +
                "VALUES (1, 'Porsche Taycan 970', 175000, 50000, 'na', 'taycan')")
        db?.execSQL("INSERT INTO CARS(ID, CARNAME, BASEPRICE, DAYPRICE, AVAILABLE, PICTURE) " +
                "VALUES (2, 'Mercedes Benz GLK', 50000, 25000, 'na', 'mercedes')")
        db?.execSQL("INSERT INTO CARS(ID, CARNAME, BASEPRICE, DAYPRICE, AVAILABLE, PICTURE) " +
                "VALUES (3, 'BMW M4 340i', 75000, 30000, 'na', 'm4')")
        db?.execSQL("INSERT INTO CARS(ID, CARNAME, BASEPRICE, DAYPRICE, AVAILABLE, PICTURE) " +
                "VALUES (4, 'Toyota Supra RZ', 250000, 55000, 'na', 'supra')")
        db?.execSQL("INSERT INTO CARS(ID, CARNAME, BASEPRICE, DAYPRICE, AVAILABLE, PICTURE) " +
                "VALUES (5, 'Mitsubishi Lancer EVO', 150000, 25000, 'na', 'evo')")
        db?.execSQL("INSERT INTO RENTS(ID, USERID, CARID, DATESTART, DATEEND, PAYED) " +
                "VALUES (1,1,1,'2021/10/10','2021/10/30' , true)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}