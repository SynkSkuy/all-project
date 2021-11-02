package com.example.haditsku.activity

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {
    @POST("tampilkanNamaHadits.php")
    fun getPosts(): Call<ArrayList<PostResponse>>

    @FormUrlEncoded
    @POST("tampilkanDataHadits.php")
    fun getPostsAll(@Field("chapter") chapter: String, @Field("naratter") naratter: String): Call<ArrayList<PostResponse>>

    @FormUrlEncoded
    @POST("tampilkanSearchingDataHadits.php")
    fun getPostsSearch(@Field("search_chapter") chapter: String): Call<ArrayList<PostResponse>>

    @FormUrlEncoded
    @POST("simpanNamaHadits.php")
    fun insertPosts(
            @Field("id") id: Int,
            @Field("chapter") chapter: String,
            @Field("name_user") name_user: String
    ): Call<String>

    @FormUrlEncoded
    @POST("tampilkanBookmarkHadits.php")
    fun getBookmarks(@Field("name_user") name_user: String): Call<ArrayList<PostResponse>>

    @FormUrlEncoded
    @POST("simpanUser.php")
    fun insertUsers(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String
    ): Call<String>

    @FormUrlEncoded
    @POST("tampilkanUser.php")
    fun verifyUser(@Field("username") username: String,
                   @Field("password") password: String): Call<ArrayList<UserResponse>>

    @FormUrlEncoded
    @POST("checkBookmarkHadits.php")
    fun checkBookmarks(@Field("name_user") name_user: String, @Field("nama_hadith") nama_hadith: String): Call<String>

}