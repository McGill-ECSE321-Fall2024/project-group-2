<template>
    <div id="browse-page">
        
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
                    </div>
                </div>
            </div>
            <hr />
        </header>

        
        <div class="browse-controls">
            <div class="search-bar">
                <input type="text" v-model="searchQuery" @input="handleSearch" placeholder="Search games..."
                    class="search-input" />
            </div>

            <div class="filter-controls">
                <select v-model="selectedCategory" @change="filterProducts" class="category-select">
                    <option value="">All Categories</option>
                    <option v-for="category in categories" :key="category.id" :value="category.id">
                        {{ category.name }}
                    </option>
                </select>

                <select v-model="priceSort" @change="sortProducts" class="sort-select">
                    <option value="">Sort by Price</option>
                    <option value="high">Highest to Lowest</option>
                    <option value="low">Lowest to Highest</option>
                </select>
            </div>
        </div>

        
        <div class="products-grid">
            <div v-for="product in displayedProducts" :key="product.id" class="product-card"
                @click="goToProduct(product)">
                <img :src="product.imageURL ? `data:image/jpeg;base64,${product.imageURL}` : '@/assets/picture1.jpg'"
                    :alt="product.name" class="product-image" />
                <div class="product-info">
                    <h3>{{ product.name }}</h3>
                    <p class="price">${{ product.lineItem?.price || '0.00' }}</p>
                </div>
            </div>
        </div>

        
        <div v-if="displayedProducts.length === 0" class="no-results">
            <p>No products found matching your search.</p>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

//for api calls
const axiosClient = axios.create({
    baseURL: 'http://localhost:8080'
});

export default {
    name: 'SearchView',
    data() {
        return {
            products: [],
            categories: [],
            searchQuery: '',
            selectedCategory: '',
            priceSort: '',
            userEmail: '',
            userName: '',
            displayedProducts: []
        };
    },
    // runs when first created
    async created() {
        // load user info
        this.userEmail = localStorage.getItem('userEmail') || '';
        this.userName = localStorage.getItem('userName') || '';

        // load initial data
        await this.loadCategories();
        await this.loadAllProducts();
    },
    methods: {
        // gets all the categories from db
        async loadCategories() {
            try {
                const response = await axiosClient.get('/category');
                console.log('Raw category response:', response.data);
                this.categories = response.data.category;
            } catch (error) {
                console.error('Error loading categories:', error);
            }
        },

        // gets all games from db
        async loadAllProducts() {
            try {
                const response = await axiosClient.get('/product');
                this.products = await this.enrichProductsWithLineItems(response.data.products);
                this.displayedProducts = [...this.products];
            } catch (error) {
                console.error('Error loading products:', error);
            }
        },

        async enrichProductsWithLineItems(products) {
            // get more game details from line item
            return Promise.all(products.map(async (product) => {
                if (product.lineItem_id) {
                    try {
                        const lineItemResponse = await axiosClient.get(`/lineitems/${product.lineItem_id}`);
                        product.lineItem = lineItemResponse.data;
                    } catch (error) {
                        console.error(`Error fetching line item for product ${product.id}:`, error);
                    }
                }
                return product;
            }));
        },

        // for user typing
        async handleSearch() {
            if (this.searchQuery.trim() === '') {
                // if search is empty, show all products (with current category filter)
                this.filterProducts();
                return;
            }

            try {
                const response = await axiosClient.get(`/product/name/${this.searchQuery}`);
                if (response.data) {
                const product = response.data;
                const enrichedProduct = await this.enrichProductsWithLineItems([product]);
                this.displayedProducts = enrichedProduct;
                } else {
                this.displayedProducts = [];
                }
                this.applyFilters();
            } catch (error) {
                console.error('Error searching products:', error);
                this.displayedProducts = [];
            }
        },

        //filter products by category
        async filterProducts() {
            if (this.selectedCategory) {
                try {
                    const response = await axiosClient.get(`/product/category/${this.selectedCategory}`);
                    this.displayedProducts = await this.enrichProductsWithLineItems(response.data.products);
                } catch (error) {
                    console.error('Error filtering by category:', error);
                }
            } else {
                this.displayedProducts = [...this.products];
            }
            this.applyFilters();
        },

        // dort products by price
        sortProducts() {
            const sorted = [...this.displayedProducts];
            if (this.priceSort === 'high') {
                sorted.sort((a, b) => (b.lineItem?.price || 0) - (a.lineItem?.price || 0));
            } else if (this.priceSort === 'low') {
                sorted.sort((a, b) => (a.lineItem?.price || 0) - (b.lineItem?.price || 0));
            }
            this.displayedProducts = sorted;
        },

        applyFilters() {
            // apply price sorting after filtering
            if (this.priceSort) {
                this.sortProducts();
            }
        },

        goToProduct(product) {
            this.$router.push(`/product/${product.id}`);
        },

        signOut() {
            localStorage.removeItem('userEmail');
            localStorage.removeItem('userRole');
            localStorage.removeItem('userName');
            localStorage.removeItem('sessionCart');
            localStorage.removeItem('userWishlist');
            this.userEmail = '';
            this.userName = '';
            this.$router.push({ name: 'signin' });
        }
    }
};
</script>

<style scoped>
#browse-page {
    background-color: #000;
    color: #fff;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    width: 85vw;
    padding: 0 auto;
    max-width: 1300px;
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

.header-right {
    display: flex;
    align-items: center;
}

.user-email {
    margin-right: 16px;
    font-size: 16px;
}

.sign-in-button,
.sign-out-button {
    background-color: #1a73e8;
    color: white;
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
}

.browse-controls {
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.search-bar {
    display: flex;
    justify-content: center;
}

.search-input {
    width: 100%;
    max-width: 600px;
    padding: 12px;
    border: 1px solid #333;
    border-radius: 8px;
    background-color: #121212;
    color: white;
    font-size: 16px;
}

.filter-controls {
    display: flex;
    justify-content: center;
    gap: 16px;
}

.category-select,
.sort-select {
    padding: 8px 16px;
    border: 1px solid #333;
    border-radius: 4px;
    background-color: #121212;
    color: white;
    cursor: pointer;
}

.products-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 24px;
    padding: 20px;
}

.product-card {
    background-color: #121212;
    border-radius: 8px;
    overflow: hidden;
    cursor: pointer;
    transition: transform 0.2s;
}

.product-card:hover {
    transform: translateY(-5px);
}

.product-image {
    width: 100%;
    height: 250px;
    object-fit: cover;
}

.product-info {
    padding: 12px;
}

.product-info h3 {
    margin: 0;
    font-size: 16px;
    font-weight: bold;
}

.price {
    margin: 8px 0 0;
    color: #1a73e8;
    font-weight: bold;
}

.no-results {
    text-align: center;
    padding: 40px;
    color: #666;
}

hr {
    border: 0;
    border-top: 1px solid #333;
    margin: 0;
}
</style>