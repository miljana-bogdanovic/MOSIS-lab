package elfak.mosis.myplace.model

import androidx.lifecycle.ViewModel
import elfak.mosis.myplace.data.MyPlace

class MyPlacesViewModel : ViewModel() {
    var myPlacesList : ArrayList<MyPlace> = ArrayList<MyPlace>()
    fun addPlace(place : MyPlace){
        myPlacesList.add(place)
    }

}