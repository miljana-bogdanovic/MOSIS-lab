package elfak.mosis.myplaces

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplaces.data.MyPlace
import elfak.mosis.myplaces.databinding.FragmentListBinding
import elfak.mosis.myplaces.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val myPlacesViewModel: MyPlacesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPlacesList: ListView = requireView().findViewById<ListView>(R.id.my_places_list)
        myPlacesList.adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, myPlacesViewModel.myPlacesList)
        myPlacesList.setOnItemClickListener{ adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            myPlacesViewModel.selected = adapterView?.adapter?.getItem(i) as MyPlace
            this.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
        }
        myPlacesList.setOnCreateContextMenuListener{menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo ->
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            val myPlace: MyPlace = myPlacesViewModel.myPlacesList[info.position]
            menu.setHeaderTitle(myPlace.name)
            menu.add(0, 1, 1, R.string.view_fragment_label)
            menu.add(0, 2, 2, R.string.edit_fragment_label)
            menu.add(0, 3, 3, R.string.delete_place_label)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when(item.itemId){
            1 -> {
                myPlacesViewModel.selected = myPlacesViewModel.myPlacesList[info.position]
                this.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
            }
            2 -> {
                myPlacesViewModel.selected = myPlacesViewModel.myPlacesList[info.position]
                myPlacesViewModel.editing = true
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
            }
            3 -> {
                val deleted = myPlacesViewModel.myPlacesList.removeAt(info.position)
                Toast.makeText(this.context, "Deleted $deleted", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_place -> {
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_my_places_list)
        item.isVisible = false
    }
}