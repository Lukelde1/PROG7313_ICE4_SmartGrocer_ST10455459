package com.example.smartgrocer

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val tvCategory: TextView = itemView.findViewById(R.id.tvProductCategory)
        val tvAvailability: TextView = itemView.findViewById(R.id.tvAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvName.text = product.name
        holder.tvPrice.text = "R %.2f".format(product.price)
        holder.tvCategory.text = product.category

        if (product.isAvailable) {
            holder.tvAvailability.text = "In Stock"
            holder.tvAvailability.setTextColor(Color.parseColor("#2E7D32"))
            holder.tvAvailability.setBackgroundResource(R.drawable.bg_in_stock)
        } else {
            holder.tvAvailability.text = "Out of Stock"
            holder.tvAvailability.setTextColor(Color.parseColor("#C62828"))
            holder.tvAvailability.setBackgroundResource(R.drawable.bg_out_of_stock)
        }
    }

    override fun getItemCount(): Int = products.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
