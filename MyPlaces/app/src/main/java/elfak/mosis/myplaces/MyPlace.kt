package elfak.mosis.myplaces

class MyPlace(private var _name:String, private var _description: String = "") {

    var name: String
        set(value) :Unit {
            _name = value
        }
        get() :String = _name

    var description: String
        set(value) :Unit {
            _description = value
        }
        get() :String = _description

    override fun toString(): String = name
}