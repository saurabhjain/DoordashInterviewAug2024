package com.tps.challenge.features.storefeed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.tps.challenge.R
import com.tps.challenge.TCApplication
import com.tps.challenge.ViewModelFactory
import com.tps.challenge.viewmodel.StoreFeedViewModel
import javax.inject.Inject

/**
 * Displays the list of Stores with its title, description and the cover image to the user.
 */
class StoreFeedFragment : Fragment() {
    companion object {
        const val TAG = "StoreFeedFragment"
    }
    private lateinit var storeFeedAdapter: StoreFeedAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<StoreFeedViewModel>

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        val viewModel: StoreFeedViewModel by lazy {
            viewModelFactory.get<StoreFeedViewModel>(
            requireActivity()
            )
        }

        viewModel.storesData.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = false
            if(it != null && it.isNotEmpty()) {
                storeFeedAdapter.stores = it
                storeFeedAdapter.notifyDataSetChanged()
                view?.findViewById<RecyclerView>(R.id.stores_view)?.visibility = View.VISIBLE
                view?.findViewById<TextView>(R.id.tv_empty_text)?.visibility = View.GONE
            } else {
                view?.findViewById<RecyclerView>(R.id.stores_view)?.visibility = View.GONE
                view?.findViewById<TextView>(R.id.tv_empty_text)?.visibility = View.VISIBLE
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchAndGetStoresData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_feed, container, false)
        swipeRefreshLayout = view.findViewById(R.id.swipe_container)
        // Enable if Swipe-To-Refresh functionality will be needed
        swipeRefreshLayout.isEnabled = true

        storeFeedAdapter = StoreFeedAdapter()
        recyclerView = view.findViewById(R.id.stores_view)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = storeFeedAdapter
        }
        return view
    }
}
