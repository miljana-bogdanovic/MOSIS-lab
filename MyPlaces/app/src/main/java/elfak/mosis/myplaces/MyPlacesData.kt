package elfak.mosis.myplaces

object MyPlacesData {
    private var myPlaces: MutableList<MyPlace> =
        mutableListOf(MyPlace("Place A"), MyPlace("Place B"), MyPlace("Place C"))

    fun getMyPlaces(): List<MyPlace> = myPlaces
    fun getPlace(index: Int): MyPlace = myPlaces[index]
    fun deletePlace(index: Int): MyPlace = myPlaces.removeAt(index)
    fun addNewPlace(newPlace: MyPlace) {
        myPlaces.add(newPlace)
    }
}