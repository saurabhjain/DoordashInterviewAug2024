package com.tps.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tps.challenge.features.storefeed.SignInFragment
import com.tps.challenge.features.storefeed.StoreFeedFragment
import com.tps.challenge.viewmodel.SignInViewModel
import com.tps.challenge.viewmodel.StoreFeedViewModel
import javax.inject.Inject

/**
 * The initial Activity for the app.
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SignInViewModel>

    private val viewModel: SignInViewModel by lazy {
        viewModelFactory.get<SignInViewModel>(
            this as AppCompatActivity
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        lateinit var fragment: Fragment
        lateinit var tag: String

        if(viewModel.isUserSignedIn()) {
            fragment = StoreFeedFragment()
            tag = StoreFeedFragment.TAG
        } else {
            fragment = SignInFragment()
            tag = SignInFragment.TAG
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .commit()
    }
}
