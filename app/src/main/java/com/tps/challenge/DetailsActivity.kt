package com.tps.challenge

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tps.challenge.viewmodel.StoreDetailViewModel
import javax.inject.Inject

class DetailsActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<StoreDetailViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun onStart() {
        super.onStart()

        val viewModel: StoreDetailViewModel by lazy {
            viewModelFactory.get<StoreDetailViewModel>(
                this as AppCompatActivity
            )
        }

        val isStarred = intent.getBooleanExtra(Constants.INTENT_FAV_STATE, false)
        findViewById<ToggleButton>(R.id.btn_fav_detail).isChecked = isStarred
        intent.getStringExtra(Constants.INTENT_ID)?.let { viewModel.fetchStoreDetailData(it) }
        viewModel.storeDetailData.observe(this, Observer {
            if(it != null) {
                findViewById<TextView>(R.id.tv_address).text = it.address.printableAddress
                Glide.with(this)
                    .load(it.coverImgUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image
                            .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                    )
                    .into(findViewById<ImageView>(R.id.iv_photo))
            } else {
                findViewById<TextView>(R.id.tv_address).text = getString(R.string.error_fetching_data)
            }
        })
    }
}
