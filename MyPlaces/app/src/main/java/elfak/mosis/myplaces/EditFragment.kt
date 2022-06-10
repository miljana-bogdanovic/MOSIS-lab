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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.model.LocationViewModel
import elfak.mosis.myplaces.model.MyPlacesViewModel

class EditFragment : Fragment() {

    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

        val editName: EditText = requireView().findViewById(R.id.editmyplace_name_edit)
        val editDesc: EditText = requireView().findViewById(R.id.editmyplace_description_edit)
        val editLongitude: EditText = requireView().findViewById(R.id.editmyplace_longitude_edit)
        val editLatitude: EditText = requireView().findViewById(R.id.editmyplace_latitude_edit)
        val addButton: Button = requireView().findViewById(R.id.editmyplace_finish_button)
        val cancelButton: Button = requireView().findViewById(R.id.editmyplace_cancel_button)
        val setButton: Button = requireView().findViewById(R.id.editmyplace_location_button)

        val longitudeObs = Observer<String> {
            editLongitude.setText(it)
        }
        val latitudeObs = Observer<String>{
            editLatitude.setText(it)
        }

        locationViewModel.longitude.observe(viewLifecycleOwner, longitudeObs)
        locationViewModel.latitude.observe(viewLifecycleOwner, latitudeObs)

        addButton.apply {
            setOnClickListener {
                val name: String = editName.text.toString()
                val desc: String = editDesc.text.toString()
                val longitude = editLongitude.text.toString().toDouble()
                val latitude = editLatitude.text.toString().toDouble()
                if (myPlacesViewModel.editing) {
                    myPlacesViewModel.selected!!.name = name
                    myPlacesViewModel.selected!!.description = desc
                    myPlacesViewModel.selected!!.longitude = longitude
                    myPlacesViewModel.selected!!.latitude = latitude
                } else
                    myPlacesViewModel.addPlace(MyPlace(name, desc, longitude, latitude))
                findNavController().popBackStack()
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
            findNavController().popBackStack()
        }

        setButton.setOnClickListener{
            locationViewModel.setLocation = true
            findNavController().navigate(R.id.action_EditFragment_to_MapFragment)
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
        locationViewModel.setLocation("", "")
    }
}