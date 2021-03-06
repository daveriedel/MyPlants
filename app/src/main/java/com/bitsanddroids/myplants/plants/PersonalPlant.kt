package com.bitsanddroids.myplants.plants


import java.io.Serializable
import java.util.Date

class PersonalPlant(val name: String = ""
                    , val imageUrl: String = ""
                    , val sun: Int = 1
                    , val placement: Int = 1
                    , val toxic: Int = 1
                    , val water: Int = 1
                    , val edible: Int = 1
                    , var watered: Date? = null
                    , val plantID: String? = null
                    , var placementNote: String? = ""
                    , val plantingTime: String? = ""
                    , val userID: String? = "") : Serializable {

}
