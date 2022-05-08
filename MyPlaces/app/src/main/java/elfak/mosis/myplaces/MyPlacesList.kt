package elfak.mosis.myplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import elfak.mosis.myplaces.databinding.ActivityMyPlacesListBinding

class MyPlacesList : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPlacesListBinding
    private var places: MyPlacesData = MyPlacesData
    private val addNewPlace:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        var text = ""
        when(it.resultCode){
            EditMyPlaceActivity.NEW_PLACE_ADDED -> text="New place added!"
            EditMyPlaceActivity.PLACE_UPDATED -> text = "Place updated!"
        }

        if(it.resultCode != Activity.RESULT_CANCELED){
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            findViewById<ListView>(R.id.my_places_list).adapter = ArrayAdapter<MyPlace>(this, android.R.layout.simple_list_item_1, places.getMyPlaces())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPlacesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val arrayAdapter: ArrayAdapter<MyPlace> = ArrayAdapter(this, android.R.layout.simple_list_item_1, places.getMyPlaces())
        findViewById<ListView>(R.id.my_places_list).apply {
            adapter = arrayAdapter
            setOnItemClickListener{ _, _, i, _ ->
                val bundle = Bundle()
                bundle.putInt("position", i)
                val intent = Intent(this@MyPlacesList, ViewMyPlacesActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            setOnCreateContextMenuListener { menu, v, menuInfo ->
                val place = places.getPlace((menuInfo as AdapterView.AdapterContextMenuInfo).position)

                menu.apply {
                    setHeaderTitle(place.name)
                    add(0, 1, 1, "View place")
                    add(0, 2, 2, "Edit place")
                }
            }
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{ addNewPlace.launch(Intent(this, EditMyPlaceActivity::class.java)) }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = (item.menuInfo as AdapterView.AdapterContextMenuInfo)
        val posBundle = Bundle()
        posBundle.putInt("position", info.position)

        val intent = Intent()
        intent.putExtras(posBundle)

        when(item.itemId){
            1 ->  {
                intent.setClass(this, ViewMyPlacesActivity::class.java)
                startActivity(intent)
            }
            2 -> {
                intent.setClass(this, EditMyPlaceActivity::class.java)
                addNewPlace.launch(intent)
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_places_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_show_map -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_new_place -> addNewPlace.launch(Intent(this, EditMyPlaceActivity::class.java))
            R.id.action_about -> startActivity(Intent(this, About::class.java))
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}