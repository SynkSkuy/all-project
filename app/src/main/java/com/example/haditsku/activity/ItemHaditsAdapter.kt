package com.example.haditsku.activity

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.haditsku.R

class ItemHaditsAdapter(private val itemPilihanHaditsList: List<ItemHadits>, private val listener: OnItemClickListener) : RecyclerView.Adapter<ItemHaditsAdapter.ItemHaditsViewHolder>() {

    inner class ItemHaditsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val titleHadits : TextView = itemView.findViewById(R.id.title_hadits)
        val naratorHadits : TextView = itemView.findViewById(R.id.narator_hadits)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHaditsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pilihan_hadits, parent, false)
        return ItemHaditsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemHaditsViewHolder, position: Int) {
        val currentItem = itemPilihanHaditsList[position]

        holder.titleHadits.text = currentItem.titleHadits

        val term = currentItem.subNaratorHadits.substring(currentItem.subNaratorHadits.lastIndexOf(" ")+1)
        var splitHadits = currentItem.subNaratorHadits.split(" ").toTypedArray()
        if (splitHadits.size <= 10){
            holder.naratorHadits.text = currentItem.subNaratorHadits
        }else{
            var finalSplit = arrayListOf<String>()

            var countPositionWord = 0
            for (termAllHadits in splitHadits){
                if(splitHadits.size-1 == countPositionWord){
                    // Nothing
                }else{
                    if(termAllHadits.contains(term, ignoreCase = false)){
                        finalSplit.add(countPositionWord, "<font color='#8B0000'>"+termAllHadits+"</font>")
                    }else{
                        finalSplit.add(countPositionWord, termAllHadits)
                    }
                }
                countPositionWord = countPositionWord+1
            }
            var finalText = finalSplit.joinToString(" ")
            holder.naratorHadits.text = Html.fromHtml(finalText)
        }
    }

    override fun getItemCount() = itemPilihanHaditsList.size

}