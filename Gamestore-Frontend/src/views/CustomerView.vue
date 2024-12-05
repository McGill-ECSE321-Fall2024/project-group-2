
<template>
  <div id="homepage">
        <header>
            <div class="header-top">
                <div class="header-left">
                    <img src="@/assets/logo.png" alt="Logo" class="logo" />
                    <h1>Game Store</h1>
                </div>
                <div class="header-right">
                    <div v-if="userEmail">
                        <span class="user-email">Welcome, {{ userName }}!</span>
                        <button @click="signOut" class="sign-out-button">Sign Out</button>
                    </div>
                    <div v-else>
                        <router-link to="/signin" class="sign-in-button">Sign In</router-link>
                        <router-link to="/dashboard" class="sign-in-button">DashBoard</router-link>

                    </div>
                </div>
            </div>
            <hr />

            <div class="header-center">
                <div class="header-left-side">
                </div>
                <div class="header-right-side">
                    <router-link to="/shoppingcart" class="icon-link">
                        <img :src="cartIcon" alt="Cart" height="40" width="40">
                    </router-link>
                    <router-link to="/wishlist" class="icon-link">
                        <img :src="wishlistIcon" alt="Wishlist" height="40" width="40">
                    </router-link>
                </div>
            </div>
        </header>

    <main>
      <section class="all-products">
        <h2>All Products</h2>
        <div v-if="loading" class="loading">
          Loading products...
        </div>
        <div v-else-if="error" class="error">
          {{ error }}
        </div>
        <div v-else class="card-container">
          <div
            v-for="product in filteredProducts"
            :key="product.id"
            class="game-card"
            @click="redirectToProduct(product.id)"
          >
            <img
              :src="product.imageURL ? `data:image/jpeg;base64,${product.imageURL}` : '/default-image.png'"
              :alt="product.name"
              class="game-image"
            />
            <h3>{{ product.name }}</h3>
            <p>{{ product.description }}</p>
          </div>
        </div>
      </section>
    </main>

    <footer>
      <p>©️ 2024 Game Store. All Rights Reserved.</p>
      <ul>
        <li><a href="#">Terms of Service</a></li>
        <li><a href="#">Privacy Policy</a></li>
        <li><a href="#">Contact Us</a></li>
      </ul>
    </footer>
  </div>
</template>

<script>
import cartIcon from '../assets/shopping-cart.svg'
import wishlistIcon from '../assets/wishlist-icon.svg'

import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: "HomeView",
  data() {
    return {
      products: [],
      filteredProducts: [],
      searchQuery: '',
      loading: true,
      error: null
    };
  },
  methods: {
    async fetchProducts() {
      try {
        this.loading = true;

        // get products from backend
        const response = await axios.get('http://localhost:8080/product');

        console.log('Full Response:', response);
        console.log('Response Data:', response.data);

        
        this.products = response.data.products || response.data;
        this.filteredProducts = this.products;

        this.loading = false;
      } catch (error) {
        this.loading = false;
        this.error = 'Failed to load products';

        console.error('Error fetching products:', error);

  
        if (error.response) {
          console.error('Error data:', error.response.data);
          console.error('Error status:', error.response.status);
        }
      }
    },
    // gives all products if no search, if there is gives product with name or description matching query
    filterProducts() {
      if (!this.searchQuery) {
        this.filteredProducts = this.products;
      } else {
        this.filteredProducts = this.products.filter(product =>
          product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
          (product.description && product.description.toLowerCase().includes(this.searchQuery.toLowerCase()))
        );
      }
    },
    redirectToProduct(productId) {
      // redirect to the ProductView with the product ID
      this.$router.push({ name: 'product', params: { id: productId } });
    }
  },
  mounted() {
    this.fetchProducts();
  }
};
</script>


<style scoped>


/* Logo Styling */
.logo {
  height: 50px;
  width: 50px;
  margin-right: 10px; /* Add spacing between the logo and the heading */
  display: inline-block;
  vertical-align: middle; /* Align logo vertically with text */
  border-radius: 28px;
  margin-left: 10px;
}

#homepage {
  background-color: #000; /* Black background */
  color: #fff; 
  min-height: 100vh; /* Cover full screen */
  display: flex;
  flex-direction: column;
  min-width: 210%; 
  margin-left: -5.7vh;
  overflow-y: hidden;
}

.logo {
    height: 50px;
    width: 50px;
    margin-right: 10px;
    display: inline-block;
    vertical-align: middle;
    border-radius: 28px;
    margin-left: 10px;
}

header {
    background-color: #000;
    color: white;
}

.header-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 32px;
}

.header-top .header-left {
    display: flex;
    align-items: center;
}

.header-top .header-left h1 {
    font-size: 24px;
    font-weight: bold;
}


