package elfak.mosis.myplaces

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.model.MyPlacesViewModel

class EditFragment : Fragment() {

    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_my_places_list -> {
                this.findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_new_place)
        item.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editName: EditText = requireView().findViewById(R.id.editmyplace_name_edit)
        val editDesc: EditText = requireView().findViewById(R.id.editmyplace_description_edit)
        val addButton: Button = requireView().findViewById(R.id.editmyplace_finish_button)
        val cancelButton: Button = requireView().findViewById(R.id.editmyplace_cancel_button)

        addButton.apply {
            setOnClickListener {
                val name: String = editName.text.toString()
                val desc: String = editDesc.text.toString()
                if (myPlacesViewModel.editing) {
                    myPlacesViewModel.selected!!.name = name
                    myPlacesViewModel.selected!!.description = desc
                } else
                    myPlacesViewModel.addPlace(MyPlace(name, desc))
                findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
            }
            isEnabled = false
        }

        if (myPlacesViewModel.editing) {
            addButton.text = "Done"
            editName.setText(myPlacesViewModel.selected!!.name)
            editDesc.setText(myPlacesViewModel.selected!!.description)
        }
        else
            (activity as AppCompatActivity).supportActionBar?.title = "Add New Place"

        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
        }

        val txtWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                addButton.isEnabled =
                    (!myPlacesViewModel.editing && editName.text.isNotBlank() && editDesc.text.isNotBlank())
                            || (myPlacesViewModel.editing && (editName.text.toString() != myPlacesViewModel.selected!!.name || editDesc.text.toString() != myPlacesViewModel.selected!!.description))
            }
        }

        editName.addTextChangedListener(txtWatcher)
        editDesc.addTextChangedListener(txtWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myPlacesViewModel.editing = false
    }
}