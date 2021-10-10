package development.dreamcatcher.clothesshopapp.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDao
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabase
import development.dreamcatcher.clothesshopapp.features.items.database.ItemsDatabaseInteractor
import development.dreamcatcher.clothesshopapp.features.items.network.ItemGsonObject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ItemsDatabaseInteractorTest {

    private var itemsDatabaseInteractor: ItemsDatabaseInteractor? = null
    private var fakeItemGsonObject: ItemGsonObject? = null
    private var fakeItemDatabaseEntity: ItemDatabaseEntity? = null

    @Mock
    private val itemsDatabase: ItemsDatabase? = null

    @Mock
    private val itemsDao: ItemsDao? = null

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setupTest() {

        // Inject Mocks
        MockitoAnnotations.initMocks(this)

        // Initialize the Interactor
        itemsDatabaseInteractor =
            ItemsDatabaseInteractor(
                itemsDatabase!!
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
    fun saveItemByDatabaseInteractor() {

        // Perform the action
        val resultStatus = itemsDatabaseInteractor!!.addNewItem(fakeItemGsonObject).value

        // Check results
        Assert.assertTrue(resultStatus!!)
    }

    @Test
    fun fetchItemByDatabaseInteractor() {

        // Prepare LiveData structure
        val itemEntityLiveData = MutableLiveData<ItemDatabaseEntity>()
        itemEntityLiveData.setValue(fakeItemDatabaseEntity);

        // Set testing conditions
        Mockito.`when`(itemsDatabase?.getItemsDao()).thenReturn(itemsDao)
        Mockito.`when`(itemsDao?.getSingleSavedItemById(anyInt())).thenReturn(itemEntityLiveData)

        // Perform the action
        val storedItem = itemsDatabaseInteractor?.getSingleSavedItemById(123)

        // Check results
        Assert.assertSame(itemEntityLiveData, storedItem)
    }
}