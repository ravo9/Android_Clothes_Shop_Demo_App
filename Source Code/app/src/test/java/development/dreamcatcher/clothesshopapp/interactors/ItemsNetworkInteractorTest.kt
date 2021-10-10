package development.dreamcatcher.clothesshopapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import development.dreamcatcher.clothesshopapp.features.items.network.ItemGsonObject
import development.dreamcatcher.clothesshopapp.features.items.network.RestClient
import development.dreamcatcher.clothesshopapp.features.items.network.ItemsNetworkInteractor
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ItemsNetworkInteractorTest {

    private var itemsNetworkInteractor: ItemsNetworkInteractor? = null
    private var fakeItemGsonObject: ItemGsonObject? = null

    @Mock
    private val restClient: RestClient? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        itemsNetworkInteractor =
            ItemsNetworkInteractor(
                restClient!!
            )

        // Prepare fake features
        val id = 123
        val name = "fake/item/name"
        val category = "fake/item/category"
        val price = 100.0f

        // Prepare fake Gson (API) object
        fakeItemGsonObject =
            ItemGsonObject(
                id,
                name,
                category,
                price,
                null,
                null
            )
    }
}
