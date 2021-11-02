package com.example.haditsku.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.haditsku.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PilihanHaditsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihan_hadits)

        var recycleView = findViewById(R.id.pilihan_item_hadits) as RecyclerView
        var editTextSearchHadits = findViewById(R.id.input_search_hadits) as EditText

        val listDataPilihanHaditsRV = ArrayList<ItemHadits>()
        val listSearchDataPilihanHaditsRV = ArrayList<ItemHadits>()

        val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
        retrofitKeluaranHadits.getPosts().enqueue(object : Callback<ArrayList<PostResponse>>, ItemHaditsAdapter.OnItemClickListener {
            override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                for (i in response.body()!!.indices){
                    // val idHadits = response.body()!!.get(i).id
                    val namaHadits = response.body()!!.get(i).chapter
                    val sumberHadits = response.body()!!.get(i).netter

                    listDataPilihanHaditsRV.add(ItemHadits(namaHadits, sumberHadits))
                    recycleView.adapter = ItemHaditsAdapter(listDataPilihanHaditsRV, this)
                    recycleView.layoutManager = LinearLayoutManager(this@PilihanHaditsActivity)
                    recycleView.setHasFixedSize(true)
                }

            }

            override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                Log.e("Error Failed, why ? ", t.message.toString())
            }

            override fun onItemClick(position: Int) {
                val intent = Intent(this@PilihanHaditsActivity, DetailHaditsActivity::class.java)
                intent.putExtra("Nama Hadits",listDataPilihanHaditsRV[position].titleHadits)
                intent.putExtra("Sumber Hadits",listDataPilihanHaditsRV[position].subNaratorHadits)
                startActivity(intent)
            }

        })

        editTextSearchHadits.addTextChangedListener {
            val inputUserSearchingHadits = editTextSearchHadits.text.toString()

            if (inputUserSearchingHadits.equals("") || inputUserSearchingHadits.equals(" ")){
                val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
                retrofitKeluaranHadits.getPosts().enqueue(object : Callback<ArrayList<PostResponse>>, ItemHaditsAdapter.OnItemClickListener {
                    override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                        listDataPilihanHaditsRV.clear()
                        for (i in response.body()!!.indices){
                            // val idHadits = response.body()!!.get(i).id
                            val namaHadits = response.body()!!.get(i).chapter
                            val sumberHadits = response.body()!!.get(i).netter

                            listDataPilihanHaditsRV.add(ItemHadits(namaHadits, sumberHadits))
                            recycleView.adapter = ItemHaditsAdapter(listDataPilihanHaditsRV, this)
                            recycleView.layoutManager = LinearLayoutManager(this@PilihanHaditsActivity)
                            recycleView.setHasFixedSize(true)
                        }

                    }

                    override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                        Log.e("Error Failed, why ? ", t.message.toString())
                    }

                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@PilihanHaditsActivity, DetailHaditsActivity::class.java)
                        intent.putExtra("Nama Hadits",listDataPilihanHaditsRV[position].titleHadits)
                        intent.putExtra("Sumber Hadits",listDataPilihanHaditsRV[position].subNaratorHadits)
                        startActivity(intent)
                    }

                })
            }else{
                val retrofitKeluaranHadits = RetrofitClient().getRetrofitClientInstance().create(Api::class.java)
                retrofitKeluaranHadits.getPostsSearch(inputUserSearchingHadits.toString()).enqueue(object : Callback<ArrayList<PostResponse>>, ItemHaditsAdapter.OnItemClickListener{
                    override fun onResponse(call: Call<ArrayList<PostResponse>>, response: Response<ArrayList<PostResponse>>) {
                        listDataPilihanHaditsRV.clear()
                        for (i in response.body()!!.indices){
                            // val idHadits = response.body()!!.get(i).id
                            val namaHadits = response.body()!!.get(i).chapter
                            val sumberHadits = response.body()!!.get(i).content+" "+response.body()!!.get(i).term

                            listDataPilihanHaditsRV.add(ItemHadits(namaHadits, sumberHadits))
                        }
                        recycleView.adapter = ItemHaditsAdapter(listDataPilihanHaditsRV, this)
                        recycleView.layoutManager = LinearLayoutManager(this@PilihanHaditsActivity)
                        recycleView.setHasFixedSize(true)
                    }

                    override fun onFailure(call: Call<ArrayList<PostResponse>>, t: Throwable) {
                        Log.e("Error Failed, why ? ", t.message.toString())
                    }

                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@PilihanHaditsActivity, DetailHaditsActivity::class.java)
                        intent.putExtra("Nama Hadits",listDataPilihanHaditsRV[position].titleHadits)
                        intent.putExtra("Sumber Hadits",listDataPilihanHaditsRV[position].subNaratorHadits)
                        startActivity(intent)
                    }

                })

            }


        }

    }

}