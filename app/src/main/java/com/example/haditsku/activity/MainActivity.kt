package com.example.haditsku.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haditsku.R
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    private val listDataPilihanHaditsRV = ArrayList<ItemHadits>()
    private lateinit var recycleView: RecyclerView
    private lateinit var sharedPref: SharedPreferences

    private val profil= Profileus()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("hadits-arbain", 0)
        var cardView = findViewById(R.id.click_hadits_arbain) as CardView
       recycleView = findViewById(R.id.favorite_hadits)


        // Menuju bagian pemilihan hadits
        cardView.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, PilihanHaditsActivity::class.java))
        })

        // Menuju Favorite dan Profile
        

    }




    override fun onResume() {
        super.onResume()
        // Memperlihatkan seluruh hasil bookmarks
        val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
        retrofitKeluaranHadits.getBookmarks(sharedPref.getString("Username", "").toString()).
        enqueue(object : Callback<ArrayList<PostResponse>>,
                ItemHaditsAdapter.OnItemClickListener{
            override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                listDataPilihanHaditsRV.clear()
                for (i in response.body()!!.indices){
                    // val idHadits = response.body()!!.get(i).id
                    val namaHadits = response.body()!!.get(i).chapter
                    val sumberHadits = "Tersimpan di Favorite"

                    listDataPilihanHaditsRV.add(ItemHadits(namaHadits, sumberHadits))
                    recycleView.adapter = ItemHaditsAdapter(listDataPilihanHaditsRV, this)
                    recycleView.layoutManager = LinearLayoutManager(this@MainActivity)
                    recycleView.setHasFixedSize(true)
                }
            }

            override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                Log.e("Error Failed, why ? ", t.message.toString())
            }

            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DetailHaditsActivity::class.java)
                intent.putExtra("Nama Hadits",listDataPilihanHaditsRV[position].titleHadits)
                intent.putExtra("Sumber Hadits",listDataPilihanHaditsRV[position].subNaratorHadits)
                startActivity(intent)
            }
        })
    }


}


