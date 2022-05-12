package elfak.mosis.myplace

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplace.R.id.action_my_places_list
import elfak.mosis.myplace.data.MyPlace
import elfak.mosis.myplace.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [EditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton: Button = requireView().findViewById<Button>(R.id.edit_myplace_finished_button)
        addButton.setOnClickListener{
            val editName: EditText=requireView().findViewById<EditText>(R.id.editmyplace_name_edit)
            val name: String=editName.text.toString()
            val editDesc: EditText=requireView().findViewById<EditText>(R.id.editmyplace_desc_edit)
            val desc: String=editDesc.text.toString()
            myPlacesViewModel.addPlace(MyPlace(name, desc))
            findNavController().navigate(R.id.action_EditFragment_to_ListFragment)
        }
        val cancelButton: Button = requireView().findViewById<Button>(R.id.edit_myplace_cancel_button)
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

}