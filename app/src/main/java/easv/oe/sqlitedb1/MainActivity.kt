package easv.oe.sqlitedb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import easv.oe.sqlitedb1.data.BEPerson
import easv.oe.sqlitedb1.data.IPersonDao
import easv.oe.sqlitedb1.data.PersonDao_Impl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "xyz"
    }

    var filterActive: Boolean = false

    lateinit var mRep: IPersonDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRep = PersonDao_Impl(this)
        insertTestData()

        setAdapterforListView(mRep.getAll())

    }

    private fun insertTestData() {
        mRep.insert(BEPerson(0,"Rip", 3))
        mRep.insert(BEPerson(0,"Rap", 3))
        mRep.insert(BEPerson(0,"Rup", 3))
    }


    var cache: List<BEPerson> = ArrayList<BEPerson>()

    private fun setAdapterforListView(persons: List<BEPerson>) {
        Log.d(TAG, "Listview initialized")
        val asStrings = persons.map { p -> "${p.id}, ${p.name}"}
        val adapter: ListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            asStrings.toTypedArray()
        )
        lvNames.adapter = adapter
        lvNames.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ -> onClickPerson(pos)}
        cache = persons
    }


    private fun onClickPerson(pos: Int) {
        val person = cache[pos]
        Toast.makeText(this, "You have clicked ${person} ", Toast.LENGTH_LONG).show()
    }

    fun onClickInsert(view: View) {
        mRep.insert(BEPerson(0, etName.text.toString(), 23))
        setAdapterforListView(mRep.getAll())

    }

    fun onClickClear(view: View) {
        mRep.deleteAll()
        setAdapterforListView(mRep.getAll())
    }

    fun onClickFilter(view: View){
        if (! filterActive ) {

            //val persons = mRep.getAll().filter { p -> p.name.contains(etName.text.toString()) }
            val persons = mRep.getByName(etName.text.toString())
            setAdapterforListView(persons)
            filterActive = true
            btnFilter.text = resources.getString(R.string.remove_filter)
        } else
        {
            setAdapterforListView(mRep.getAll())
            filterActive = false
            btnFilter.text = resources.getString(R.string.use_filter)
        }
    }
}