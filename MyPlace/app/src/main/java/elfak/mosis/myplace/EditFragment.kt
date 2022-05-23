package elfak.mosis.myplace

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplace.R.id.action_my_places_list
import elfak.mosis.myplace.data.MyPlace
import elfak.mosis.myplace.model.MyPlacesViewModel

class EditFragment : Fragment() {
    private val myPlacesViewModel : MyPlacesViewModel by activityViewModels()

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
        if (myPlacesViewModel.selected == null)
            (activity as AppCompatActivity).supportActionBar?.title = "Add My Place"
        else
            (activity as AppCompatActivity).supportActionBar?.title = "Edit My Place"
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton: Button = requireView().findViewById(R.id.edit_myplace_finished_button)
        addButton.isEnabled = false
        val editName: EditText=requireView().findViewById(R.id.editmyplace_name_edit)
        val editDesc: EditText=requireView().findViewById(R.id.editmyplace_desc_edit)
        if (myPlacesViewModel.selected!=null)
            addButton.setText(R.string.editmyplace_save_label)
        editName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                addButton.isEnabled=(editName.text.isNotEmpty())
            }
        })
        addButton.setOnClickListener{
            val name: String=editName.text.toString()
            val desc: String=editDesc.text.toString()
            if (myPlacesViewModel.selected!=null) {
                myPlacesViewModel.selected!!.name=name
                myPlacesViewModel.selected!!.descritpion=desc
            }
            else
                myPlacesViewModel.addPlace(MyPlace(name, desc))
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
        }
        val cancelButton: Button = requireView().findViewById(R.id.edit_myplace_cancel_button)
        cancelButton.setOnClickListener{
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_new_place)
        item.isVisible=false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            action_my_places_list -> {
                this.findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        myPlacesViewModel.selected=null
    }

}