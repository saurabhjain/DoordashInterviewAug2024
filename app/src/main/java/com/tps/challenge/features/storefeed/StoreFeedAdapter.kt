package com.tps.challenge.features.storefeed

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tps.challenge.Constants
import com.tps.challenge.DetailsActivity
import com.tps.challenge.MainActivity
import com.tps.challenge.R
import com.tps.challenge.network.model.StoreResponse

/**
 * A RecyclerView.Adapter to populate the screen with a store feed.
 */
class StoreFeedAdapter: RecyclerView.Adapter<StoreItemViewHolder>() {

    var stores = listOf<StoreResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreItemViewHolder {
        return StoreItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoreItemViewHolder, position: Int) {
        val item = stores[position]
        holder.bind(item)
    }

    override fun getItemCount() = stores.size
}

/**
 * Holds the view for the Store item.
 */
class StoreItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: StoreResponse) {
        with(itemView) {
            findViewById<TextView>(R.id.name).text = item.name
            findViewById<TextView>(R.id.description).text = item.description

            findViewById<ConstraintLayout>(R.id.item_root).setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java)
                intent.putExtra(Constants.INTENT_ID, item.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}