.header-top .header-right {
    display: flex;
    align-items: center;
}

/* Card Container */
.card-container {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* 5 cards in a row */
  gap: 16px; /* Space between cards */
  justify-content: center; /* Center the grid */
}

.game-card {
  background-color: #000; /* Darker card background */
  color: white;
  font-size: 12px;
  border-radius: 8px;
  padding: 0px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.game-card img {
  width: 170px; /* Uniform image width */
  height: 200px; /* Uniform image height */
  border-radius: 3px;
  margin-bottom: 8px;
}

.game-card h3 {
  font-weight: bold; /* Bold game name */
  margin: 8px 0 4px;
}

.game-card p {
  font-weight: normal; /* Keep price in normal font */
  margin: 0;
}

.vertical-section {
  flex: 1;
  text-align: center;
  width: 20px;
}

.vertical-divider {
  width: 1px;
  background-color: #555;
  margin: 0 16px;
}

.vertical-card-container {
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 100px;
  margin-left: 0px;
}

.horizontal-section {
  display: flex;
  gap: 12px;
  justify-content: space-between;
  margin-bottom: 100px;
}

/* Footer */
footer {
  background-color: #121212;
  color: white;
  text-align: center;
  padding: 16px;
  margin-top: 16px;
}
.game-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 8px;
}

.sign-in-button {
  background-color: #333;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  text-decoration: none; /* Remove underline from link */
}
.sign-in-button:hover {
  background-color: #555;
}

/* List Card Styling (for specific sections) */
.list-card-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.list-card {
  display: flex;
  align-items: flex-start;
  gap: 0px;
}

.list-game-image {
  width: 80px;
  height: 80px;
  border-radius: 8px;
}

.list-card-content {
  flex: 1;
  margin-left: 10px;
  text-align: left; /* Align text to the left */
}
.game-name {
  font-weight: bold; /* Make game name bold */
  margin: 0;
}

.game-price {
  font-weight: normal; /* Keep price in normal font */
  margin: 4px 0 0;
}

/* Featured Section */
#featured-section {
  display: flex;
  justify-content: space-between;
  margin-bottom: 32px;
  padding: 16px;
  background-color: #000;
}

.featured-content {
  display: flex;
  gap: 16px;
  width: 100%;
}

.featured-banner {
  flex: 3;
  position: relative;
  border-radius: 8px;
  overflow: hidden;
}

.featured-image {
  width: 900px;
  height: 400px;
  border-radius: 8px;
}

.banner-details {
  position: absolute; /* Layer the text over the image */
    bottom: 10px;
    left: 16px;
    color: white;
    padding: 16px;
    border-radius: 8px;
    max-width: 70%;
}

.banner-details h2 {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
  margin-right: 100px;
}

.banner-details p {
  font-size: 14px;
  margin-bottom: 16px;
}

.save-now-btn {
  background-color: white;
  color: #000;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.featured-side-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.side-list-item {
  display: flex;
  align-items: center;
  padding: 8px;
  background-color: #121212;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.side-list-item.active {
  background-color: #333;
}

.side-thumbnail {
  width: 50px;
  height: 50px;
  border-radius: 4px;
  margin-right: 12px;
}

.side-text {
  color: white;
  font-size: 14px;
  font-weight: bold;
}

.side-list-item:hover {
  background-color: #444;
}
/* Free Game Section */
.free-games {
  background-color: #121212;
  padding: 16px;
  width: 700px;
  border-radius: 8px;
  margin-left: 250px;
  margin-bottom: 100px;
  margin-top: 100px;
}

.free-games-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.free-games-container {
  display: flex;
  gap: 20px;
}

.free-game-card {
  background-color: #000;
  border-radius: 8px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.free-game-image {
  width: 100%;
  border-radius: 8px;
}

.free-game-details h3 {
  font-weight: bold;
  margin: 8px 0;
}

.free-now-btn {
  background-color: #1a73e8;
  color: black;
  border: none;
  font-weight: bold;
  width: 200px;
  padding: 8px 16px;
  border-radius: 4px;
  margin-bottom: 8px;
}

.coming-soon-btn {
  background-color: #555;
  color: white;
  font-weight: bold;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  margin-bottom: 8px;
}

.loading, .error {
  text-align: center;
  padding: 20px;
  color: #aaa;
}

.card-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  justify-content: center;
  padding: 20px;
}

.game-card {
  background-color: #121212;
  border-radius: 8px;
  padding: 16px;
  text-align: center;
  transition: transform 0.3s ease;
}

.game-card:hover {
  transform: scale(1.05);
}

.game-image {
  width: 100%;
  height: 250px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 12px;
}

.game-card h3 {
  font-weight: bold;
  margin-bottom: 8px;
}

.game-card p {
  color: #aaa;
  font-size: 0.9em;
}
</style>