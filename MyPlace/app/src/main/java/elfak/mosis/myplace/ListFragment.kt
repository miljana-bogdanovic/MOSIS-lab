package elfak.mosis.myplace

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplace.data.MyPlace
import elfak.mosis.myplace.databinding.FragmentListBinding
import elfak.mosis.myplace.model.MyPlacesViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
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
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPlacesList: ListView = requireView().findViewById(R.id.my_places_list)
        myPlacesList.adapter= ArrayAdapter(view.context, android.R.layout.simple_list_item_1, myPlacesViewModel.myPlacesList)

        myPlacesList.onItemClickListener =
            AdapterView.OnItemClickListener { p0, _, p2, _ ->
                val myPlace :MyPlace = p0?.adapter?.getItem(p2) as MyPlace
                myPlacesViewModel.selected=myPlace
                view.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
            }
        myPlacesList.setOnCreateContextMenuListener { p0, _, p2 ->
            val info = p2 as AdapterView.AdapterContextMenuInfo
            val myPlace: MyPlace = myPlacesViewModel.myPlacesList[info.position]
            p0?.setHeaderTitle(myPlace.name)
            p0?.add(0, 1, 1, "View place")
            p0?.add(0, 2, 2, "Edit place")
            p0?.add(0, 3, 3, "Delete place")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.action_my_places_list)
        item.isVisible=false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_new_place -> {
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            1 -> {
                myPlacesViewModel.selected=myPlacesViewModel.myPlacesList[info.position]
                this.findNavController().navigate(R.id.action_ListFragment_to_ViewFragment)
            }
            2 -> {
                myPlacesViewModel.selected=myPlacesViewModel.myPlacesList[info.position]
                this.findNavController().navigate(R.id.action_ListFragment_to_EditFragment)
            }
            3 -> {
                Toast.makeText(this.context, "Delete item", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }
}