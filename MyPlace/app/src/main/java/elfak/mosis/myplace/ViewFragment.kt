package elfak.mosis.myplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import elfak.mosis.myplace.databinding.FragmentViewBinding
import elfak.mosis.myplace.model.MyPlacesViewModel

class ViewFragment : Fragment() {
    private var _binding: FragmentViewBinding? = null
    private val binding get() = _binding!!
    private val myPlacesViewModel : MyPlacesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmyplaceNameText.text=myPlacesViewModel.selected?.name
        binding.viewmyplaceDescText.text=myPlacesViewModel.selected?.descritpion
        binding.viewmyplaceFinishedButton.setOnClickListener{
            myPlacesViewModel.selected= null
            findNavController().navigate(R.id.action_ViewFragment_to_ListFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        myPlacesViewModel.selected=null
    }

}