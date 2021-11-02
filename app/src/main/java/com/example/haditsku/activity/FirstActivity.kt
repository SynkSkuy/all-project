package com.example.haditsku.activity

import android.content.Intent
import android.content.SharedPreferences
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

class FirstActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "hadits-arbain"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        if (sharedPref.getString("Username", "").toString() != "") {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
            finish()
        }

        // Login
        var usernameLogin = findViewById(R.id.username_login) as TextView
        var passwordLogin = findViewById(R.id.password_login) as TextView
        var btnLogin = findViewById(R.id.btn_login_user) as Button
        var btnregis = findViewById(R.id.textRegister) as TextView
        var textregis = findViewById(R.id.textRegister) as TextView

        /*// Registrasi
        var usernameRegistrasi = findViewById(R.id.username_registrasi) as TextView
        var passwordRegistrasi = findViewById(R.id.password_registrasi) as TextView
        var firstNameRegistrasi = findViewById(R.id.first_name_registrasi) as TextView
        var lastNameRegistrasi = findViewById(R.id.last_name_registrasi) as TextView
        var btnRegistrasi = findViewById(R.id.btn_registrasi_user) as Button*/

        val retrofitUser = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
        // Start to Login
        btnLogin.setOnClickListener {
            retrofitUser.verifyUser(usernameLogin.text.toString(), passwordLogin.text.toString())
                .enqueue(object : Callback<ArrayList<UserResponse>>{
                    override fun onResponse(
                        call: Call<ArrayList<UserResponse>>,
                        response: Response<ArrayList<UserResponse>>
                    ) {

                        if(response.body()!!.get(0).message == "Sukses, login berhasil"){
                            val editor = sharedPref.edit()
                            editor.putString("Username", response.body()!!.get(0).username)
                            editor.putString("First Name", response.body()!!.get(0).first_name)
                            editor.putString("Last Name", response.body()!!.get(0).last_name)
                            editor.apply()
                            usernameLogin.text = ""
                            passwordLogin.text = ""

                            // Pemberitahuan Berhasil
                            Toast.makeText(this@FirstActivity,response.body()!!.get(0).message,Toast.LENGTH_SHORT).show()

                            // Start Move to Main
                            val homeIntent = Intent(this@FirstActivity, MainActivity::class.java)
                            startActivity(homeIntent)
                            finish()
                        }else{
                            // Pemberitahuan Gagal
                            Toast.makeText(this@FirstActivity,response.body()!!.get(0).message,Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {
                        Log.e("Error Failed, why ? ", t.message.toString())
                    }

                })
        }

        textregis.setOnClickListener {
            Toast.makeText(this@FirstActivity, "Daftar", Toast.LENGTH_SHORT).show()

            val daftarIntent = Intent(this@FirstActivity, Register::class.java)
            startActivity(daftarIntent)
            finish()
        }


        /*// Start to Registration
        btnRegistrasi.setOnClickListener {
            retrofitUser.insertUsers(usernameRegistrasi.text.toString(), passwordRegistrasi.text.toString(),
                firstNameRegistrasi.text.toString(), lastNameRegistrasi.text.toString()).enqueue(object :
                Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    usernameRegistrasi.text = ""
                    passwordRegistrasi.text = ""
                    firstNameRegistrasi.text = ""
                    lastNameRegistrasi.text = ""
                    Toast.makeText(this@FirstActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Error Failed, why ? ", t.message.toString())
                }

            })
        }*/



    }
}