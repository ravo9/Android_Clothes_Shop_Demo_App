package development.dreamcatcher.clothesshopapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.ui.detailedview.DetailedViewViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailedViewViewModelTest {

    private var viewModel: DetailedViewViewModel? = null
    private var fakeItemDatabaseEntity: ItemDatabaseEntity? = null

    @Mock
    private val itemsRepository: ItemsRepository? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the item
        viewModel = DetailedViewViewModel(itemsRepository!!)

        // Prepare fake features
        val id = 123
        val name = "fake/item/name"
        val category = "fake/item/category"
        val price = 100.0f

        // Prepare fake Item Entity (DB object)
        fakeItemDatabaseEntity =
            ItemDatabaseEntity(
                id,
                name,
                category,
                price,
                null,
                null
            )
    }

    @Test
    fun fetchItemByFeedViewModel() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemDatabaseEntity>()
        itemEntityLiveData.setValue(fakeItemDatabaseEntity);

        // Prepare fake item id
        val fakeItemId = 123

        // Set testing conditions
        Mockito.`when`(itemsRepository?.getSingleSavedItemById(fakeItemId))
            .thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItem = viewModel?.getSingleSavedItemById(fakeItemId)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItem);
    }
}