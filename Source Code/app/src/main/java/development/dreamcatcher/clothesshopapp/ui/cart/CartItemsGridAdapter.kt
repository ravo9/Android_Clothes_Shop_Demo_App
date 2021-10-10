package development.dreamcatcher.clothesshopapp.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import development.dreamcatcher.clothesshopapp.R
import development.dreamcatcher.clothesshopapp.features.items.database.ItemDatabaseEntity
import kotlinx.android.synthetic.main.grid_single_item.view.name
import kotlinx.android.synthetic.main.grid_single_item_cart.view.*

// Main adapter used for managing items grid within the main GridView (main feed listed)
class CartItemsGridAdapter (val context: Context,
                            val removeItemClickListener: (Int) -> Unit) : BaseAdapter() {

    private var itemsList: List<ItemDatabaseEntity> = ArrayList()

    fun setItems(items: List<ItemDatabaseEntity>) {
        this.itemsList = items
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return itemsList.size
    }

    override fun getItem(position: Int): Any {
        return itemsList[position]
    }

    override fun getItemId(position: Int): Long {
        return itemsList[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        val holder: ViewHolder
        val inflater = LayoutInflater.from(parent?.context)

        // Inflate/ re-use the view
        if (view == null) {
            view = inflater.inflate(R.layout.grid_single_item_cart, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        // Prepare fetched features
        val name = itemsList[position].name
        val category = itemsList[position].category
        val price = itemsList[position].price
        val oldPrice = itemsList[position].oldPrice
        val stock = itemsList[position].stock

        // Set features within the holder
        holder.name.text = name
        holder.category.text = category
        holder.price.text = price.toString()
        oldPrice?.let {
            holder.oldPrice.text = it.toString()
        }
        stock?.let {
            holder.stock.text = it.toString()
        }

        // Set onClickListeners
        holder.btnRemove.setOnClickListener{
            val itemId = itemsList[position].id
            removeItemClickListener(itemId)
        }

        // In this exceptional case we use '!!' as we know that he view will be either inflated or re-used.
        return view!!
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val name = view.name
        val category = view.category
        val price = view.price
        val oldPrice = view.oldPrice
        val stock = view.stock
        val btnRemove = view.btn_remove
    }
}