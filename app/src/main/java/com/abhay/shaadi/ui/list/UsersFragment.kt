package com.abhay.shaadi.ui.list

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.abhay.shaadi.R
import com.abhay.shaadi.databinding.FragmentUsersBinding
import com.abhay.shaadi.domain.model.DomainUserResult
import com.abhay.shaadi.presentation.mapper.UiRandomUsersMapper
import com.abhay.shaadi.presentation.model.UiUserResult
import com.abhay.shaadi.presentation.model.UiWrapperUserResult
import com.abhay.shaadi.presentation.viewmodel.FavoriteUsersViewModel
import com.abhay.shaadi.presentation.viewmodel.RandomUsersViewModel
import com.abhay.shaadi.ui.list.adapter.UsersAdapter
import com.abhay.shaadi.ui.list.interfaces.UserItemListener
import com.abhay.library.base.data.coroutines.Result
import com.abhay.library.base.presentation.adapter.base.BaseBindClickHandler
import com.abhay.library.base.presentation.databinding.paginationForRecyclerScroll
import com.abhay.library.base.presentation.databinding.runLayoutAnimation
import com.abhay.library.base.presentation.extensions.isNetworkAvailable
import com.abhay.library.base.presentation.extensions.observe
import com.abhay.library.base.presentation.viewmodel.PaginationViewModel
import com.abhay.library.uikit.extension.*
import com.abhay.shaadi.presentation.state.SearchState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class UsersFragment : Fragment(), BaseBindClickHandler<UiUserResult>,
    UserItemListener {

    companion object {
        const val SUGGESTION_CURSOR = "users"
    }

    private val randomUsersViewModel: RandomUsersViewModel by viewModel()
    private val paginationViewModel: PaginationViewModel<UiUserResult> by viewModel()
    private val favoriteUsersViewModel: FavoriteUsersViewModel by viewModel()

    private val connectionManager: ConnectivityManager by inject()
    private val uiRandomUsersMapper: UiRandomUsersMapper by inject()
    private val usersAdapter: UsersAdapter by inject()


    lateinit var binding: FragmentUsersBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentUsersBinding
        binding.lifecycleOwner = this

        paginationViewModel.run {
            binding.isLoadingac = isLoading
            binding.isErrorac = isError
        }


        with(randomUsersViewModel) {
            observe(state, ::stateObserver)
            observe(usersLiveResult, ::newsObserver)
        }

        with(favoriteUsersViewModel) {
            observe(saveFavoriteUserLiveResult, ::saveFavoriteUserObserver)
            observe(deleteFavoriteUserLiveResult, ::deleteFavoriteUserObserver)
        }

        binding.srlUsers.initSwipe {
            paginationViewModel.run {
                offset.set(0)
                randomUsersViewModel.getUsers(offset.get())
            }
        }
    }


    private fun stateObserver(state: SearchState?) {
        state ?: return

        randomUsersViewModel.run {
            (usersLiveResult.value as? Result.OnSuccess<List<DomainUserResult>>)?.let { result ->
                newsObserver(result)
            } ?: getUsers(paginationViewModel.offset.get())
        }
    }

    private fun newsObserver(result: Result<List<DomainUserResult>>?) {
        when (result) {
            is Result.OnLoading -> {
                if (!paginationViewModel.isLoadingMorePages.get()) {
                    binding.isLoadingac?.set(true)
                    binding.isErrorac?.set(false)
                }
            }
            is Result.OnSuccess -> {

                binding.isLoadingac?.set(false)
                binding.isErrorac?.set(false)
                binding.srlUsers.isRefreshing = false

                val wrapperResult = with(uiRandomUsersMapper) {


                    UiWrapperUserResult(items = result.value.map {


                        paginationViewModel.pageSize = it.pageSize
                        it.fromDomainToUi()
                    })
                }

                paginationViewModel.setPagination(
                    adapter = usersAdapter,
                    items = wrapperResult?.items as ArrayList<UiUserResult>,
                    bodyHasItems = { items ->
                        if (items?.isNotEmpty() == true) {
                            usersAdapter.clickHandler = this@UsersFragment
                            usersAdapter.favoriteClickHandler = this@UsersFragment
                            usersAdapter.items = (items as? MutableList<UiUserResult?>)!!
                            setAdapterByData()
                        } else {
                            // Show Empty View
                            paginationViewModel.isEmpty.set(true)
                        }
                    }, bodyNoItems = { items ->
                        usersAdapter.clickHandler = this@UsersFragment
                        usersAdapter.favoriteClickHandler = this@UsersFragment
                        usersAdapter.items = (items as? MutableList<UiUserResult?>)!!
                        setAdapterByData()
                        // Show Empty View
                        paginationViewModel.isEmpty.set(true)
                    })

                favoriteUsersViewModel.getFavoriteUsers()
            }
            is Result.OnError -> {
                binding.isLoadingac?.set(false)
                binding.isErrorac?.set(true)
                binding.srlUsers.isRefreshing = false

                if (connectionManager.isNetworkAvailable()) {
                    activity?.toast(getString(R.string.error_connection))
                } else {
                    activity?.toast(getString(R.string.error_connection_internet))
                }
            }
        }
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
    

    override fun onClickView(view: View, item: UiUserResult) {

    }


    override fun onFavoriteClick(item: UiUserResult) {

            with(uiRandomUsersMapper) {
                item.isAccepted.set(true)
                item.isDeclined.set(false)
                favoriteUsersViewModel.saveFavoriteUser(item.fromUiToDomain())
            }
       
    }

    override fun onNotFavoriteClick(item: UiUserResult) {
   
            with(uiRandomUsersMapper) {
                item.isAccepted.set(false)
                item.isDeclined.set(true)
                favoriteUsersViewModel.saveFavoriteUser(item.fromUiToDomain())
               // favoriteUsersViewModel.deleteFavoriteUser(item.fromUiToDomain())
            }
    }

    private fun setAdapterByData() {
        context?.let {
            val tdLayoutManager = GridLayoutManager(it, 1)

            binding.rvUsers.let { rv ->
                rv.setHasFixedSize(true)
                rv.layoutManager = tdLayoutManager
                rv.adapter = usersAdapter

                paginationViewModel.run {
                    rv.paginationForRecyclerScroll(
                        itemPosition = itemPosition,
                        lastPosition = lastPosition,
                        offset = offset,
                        listSize = usersAdapter.items.size,
                        pageSize = pageSize,
                        isLoadingMorePages = isLoadingMorePages
                    ) {
                        binding.isLoadingac?.set(false)
                        randomUsersViewModel.getUsers(offset.get())
                    }
                    rv.scrollToPosition(itemPosition.get())
                }
                rv.runLayoutAnimation()
            }
        }
    }
}
