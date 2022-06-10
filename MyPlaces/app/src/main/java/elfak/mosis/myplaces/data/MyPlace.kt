package elfak.mosis.myplaces.data

data class MyPlace(var name: String, var description: String, var longitude: Double, var latitude: Double){
    override fun toString(): String = name
}
