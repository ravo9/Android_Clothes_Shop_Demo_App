package development.dreamcatcher.clothesshopapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.clothesshopapp.features.items.ItemsRepository
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.ItemsNetworkInteractor
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ItemsRepositoryTest {

    private var itemsRepository: ItemsRepository? = null
    private var fakeItemDatabaseEntity: ItemDatabaseEntity? = null
    private var fakeItemEntitiesList = ArrayList<ItemDatabaseEntity>()

    @Mock
    private val itemsDatabaseInteractor: ItemsDatabaseInteractor? = null

    @Mock
    private val itemsNetworkInteractor: ItemsNetworkInteractor? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the repository
        itemsRepository = ItemsRepository(
            itemsNetworkInteractor!!,
            itemsDatabaseInteractor!!
        )

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

        // Prepare fake Items Entities List
        fakeItemEntitiesList.add(fakeItemDatabaseEntity!!)
    }

    @Test
    fun fetchAllItemsByItemsRepository() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<List<ItemDatabaseEntity>>()
        itemEntityLiveData.setValue(fakeItemEntitiesList);

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getAllItems()).thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItems = itemsRepository?.getAllItems(false)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItems);
    }

    @Test
    fun fetchItemByItemsRepository() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemDatabaseEntity>()
        itemEntityLiveData.setValue(fakeItemDatabaseEntity);

        // Prepare fake item id
        val fakeItemId = 123

        // Set testing conditions
        Mockito.`when`(itemsDatabaseInteractor?.getSingleSavedItemById(fakeItemId))
            .thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItem = itemsRepository?.getSingleSavedItemById(fakeItemId)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItem);
    }
}