package easv.oe.sqlitedb1.data

interface IPersonDao {

    fun getAll(): List<BEPerson>

    fun getByName(s:String): List<BEPerson>

    fun getAllNames(): List<String>

    fun getById(id: Int): BEPerson

    fun insert(p: BEPerson)

    fun update(p: BEPerson)

    fun delete(p: BEPerson)

    fun deleteAll()
}