package elfak.mosis.myplaces

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import elfak.mosis.myplaces.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.EditFragment || destination.id == R.id.ViewFragment)
                binding.fab.hide()
            else
                binding.fab.show()
        }

        binding.fab.setOnClickListener { _ ->
            when(navController.currentDestination?.id){
                R.id.HomeFragment -> navController.navigate(R.id.action_HomeFragment_to_EditFragment)
                R.id.ListFragment -> navController.navigate(R.id.action_ListFragment_to_EditFragment)
                R.id.MapFragment ->  navController.navigate(R.id.action_MapFragment_to_EditFragment)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_show_map -> {
                when(navController.currentDestination?.id){
                    R.id.HomeFragment -> navController.navigate(R.id.action_HomeFragment_to_MapFragment)
                    R.id.ListFragment -> navController.navigate(R.id.action_ListFragment_to_MapFragment)
                }
            }
            R.id.action_my_places_list -> {
                when(navController.currentDestination?.id){
                    R.id.HomeFragment -> navController.navigate(R.id.action_HomeFragment_to_ListFragment)
                }
            }
            R.id.action_new_place -> {
                when(navController.currentDestination?.id){
                    R.id.HomeFragment -> navController.navigate(R.id.action_HomeFragment_to_EditFragment)
                }
            }
            R.id.action_about -> {
                Toast.makeText(this, "About!", Toast.LENGTH_SHORT).show()
                val i: Intent = Intent(this, About::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}