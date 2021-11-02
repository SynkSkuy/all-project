package com.example.haditsku.activity

import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.haditsku.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHaditsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_hadits)

        var idJudulHadits = findViewById(R.id.id_judul) as TextView
        var judulHaditsTommy = findViewById(R.id.judul_hadits) as TextView
        var kategoriHaditsTommy = findViewById(R.id.kategori_hadits) as TextView
        var arabHaditsTommy = findViewById(R.id.arab_hadits) as TextView
        var kontentHaditsTommy = findViewById(R.id.konten_hadits) as TextView
        var boorkmarkHadits =  findViewById(R.id.bookmarks_hadits) as FloatingActionButton


        val sharedPref: SharedPreferences = getSharedPreferences("hadits-arbain", 0)
        val namaHaditsPilihan = intent.getStringExtra("Nama Hadits")
        val sumberHaditsPilihan = intent.getStringExtra("Sumber Hadits")

        val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
        retrofitKeluaranHadits.getPostsAll(namaHaditsPilihan!!, sumberHaditsPilihan!!).enqueue(object : Callback<ArrayList<PostResponse>>{
            override fun onResponse(
                call: Call<ArrayList<PostResponse>>,
                response: Response<ArrayList<PostResponse>>
            ) {
                if (response.body()!!.size > 0){
                    val idHadits = response.body()!!.get(0).id
                    val namaHadits = response.body()!!.get(0).chapter
                    val sumberHadits = response.body()!!.get(0).netter
                    val kategoriHadits = response.body()!!.get(0).category
                    val arabHadits = response.body()!!.get(0).arabic
                    val kontentHadits = response.body()!!.get(0).content

                    idJudulHadits.text = idHadits.toString()
                    judulHaditsTommy.text = namaHaditsPilihan
                    kategoriHaditsTommy.text = kategoriHadits
                    arabHaditsTommy.text = arabHadits
                    kontentHaditsTommy.text = kontentHadits
                }
            }

            override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                Log.e("Error Failed, why ? ", t.message.toString())
            }

        })

        retrofitKeluaranHadits.checkBookmarks(sharedPref.getString("Username", "").toString(), namaHaditsPilihan).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.body().toString() == "Favorite"){
                    boorkmarkHadits.backgroundTintList = ColorStateList.valueOf(Color.rgb(255, 50, 50))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("Error Failed, why ? ", t.message.toString())
            }

        })

        boorkmarkHadits.setOnClickListener {

            val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
            retrofitKeluaranHadits
                    .insertPosts(idJudulHadits.text.toString().toInt(),judulHaditsTommy.text.toString(),sharedPref.getString("Username", "").toString())
                    .enqueue(object : Callback<String>{
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if(response.body().toString() == "Sukses, favorite anda telah dihapus"){
                                boorkmarkHadits.backgroundTintList = ColorStateList.valueOf(Color.rgb(50, 255, 50))
                            }else{
                                boorkmarkHadits.backgroundTintList = ColorStateList.valueOf(Color.rgb(255, 50, 50))
                            }
                            Toast.makeText(this@DetailHaditsActivity, ""+response.body().toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("Error Failed, why ? ", t.message.toString())
                        }

                    })
        }

    }


}