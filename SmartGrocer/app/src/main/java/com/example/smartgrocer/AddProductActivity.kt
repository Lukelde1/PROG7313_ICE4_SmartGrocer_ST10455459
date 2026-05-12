package com.example.smartgrocer

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddProductActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance(
        "https://smartgrocer-5a9d6-default-rtdb.europe-west1.firebasedatabase.app"
    ).getReference("products")

    private val categories = listOf(
        "Fruits", "Vegetables", "Dairy", "Bakery",
        "Beverages", "Meat", "Snacks", "Other"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etName = findViewById<EditText>(R.id.etProductName)
        val etPrice = findViewById<EditText>(R.id.etProductPrice)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val switchAvailable = findViewById<Switch>(R.id.switchAvailable)
        val etImageUrl = findViewById<EditText>(R.id.etImageUrl)
        val btnSave = findViewById<Button>(R.id.btnSaveProduct)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        btnSave.setOnClickListener {
            saveProduct(etName, etPrice, spinnerCategory, switchAvailable, etImageUrl)
        }
    }

    private fun saveProduct(
        etName: EditText,
        etPrice: EditText,
        spinnerCategory: Spinner,
        switchAvailable: Switch,
        etImageUrl: EditText
    ) {
        val name = etName.text.toString().trim()
        val priceText = etPrice.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val isAvailable = switchAvailable.isChecked
        val imageUrl = etImageUrl.text.toString().trim()

        if (name.isEmpty()) {
            etName.error = "Product name is required"
            etName.requestFocus()
            return
        }

        if (priceText.isEmpty()) {
            etPrice.error = "Price is required"
            etPrice.requestFocus()
            return
        }

        val price = priceText.toDoubleOrNull()
        if (price == null || price < 0) {
            etPrice.error = "Enter a valid positive price"
            etPrice.requestFocus()
            return
        }

        val productId = database.push().key ?: return
        val product = Product(
            id = productId,
            name = name,
            price = price,
            category = category,
            isAvailable = isAvailable,
            imageUrl = imageUrl
        )

        database.child(productId).setValue(product)
            .addOnSuccessListener {
                Toast.makeText(this, "${product.name} added successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
