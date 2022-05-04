package elfak.mosis.myplace

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import elfak.mosis.myplace.databinding.ActivityMyPlacesListBinding

class MyPlacesList : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMyPlacesListBinding
    private lateinit var places: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPlacesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        places = arrayOf("Tvrdjava", "Cair", "Park Svetog Save", "Trg Kralja Milana")
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, places)
        findViewById<ListView>(R.id.my_places_list).adapter = arrayAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my_places_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_show_map_item -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_new_place_item -> Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show()
            R.id.action_about_item -> {
                val i: Intent = Intent(this, About::class.java)
                startActivity(i)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}