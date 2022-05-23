package elfak.mosis.myplace

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import elfak.mosis.myplace.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            if (destination.id== R.id.EditFragment || destination.id==R.id.ViewFragment )
                binding.fab.hide()
            else
                binding.fab.show()
        }
        binding.fab.setOnClickListener {
            if (navController.currentDestination?.id== R.id.HomeFragment)
               navController.navigate(R.id.action_HomeFragment_to_EditFragment)
            else if (navController.currentDestination?.id== R.id.ListFragment)
                navController.navigate(R.id.action_ListFragment_to_EditFragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId)
        {
            R.id.action_show_map -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_new_place -> Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show()
            R.id.action_my_places_list ->
            {
                //this.findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.action_HomeFragment_to_ListFragment)
            }
            R.id.action_about ->
            {
                val i = Intent(this,About::class.java)
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