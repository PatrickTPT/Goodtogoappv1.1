package com.example.goodtogoappv11.model

import android.util.Base64
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

object Tokens {

    fun standardAut(): String {

        val d = Date()
        d.time = d.time + 259200

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

        val claims : Claims = Jwts.claims()
        /**proved by Ezou*/
        val base64Encoded = Base64.encodeToString(Constants.SECRET_KEY_CURRENT.toByteArray(), 0)

        //val hmacKey: ByteArray = base64Encoded.toByteArray(StandardCharsets.UTF_8)
        var key : Key = SecretKeySpec(base64Encoded.toByteArray(), SignatureAlgorithm.HS256.getJcaName())


        return Jwts.builder()
            .setId(Constants.reqID())
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(d)
            .signWith(SignatureAlgorithm.HS256,base64Encoded)
            .compact()

    }

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