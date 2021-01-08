package com.abhay.shaadi.ui.ui.mydashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.abhay.shaadi.R
import com.abhay.shaadi.databinding.FragmentDashboardBinding
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.presentation.mapper.UiRandomUsersMapper
import com.abhay.shaadi.presentation.model.UiUserResult
import com.abhay.shaadi.presentation.viewmodel.FavoriteUsersViewModel
import com.abhay.shaadi.ui.list.adapter.UsersAdapter
import com.abhay.shaadi.ui.list.interfaces.UserItemListener
import com.abhay.library.base.data.coroutines.Result
import com.abhay.library.base.presentation.adapter.base.BaseBindClickHandler
import com.abhay.library.base.presentation.extensions.observe
import com.abhay.library.uikit.extension.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyDashboardFragment : Fragment(), BaseBindClickHandler<UiUserResult>, UserItemListener {


    private val favoriteUsersViewModel: FavoriteUsersViewModel by viewModel()

    lateinit var binding: FragmentDashboardBinding
    private val uiRandomUsersMapper: UiRandomUsersMapper by inject()

    private val usersAdapter: UsersAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentDashboardBinding
        binding.lifecycleOwner = this

        with(favoriteUsersViewModel) {
            observe(getFavoriteUsersLiveResult, ::getFavoriteUsersObserver)
            observe(saveFavoriteUserLiveResult, ::saveFavoriteUserObserver)
            observe(deleteFavoriteUserLiveResult, ::deleteFavoriteUserObserver)
        }
        favoriteUsersViewModel.getFavoriteUsers()
    }


    private fun deleteFavoriteUserObserver(result: Result<DomainUserResult>?) {
        when (result) {
            is Result.OnSuccess -> {
                usersAdapter.items.map { uiItem ->
                    if (uiItem?.userId == result.value.userId) {
                        uiItem.isAccepted.set(false)
                    }
                }
                favoriteUsersViewModel.getFavoriteUsers()
            }
            is Result.OnError -> {
                activity?.toast(getString(R.string.error_database))
            }
        }
    }

    private fun saveFavoriteUserObserver(result: Result<DomainUserResult>?) {
        when (result) {
            is Result.OnSuccess -> {
                usersAdapter.items.map { uiItem ->
                    if (uiItem?.userId == result.value.userId) {
                        uiItem.isAccepted.set(true)
                    }
                }
                favoriteUsersViewModel.getFavoriteUsers()
            }
            is Result.OnError -> {
                activity?.toast(getString(R.string.error_database))
            }
        }
    }

    private fun getFavoriteUsersObserver(result: Result<List<DomainUserResult>>?) {
        when (result) {
            is Result.OnSuccess -> {
                // First refresh vertical view
                val users = usersAdapter.items

                if (result.value.isEmpty()) {
                    users.map { uiItem ->
                        uiItem?.isAccepted?.set(false)
                    }
                } else {
                    var favIds = ""
                    result.value.map {
                        favIds += "$it&"
                    }

                    users.map { uiItem ->
                        if (favIds.contains(uiItem?.userId!!)) {
                            uiItem.isAccepted.set(true)
                        } else {
                            uiItem.isAccepted.set(false)
                        }
                    }
                }

                // After refresh horizontal view
                if (result.value.isEmpty()) {
                  // No result
                } else {

                    binding.rvFavoriteUsers.let { rv ->
                        if (rv.adapter == null) {
                            rv.setHasFixedSize(true)
                            usersAdapter.favoriteClickHandler = this@MyDashboardFragment
                            rv.adapter = usersAdapter
                        }

                        val items =
                            with(uiRandomUsersMapper) { result.value.map { it.fromDomainToUi() } }.toMutableList()
                        usersAdapter.items = items.toMutableList()
                        usersAdapter.notifyDataSetChanged()
                    }
                }
            }
            is Result.OnError -> {
                activity?.toast(getString(R.string.error_database))
            }
        }
    }

    override fun onClickView(view: View, item: UiUserResult) {
    }

    override fun onFavoriteClick(item: UiUserResult) {

    }

    override fun onNotFavoriteClick(item: UiUserResult) {

    }
}