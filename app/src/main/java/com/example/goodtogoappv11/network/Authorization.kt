package com.example.goodtogoappv11.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.goodtogoappv11.model.Constants
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

open class Authorization {

    @RequiresApi(Build.VERSION_CODES.O)
    fun standardAuth(): String {
        val d = Date()
        d.time = d.time + 259200


        var decodedKey: ByteArray = Base64.getDecoder().decode(Constants.MY_SECRET_KEY_STATION)
        val originalKey : SecretKey = SecretKeySpec(decodedKey,0,decodedKey.size,"AES")

        /*return Jwts.builder()
            .setId(Constants.reqID())
            .setIssuedAt(Date())
            .setExpiration(d)
            .signWith(originalKey)
            .compact()*/
        return ""
    }

    fun standardTime(){

    }


    /*fun standardAddOrderTime(date: Long): String {
        val d = Date()
        d.time = d.time + 259200
        var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        //var base64Key = Encoders.BASE64.encode(key.getEncoded())
        return Jwts.builder().setId(reqID())
            .setIssuedAt(Date())
            .setExpiration(d)
            .claim("orderTime", date)
            .signWith(secretKey,SignatureAlgorithm.HS256)
            .compact()
    }*/

}