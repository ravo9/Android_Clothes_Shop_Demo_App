package development.dreamcatcher.clothesshopapp.ui.feed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.clothesshopapp.R
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.ui.detailedview.DetailedViewFragment
import development.dreamcatcher.clothesshopapp.injection.ClothesShopApp
import development.dreamcatcher.clothesshopapp.ui.cart.CartFragment
import development.dreamcatcher.clothesshopapp.ui.wishlist.WishlistFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.loading_badge.*
import javax.inject.Inject

// Main ('feed') view
class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FeedViewModel
    private lateinit var itemsGridAdapter: ItemsGridAdapter

    private val STATE_LOADING_ERROR = "STATE_LOADING_ERROR"
    private val STATE_CONTENT_LOADED = "STATE_CONTENT_LOADED"

    init {
        ClothesShopApp.mainComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel::class.java)

        // Initialize RecyclerView (items List)
        setupRecyclerView()

        // Fetch items from the back-end and load them into the view
        subscribeForItems()

        // Catch and handle potential network issues
        subscribeForNetworkError()
    }

    private fun setupRecyclerView() {
        itemsGridAdapter = ItemsGridAdapter(
            this.baseContext,
            this::displayDetailedView,
            this::addItemToCart,
            this::addItemToWhishlist
        )
        main_feed_gridView.adapter = itemsGridAdapter
    }

    private fun subscribeForItems() {
        viewModel.getAllItems(true)?.observe(this, Observer<List<ItemDatabaseEntity>> {

            if (!it.isNullOrEmpty()) {
                setViewState(STATE_CONTENT_LOADED)

                // Display fetched Items
                itemsGridAdapter.setItems(it)
            }
        })
    }

    private fun subscribeForNetworkError() {
        viewModel.getNetworkError()?.observe(this, Observer<Boolean> {

            // In case of Network Error...
            // If no items have been cached
            if (itemsGridAdapter.count == 0) {
                setViewState(STATE_LOADING_ERROR)
            }

            // Display error message to the user
            Toast.makeText(this, R.string.network_problem_check_internet_connection,
                Toast.LENGTH_LONG) .show()
        })
    }

    private fun refreshItemsSubscription() {
        viewModel.getAllItems(true)?.removeObservers(this)
        subscribeForItems()
    }

    private fun displayDetailedView(itemId: Int) {
        val fragment = DetailedViewFragment()
        val bundle = Bundle()
        bundle.putInt("itemId", itemId)
        fragment.arguments = bundle

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun displayCart() {
        val fragment = CartFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun displayWishlist() {
        val fragment = WishlistFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setViewState(state: String) {
        when(state) {
            STATE_LOADING_ERROR -> setupLoadingErrorView()
            STATE_CONTENT_LOADED -> setupContentLoadedView()
        }
    }

    private fun setupLoadingErrorView() {
        // Stop the loading progress bar (circle)
        progressBar.visibility = View.INVISIBLE

        // Display "Try Again" button
        tryagain_button.visibility = View.VISIBLE

        // Setup onClick listener that resets Items features subscription
        tryagain_button.setOnClickListener {
            refreshItemsSubscription()

            // Re-display the loading progress bar (circle)
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun setupContentLoadedView() {
        // Hide the loading view
        loading_container.visibility = View.GONE
        appbar_container.visibility = View.VISIBLE

        // Setup cart button
        btn_cart.setOnClickListener{
            displayCart()
        }

        // Setup wishlist button
        btn_wishlist.setOnClickListener{
            displayWishlist()
        }

        // Setup refresh button
        btn_refresh.setOnClickListener{
            refreshItemsSubscription()
        }

        // Setup sort button
        btn_sort.setOnClickListener{
            changeSortingOrder()
        }
    }

    private fun changeSortingOrder() {
        itemsGridAdapter.changeSortingOrder()
    }

    @SuppressLint("CheckResult")
    private fun addItemToCart(itemId: Int) {
        viewModel.addItemToCart(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, R.string.item_added_to_cart, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(this, R.string.error_item_couldnt_be_added, Toast.LENGTH_SHORT).show()
                })
    }

    @SuppressLint("CheckResult")
    private fun addItemToWhishlist(itemId: Int) {
        viewModel.addItemToWishlist(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, R.string.item_added_to_wishlist, Toast.LENGTH_SHORT).show()
                },
                {
                    Toast.makeText(this, R.string.error_item_couldnt_be_added, Toast.LENGTH_SHORT).show()
                })
    }
}
