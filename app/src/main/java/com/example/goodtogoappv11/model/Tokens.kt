package com.example.goodtogoappv11.model

import android.util.Base64
import com.example.goodtogoappv11.model.Constants.mySecretKey
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object Tokens {

    fun standardAut(): String {

        //val d = Date()
        //d.time = d.time + 259200

        val cal = Calendar.getInstance(Locale.TAIWAN)
        cal.time = Date()
        val millis = cal.timeInMillis
        val expmillis = millis+259200000



        //val claims : Claims = Jwts.claims()
        /**proved by Ezou*/
        val base64Encoded = Base64.encodeToString(mySecretKey.toByteArray(), 0)  //it worked! first step


        //test
        //val secretKeytoByte = Constants.MY_SECRET_KEY_STATION.toByteArray()
        //val hmacKey: ByteArray = base64Encoded.toByteArray(StandardCharsets.UTF_8)
        //var key : Key = SecretKeySpec(base64Encoded.toByteArray(), SignatureAlgorithm.HS256.getJcaName())


        return Jwts.builder()
            .setHeaderParam("typ","JWT")
            .claim("exp",expmillis)
            .claim("iat",millis)
            .claim("jti",Constants.reqID())
            .signWith(SignatureAlgorithm.HS256,convertStringToSecretKey(base64Encoded))
            .compact()



    //.setId(Constants.reqID()).setClaims(claims)
        //.setExpiration(expmillis)
        //.setIssuedAt(Date())
        //.claim("jti","${reqID()}")


    /*if (Build.VERSION.SDK_INT >= 26) {
      base64Encoded = Base64.getEncoder().encode(Constants.SECRET_KEY_CURRENT.toByteArray()).toString();
  } else {
      base64Encoded = android.util.Base64.encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray(), 0)
  }
      //var base64Key = BASE64.encode(Constants.SECRET_KEY_CURRENT.getEncoded())
      //val base64Encoded = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          Base64.getEncoder().encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray())
      } else {
      }*/
    }



    fun convertStringToSecretKey(encodedKey: String?): SecretKey {
        val decodedKey: ByteArray = android.util.Base64.decode(encodedKey, android.util.Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }
    //android.util.Base64.decode(str, android.util.Base64.DEFAULT)
    /*
        fun standard(): String? {
            val d = Date()
            d.time = d.time + 259200
            return Jwts.builder().setId(API.reqID())
                .setIssuedAt(Date())
                .setExpiration(d)
                .signWith(SignatureAlgorithm.HS256, SharedValues.secretKey.getBytes())
                .compact()
        }
        */
}