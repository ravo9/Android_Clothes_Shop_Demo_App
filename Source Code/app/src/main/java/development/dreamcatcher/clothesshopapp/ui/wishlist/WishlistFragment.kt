package development.dreamcatcher.clothesshopapp.ui.wishlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.clothesshopapp.R
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.wishlist.database.WishlistItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.injection.ClothesShopApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.wishlist.*
import java.util.*
import javax.inject.Inject

// Detailed view for displaying wishlist
class WishlistFragment(): Fragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WishlistViewModel
    private lateinit var wishlistItemsGridAdapter: WishlistItemsGridAdapter

    init {
        ClothesShopApp.mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WishlistViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wishlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Initialize RecyclerView (items List)
        setupRecyclerView()

        // Fetch wishlist items from repository
        subscribeForItems()

        // Setup Cross Button
        val closingOnClickListener = View.OnClickListener{ activity?.onBackPressed() }
        btn_cross.setOnClickListener(closingOnClickListener)

        // Setup closing on the grey fields' click
        spacing_top.setOnClickListener(closingOnClickListener)
        spacing_bottom.setOnClickListener(closingOnClickListener)
    }

    private fun setupRecyclerView() {
        context?.let {
            wishlistItemsGridAdapter = WishlistItemsGridAdapter(
                it,
                this::removeItem
            )
            wishlist_gridView.adapter = wishlistItemsGridAdapter
        }
    }

    @SuppressLint("CheckResult")
    private fun removeItem(itemId: Int) {
        viewModel.removeItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(context, R.string.item_removed, Toast.LENGTH_SHORT).show()
                    subscribeForItems()
                },
                {
                    Toast.makeText(context, R.string.error_item_couldnt_be_removed, Toast.LENGTH_SHORT).show()
                })
    }

    private fun subscribeForItems() {
        viewModel.getWishlistItems()?.observe(this, Observer<List<WishlistItemDatabaseEntity>> {
            showLoadingView(false)
            val itemsToBeDisplayed = LinkedList<ItemDatabaseEntity>()

            if (!it.isNullOrEmpty()) {

                // Map CartItemsEntities into ItemsEntities
                val cartItems = it
                viewModel.getWholeItems(false)?.observe(this, Observer<List<ItemDatabaseEntity>> {
                    if (!it.isNullOrEmpty()) {
                        val allItems = it
                        cartItems.forEach {
                            val searchedId = it.productId
                            allItems.firstOrNull { it.id == searchedId }?.let {
                                itemsToBeDisplayed.add(it)
                            }
                        }

                        // Display fetched Items
                        wishlistItemsGridAdapter.setItems(itemsToBeDisplayed)
                    }
                })
            } else {
                // Update empty adapter state
                wishlistItemsGridAdapter.setItems(itemsToBeDisplayed)
            }
        })
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}