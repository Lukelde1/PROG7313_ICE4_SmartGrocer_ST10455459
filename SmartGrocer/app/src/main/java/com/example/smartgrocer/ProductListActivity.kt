package com.example.smartgrocer

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var tvEmpty: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerFilter: Spinner

    private val database = FirebaseDatabase.getInstance(
        "https://smartgrocer-5a9d6-default-rtdb.europe-west1.firebasedatabase.app"
    ).getReference("products")
    private val allProducts = mutableListOf<Product>()

    private val allCategories = listOf(
        "All", "Fruits", "Vegetables", "Dairy", "Bakery",
        "Beverages", "Meat", "Snacks", "Other"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        tvEmpty = findViewById(R.id.tvEmpty)
        progressBar = findViewById(R.id.progressBar)
        spinnerFilter = findViewById(R.id.spinnerFilter)

        adapter = ProductAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val filterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, allCategories)
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter = filterAdapter

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                filterProducts(allCategories[pos])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        loadProducts()
    }

    private fun loadProducts() {
        progressBar.visibility = View.VISIBLE

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                progressBar.visibility = View.GONE
                allProducts.clear()

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        allProducts.add(product)
                    }
                }

                filterProducts(spinnerFilter.selectedItem?.toString() ?: "All")
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                Toast.makeText(
                    this@ProductListActivity,
                    "Failed to load products: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun filterProducts(category: String) {
        val filtered = if (category == "All") {
            allProducts.toList()
        } else {
            allProducts.filter { it.category == category }
        }

        adapter.updateList(filtered)

        if (filtered.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
