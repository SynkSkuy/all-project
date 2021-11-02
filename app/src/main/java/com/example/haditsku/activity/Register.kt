package com.example.haditsku.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.haditsku.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        // Registrasi
        var usernameRegistrasi = findViewById(R.id.username_registrasi) as TextView
        var passwordRegistrasi = findViewById(R.id.password_registrasi) as TextView
        var firstNameRegistrasi = findViewById(R.id.first_name_registrasi) as TextView
        var lastNameRegistrasi = findViewById(R.id.last_name_registrasi) as TextView
        var btnRegistrasi = findViewById(R.id.btn_registrasi_user) as Button
        var menulogin =  findViewById(R.id.login) as TextView

        val retrofitUser = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)

        // Start to Registration
        btnRegistrasi.setOnClickListener {
            retrofitUser.insertUsers(usernameRegistrasi.text.toString(), passwordRegistrasi.text.toString(),
                    firstNameRegistrasi.text.toString(), lastNameRegistrasi.text.toString()).enqueue(object :
                    Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    usernameRegistrasi.text = ""
                    passwordRegistrasi.text = ""
                    firstNameRegistrasi.text = ""
                    lastNameRegistrasi.text = ""
                    Toast.makeText(this@Register, response.body().toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Error Failed, why ? ", t.message.toString())
                }

            })
        }

        menulogin.setOnClickListener {
            Toast.makeText(this@Register, "Login", Toast.LENGTH_SHORT).show()

            val loginIntent = Intent(this@Register, FirstActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
    }


}

