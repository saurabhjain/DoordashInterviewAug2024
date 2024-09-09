package com.tps.challenge.features.storefeed

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
import com.tps.challenge.viewmodel.SignInViewModel
import javax.inject.Inject

class SignInFragment: Fragment() {
    companion object {
        const val TAG = "SignInFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<SignInViewModel>

    private val viewModel: SignInViewModel by lazy {
        viewModelFactory.get<SignInViewModel>(
            requireActivity()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        TCApplication.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        view.findViewById<Button>(R.id.btn_enter).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.ev_email).text.toString()
            val password = view.findViewById<EditText>(R.id.ev_password).text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.getTokenAndSigIn(username, password)
                val storeFeedFragment = StoreFeedFragment()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, storeFeedFragment, tag)
                    ?.commit()
            } else {
                Toast.makeText(activity, getString(R.string.check_input), Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}