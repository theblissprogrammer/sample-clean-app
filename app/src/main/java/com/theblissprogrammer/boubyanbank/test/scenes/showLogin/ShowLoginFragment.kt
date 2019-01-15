package com.theblissprogrammer.boubyanbank.test.scenes.showLogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theblissprogrammer.boubyanbank.businesslogic.account.models.LoginModels
import com.theblissprogrammer.boubyanbank.businesslogic.dependencies.HasDependencies
import com.theblissprogrammer.boubyanbank.test.R
import com.theblissprogrammer.boubyanbank.test.common.activities.BaseFragment
import com.theblissprogrammer.boubyanbank.test.common.extentions.hideSpinner
import com.theblissprogrammer.boubyanbank.test.common.extentions.showSpinner
import com.theblissprogrammer.boubyanbank.test.scenes.showLogin.common.*
import kotlinx.android.synthetic.main.content_login.*
import java.lang.ref.WeakReference

class ShowLoginFragment: BaseFragment(), ShowLoginDisplayable, HasDependencies, AuthenticationDelegate {

    // VIP variables
    private val interactor: ShowLoginBusinessLogic by lazy {
        ShowLoginInteractor(
                presenter = ShowLoginPresenter(activity = WeakReference(this)),
                authenticationWorker = dependencies.resolveAuthenticationWorker
        )
    }

    private val router: ShowLoginRoutable by lazy {
        ShowLoginRouter(fragment = WeakReference(this))
    }

    var authenticationDelegate: WeakReference<AuthenticationDelegate?>? = null

    var routedFromLoginActivity = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            spinner.showSpinner()

            val request = LoginModels.Request(
                    username = usernameEditText.text.toString(),
                    password = passwordEditText.text.toString()
            )

            interactor.performLogin(request = request)
        }
    }

    override fun displayAuthenticated(response: ShowLoginModels.ViewModel) {
        spinner.hideSpinner()
        router.dismiss(!routedFromLoginActivity)
        authenticationDelegate?.get()?.didAuthenticate()
    }

    override fun displayAuthenticated(error: ShowLoginModels.ViewModel.Error) {
        spinner.hideSpinner()
        present(error.title, error.message)
    }

    override fun didAuthenticate() {
        spinner.hideSpinner()
        router.dismiss(!routedFromLoginActivity)
        authenticationDelegate?.get()?.didAuthenticate()
    }

    private val textWatcher: TextWatcher = object: TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            loginButton.alpha =
                    if (usernameEditText.text.toString().count() >= 1
                            && passwordEditText.text.toString().count() >= 1)
                        1.0F else 0.3F
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }
}
