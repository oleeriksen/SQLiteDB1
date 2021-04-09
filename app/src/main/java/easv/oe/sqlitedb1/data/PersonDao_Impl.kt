package easv.oe.sqlitedb1.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import easv.oe.sqlitedb1.MainActivity
import java.lang.IllegalArgumentException


class PersonDao_Impl : IPersonDao {

    var mDatabase: SQLiteDatabase

    constructor(context: Context) {
        val openHelper = MyOpenHelper(context)
        mDatabase = openHelper.writableDatabase

    }
    override fun getAll(): List<BEPerson> {
        val result = ArrayList<BEPerson>()
        //val query = "select * from Person order by id"
        val cursor = mDatabase.query("Person", arrayOf("id", "name", "age"), null, null, null, null, "id")
        //val cursor = mDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id")).toInt()
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getString(cursor.getColumnIndex("age")).toInt()
                result.add(BEPerson(id, name, age))
            } while (cursor.moveToNext())

        }
        Log.d(MainActivity.TAG, "Dao Impl - getAll() returned ${result.size} persons")
        return result
    }

    override fun getAllNames(): List<String> {
        return getAll().map { p -> p.name }
    }

    override fun getById(id: Int): BEPerson {
        val cursor = mDatabase.query("Person", arrayOf("id", "name", "age"), "id=$id", null, null, null, "id")
        //val cursor = mDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
                val id = cursor.getString(cursor.getColumnIndex("id")).toInt()
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val age = cursor.getString(cursor.getColumnIndex("age")).toInt()
                return BEPerson(id, name, age)
        }
        else {
            Log.d(MainActivity.TAG, "Dao Impl - getById failed - no person with id = $id")
            throw IllegalArgumentException()
        }
        //Easy impl
        //return getAll().filter {p -> p.id == id}[0]
    }

    override fun insert(p: BEPerson) {
        val cv = ContentValues()
        //cv.put("id", p.id)
        cv.put("name", p.name)
        cv.put("age", p.age)
        val result = mDatabase.insert("person", null, cv)
        if (result > 0.toLong()) {
            p.id = result.toInt()
            Log.d(MainActivity.TAG, "Dao Impl - insert person - id given = $result")
        }
        else
            Log.d(MainActivity.TAG, "Dao Impl - insert person FAILED")

    }

    override fun update(p: BEPerson) {

    }

    override fun delete(p: BEPerson) {

    }

    override fun deleteAll() {
        mDatabase.delete("person", null, null)
    }

    inner class MyOpenHelper(context: Context) :
        SQLiteOpenHelper(context,"personDB", null, 1) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL("CREATE TABLE Person(id INTEGER PRIMARY KEY, name TEXT, age INTEGER)")
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            onCreate(db)
        }

    }
}