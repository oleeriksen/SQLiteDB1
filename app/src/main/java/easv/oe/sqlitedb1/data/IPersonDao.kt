package easv.oe.sqlitedb1.data

interface IPersonDao {

    fun getAll(): Array<BEPerson>

    fun getByName(s:String): Array<BEPerson>

    fun getAllNames(): Array<String>

    fun getById(id: Int): BEPerson

    fun insert(p: BEPerson)

    fun update(p: BEPerson)

    fun delete(p: BEPerson)

    fun deleteAll()
}