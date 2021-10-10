package development.dreamcatcher.clothesshopapp.ui.detailedview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import development.dreamcatcher.clothesshopapp.R
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.injection.ClothesShopApp
import kotlinx.android.synthetic.main.detailed_view.*
import javax.inject.Inject

// Detailed view for displaying chosen item
class DetailedViewFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedViewViewModel

    init {
        ClothesShopApp.mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailedViewViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detailed_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch detailed features from Data Repository
        subscribeForItem()

        // Setup Cross Button
        val closingOnClickListener = View.OnClickListener{ activity?.onBackPressed() }
        btn_cross.setOnClickListener(closingOnClickListener)

        // Setup closing on the grey fields' click
        spacing_top.setOnClickListener(closingOnClickListener)
        spacing_bottom.setOnClickListener(closingOnClickListener)
    }

    private fun subscribeForItem() {
        val itemId = this.arguments?.getInt("itemId")
        itemId?.let {
            viewModel.getSingleSavedItemById(itemId)?.observe(this, Observer<ItemDatabaseEntity> {
                val searchUrl = getString(R.string.base_search_url) + it.name
                setupWebView(searchUrl)
            })
        }
    }

    // Setup website view
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(url: String) {
        website_view.settings.javaScriptEnabled = true
        website_view.webViewClient = WebViewClient()
        website_view.loadUrl(url)
        showLoadingView(false)
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}