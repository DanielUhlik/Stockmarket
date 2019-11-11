package sk.icicleworks.stockmarket.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stock_item.view.*
import sk.icicleworks.stockmarket.R
import sk.icicleworks.stockmarket.charts.ChartFactory
import sk.icicleworks.stockmarket.database.entity.PinnedStock
import sk.icicleworks.stockmarket.database.entity.PinnedStockWithData
import sk.icicleworks.stockmarket.database.entity.StockData
import sk.icicleworks.stockmarket.extensions.inflate

class PinnedStocksAdapter(var pinnedStocks: List<PinnedStockWithData>, val onClickListener: (stockId : String) -> Unit) : RecyclerView.Adapter<PinnedStocksAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(item: PinnedStockWithData, onClickListener : (stockId : String) -> Unit) = with(itemView) {
            stockId_tv.text = item.pinnedStock?.stockId
            ChartFactory.initializeLineChart(chart, item.data ?: listOf())
            remove_btn.setOnClickListener { onClickListener(item.pinnedStock?.stockId!!) }
        }
    }

    fun setData(newData : List<PinnedStockWithData>){
        this.pinnedStocks = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.stock_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(pinnedStocks[position], onClickListener)

    override fun getItemCount() = pinnedStocks.size
}