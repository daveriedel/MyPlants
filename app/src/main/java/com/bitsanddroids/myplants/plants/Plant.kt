package com.bitsanddroids.myplants.plants

import java.io.Serializable


class Plant(val name: String = "", val imageUrl: String = "", val sun: Int = 1
            , val placement: Int = 1, val toxic: Int = 1, val water: Int = 1
            , val edible: Int = 1, val plantingTime: String = "") : Serializable {

    var plantID: String? = null
        set(plantID) {
            field = this.plantID
        }


}

