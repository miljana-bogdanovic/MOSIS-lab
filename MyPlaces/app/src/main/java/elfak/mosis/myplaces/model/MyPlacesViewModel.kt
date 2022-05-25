package elfak.mosis.myplaces.model

import androidx.lifecycle.ViewModel
import elfak.mosis.myplaces.data.MyPlace

class MyPlacesViewModel: ViewModel() {
    var myPlacesList: ArrayList<MyPlace> = ArrayList<MyPlace>()
    var selected: MyPlace? = null
    var editing: Boolean = false

    fun addPlace(place: MyPlace): Unit {
        myPlacesList.add(place)
    }
}