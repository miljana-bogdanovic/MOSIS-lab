package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import elfak.mosis.myplaces.databinding.ActivityViewMyPlacesBinding

class ViewMyPlacesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityViewMyPlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewMyPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        try{
            val position = intent.extras!!.getInt("position")
            val place = MyPlacesData.getPlace(position)
            findViewById<TextView>(R.id.viewmyplace_name_text).text = place.name
            findViewById<TextView>(R.id.viewmyplace_desc_text).text = place.description
            findViewById<Button>(R.id.viewmyplace_finished_button).setOnClickListener{ finish() }
        }
        catch (e:Error) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_view_my_place, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.show_map_item -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.about_item -> startActivity(Intent(this, About::class.java))
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}