package elfak.mosis.myplaces

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import elfak.mosis.myplaces.databinding.ActivityEditMyPlaceBinding
import java.lang.NullPointerException

class EditMyPlaceActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        const val NEW_PLACE_ADDED: Int = 2
        const val PLACE_UPDATED: Int = 4
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityEditMyPlaceBinding
    private var editMode:Boolean = false
    private var myPlace: MyPlace? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditMyPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        try {
            editMode = true
            myPlace = MyPlacesData.getPlace(intent.extras!!.getInt("position"))
            findViewById<EditText>(R.id.editmyplace_name_edit).setText(myPlace!!.name)
            findViewById<EditText>(R.id.editmyplace_desc_edit).setText(myPlace!!.description)
        }
        catch(e: NullPointerException) {
            editMode = false
        }

        findViewById<Button>(R.id.editmyplace_cancel_button).setOnClickListener(this)
        val finishButton = findViewById<Button>(R.id.editmyplace_finish_button).apply {
            setOnClickListener(this@EditMyPlaceActivity)
            isEnabled = editMode
            text = if (editMode) "Save" else "Add"
        }

        findViewById<EditText>(R.id.editmyplace_name_edit).addTextChangedListener {
            finishButton.isEnabled = (!editMode && it!!.isNotEmpty()) || (editMode && it.toString() != myPlace!!.name)
        }
        findViewById<EditText>(R.id.editmyplace_desc_edit).addTextChangedListener {
            finishButton.isEnabled = (!editMode && it!!.isNotEmpty()) || (editMode && it.toString() != myPlace!!.description)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.editmyplace_finish_button -> {
                val name = findViewById<EditText>(R.id.editmyplace_name_edit).text.toString()
                val desc = findViewById<EditText>(R.id.editmyplace_desc_edit).text.toString()

                if(!editMode) {
                    MyPlacesData.addNewPlace(MyPlace(name, desc))
                    setResult(NEW_PLACE_ADDED)
                }
                else {
                    myPlace!!.apply {
                        this.name = name
                        this.description = desc
                    }
                    setResult(PLACE_UPDATED)
                }

                finish()
            }
            R.id.editmyplace_cancel_button -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            android.R.id.home -> finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_show_map -> Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show()
            R.id.action_my_places_list -> startActivity(Intent(this, MyPlacesList::class.java))
            R.id.action_about -> startActivity(Intent(this, About::class.java))
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}