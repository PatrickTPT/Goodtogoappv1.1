package com.example.goodtogoappv11

import android.util.Base64
import com.example.goodtogoappv11.model.Constants

fun main(){



    val base64Encoded = Base64.encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray(), 0)

    println(Constants.SECRET_KEY_CURRENT)
    println(base64Encoded)
    println(base64Encoded.length)

        /* Box Generator
        var currentCupCount = Constants.randomCupNumberStand
        val arrayList = ArrayList<Boxes.BoxChild>()

        for (i in 1..10) {
            arrayList.add(
                Boxes.BoxChild("大器杯", currentCupCount + i, Constants.BOX_STATUS_BOXED))
        }

        println(arrayList)*/


    /* Filter & arraylist
    val arrayList = ArrayList<Box>()
    val displayList = ArrayList<Box>()
    arrayList.addAll(Datasource().loadBoxes())
    Constants.mediumList.addAll(arrayList)
    val filteredmediumList = Constants.mediumList.filter{ it.status == Constants.BOX_STATUS_TODELIVER}
    displayList.addAll(filteredmediumList)
    println(displayList)
    */

/**val pos = mediumList[indexof(3124014)].boxid*/



    //var base64Key = BASE64.encode(Constants.SECRET_KEY_CURRENT.getEncoded())
    //val base64Encoded = Base64.getEncoder().encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray())
    //val hmacKey: ByteArray = Constants.SECRET_KEY_CURRENT.toByteArray(StandardCharsets.UTF_8)
    //var key : Key = SecretKeySpec(Constants.SECRET_KEY_CURRENT.toByteArray(),SignatureAlgorithm.HS256.getJcaName())
    //not working
    //var decodedKey: ByteArray = Base64.getDecoder().decode(Constants.SECRET_KEY_CURRENT)
    //val originalKey : SecretKey = SecretKeySpec(decodedKey,0,decodedKey.size,"AES")
    //not working 2
    //var encodedKey :ByteArray = android.util.Base64.decode(Constants.SECRET_KEY_CURRENT, android.util.Base64.DEFAULT)
    //val originalKey: SecretKey = SecretKeySpec(encodedKey,0,encodedKey.size,"")
    //
    //var base64Key = Encoders.BASE64.encode(Constants.SECRET_KEY_CURRENT.encode().toByteArray())
}