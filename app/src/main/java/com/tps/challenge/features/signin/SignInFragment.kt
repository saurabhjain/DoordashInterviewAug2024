package com.tps.challenge.features.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tps.challenge.R
import com.tps.challenge.TCApplication
import com.tps.challenge.ViewModelFactory
import com.tps.challenge.features.storefeed.StoreFeedFragment
import com.tps.challenge.viewmodel.SignInViewModel
import javax.inject.Inject

class SignInFragment: Fragment() {
    companion object {
        const val TAG = "SignInFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SignInViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val viewModel: SignInViewModel by lazy {
            viewModelFactory.get<SignInViewModel>(
                requireActivity()
            )
        }

        view.findViewById<Button>(R.id.btn_enter).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.ev_username).text.toString()
            val password = view.findViewById<EditText>(R.id.ev_pw).text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.saveUserCredentials(username, password)
                val storeFeedFragment = StoreFeedFragment()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.container, storeFeedFragment,
                        StoreFeedFragment.TAG
                    )
                    ?.commit()
            } else {
                Toast.makeText(activity, getString(R.string.check_input), Toast.LENGTH_LONG).show()
            }
        }
        return view

    }
}
