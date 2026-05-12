# PROG7313_ICE4_SmartGrocer_ST10455459

## SmartGrocer – Product Info Manager
A lightweight Android application built for local grocery store managers to add and view product information in real-time using Firebase Realtime Database.

## Features
- **Add Products** – Enter product name, price, category, and availability status through a clean, validated form
- **View Products** – Browse all products in a scrollable list with card-based layout
- **Real-Time Sync** – All data is stored and retrieved from Firebase Realtime Database, so changes appear instantly
- **Input Validation** – Prevents empty fields and ensures price is a valid positive number
- **Category Filter** – Filter the product list by category (Fruits, Vegetables, Dairy, Bakery, Beverages, Meat, Snacks, Other)
- **Stock Toggle** – Mark products as In Stock or Out of Stock with a switch control
- **Image URL Support** – Optional field to attach an image URL to each product
- **Unique IDs** – Each product is stored with a Firebase-generated unique key

## Firebase Setup
The `google-services.json` file is included in the repository. The app connects to a pre-configured Firebase Realtime Database in test mode, so no additional setup is required — just clone, sync Gradle, and run.
