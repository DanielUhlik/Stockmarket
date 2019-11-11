package sk.icicleworks.stockmarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import sk.icicleworks.stockmarket.adapters.PinnedStocksAdapter
import sk.icicleworks.stockmarket.viewModels.MainActivityViewModel
import kotlin.collections.ArrayList
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val pinnedStocksAdapter = PinnedStocksAdapter(ArrayList()) {stockId ->
            viewModel.removePinnedStock(stockId)
        }

        pinnedStocksRecyclerView.setHasFixedSize(true)
        pinnedStocksRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        pinnedStocksRecyclerView.adapter = pinnedStocksAdapter

        viewModel.getPinnedStockWithData.observe(this, Observer {
            pinnedStocksAdapter.setData(it)
        })

        addPinnedStockFab.setOnClickListener {
            showInputDialog()
        }

    }

    //TO-DO: Input validation
    fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.input_company_dialog_title))
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.hint = getString(R.string.stock_id_placeholder)
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.ok)
        ) { _, _ -> viewModel.addPinnedStock(input.text.toString()) }
        builder.setNegativeButton(getString(R.string.cancel)
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }


    companion object {
        val A = 1
    }
}
