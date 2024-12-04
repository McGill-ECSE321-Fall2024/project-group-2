<template>
    <div id="product-page">
        
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
            
            <div class="header-center">
                <div class="header-left-side">
                    <button class="browse-btn">Browse</button>
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

        <!-- notification for add to cart-->
        <div class="notification" ref="notification">Added to cart successfully!</div>

        <!-- Main Product Content -->
        <main class="product-content">
            <div class="product-container">
                <!-- Left side - Image -->
                <div class="product-image-section">
                    <img :src="product.imageURL ? `data:image/jpeg;base64,${product.imageURL}` : '@/assets/picture1.jpg'" 
                        :alt="product.name" class="product-image" />
                </div>


                <!-- Right side - Product Info -->
                <div class="product-info-section">
                    <h1 class="product-title">{{ product.name }}</h1>
                    <p class="product-category">{{ product.category?.name }}</p>
                    <!-- Stars and Average Rating -->
                    <div class="product-rating">
                        <span v-for="n in 5" :key="n">
                            <i class="star" :class="{ 'filled': n <= Math.round(averageRating) }
                                ">★</i>
                        </span>
                        <span class="average-rating">{{ averageRating }} / 5</span>
                        <span class="review-count">({{ reviewCount }} reviews)</span>
                    </div>
                    <p class="product-description">{{ product.description }}</p>
                    <div class="product-price">
                        ${{ product.lineItem?.price || '0.00' }}
                    </div>
                    <div class="product-actions">
                        <button class="add-to-cart-btn" @click="addToCart">Add to Cart</button>
                        <button class="add-to-wishlist-btn" @click="addToWishlist">Add to Wishlist</button>
                    </div>
                </div>
            </div>

            <section class="review-section">
                <h2>Customer Reviews</h2>
                <!-- Add Review Button -->
                <button class="add-review-btn" @click="goToAddReview">Add a Review</button>
                <div v-if="reviews.length > 0">
                    <div v-for="review in reviews" :key="review.reviewId" class="review">
                        <div class="review-header">
                            <strong>{{ review.reviewWriterEmail.split('@')[0] }}</strong>
                            <span class="review-date">{{ formatDate(review.reviewDate) }}</span>
                        </div>
                        <p class="review-comment">{{ review.comments }}</p>
                        <p class="review-rating">Rating: {{ review.rating }}/5</p>
                    </div>
                </div>
                <div v-else>
                    <p>No reviews yet. Be the first to review this product!</p>
                </div>
            </section>
        </main>
    </div>
</template>


<script>

import cartIcon from '../assets/shopping-cart.svg'
import wishlistIcon from '../assets/wishlist-icon.svg'

import axios from "axios";

// for api calls
const axiosClient = axios.create({
    baseURL: "http://localhost:8080"
});


export default {
    name: 'ProductView',
    // all data properties needed
    data() {
        return {
            cartIcon,
            wishlistIcon,
            product: {
                name: '',
                description: '',
                lineItem: null,
                category: null,
                price: 0
            },
            reviews: [],
            averageRating: 0,
            reviewCount: 0,
            userEmail: '',
            userName: '',  
        };
    },
    // runs when component is created
    async created() {
        try {
            let response;
            // check if we're using name or id for product in url
            if (this.$route.name === 'product-by-name' && this.$route.params.name) {
                const encodedName = encodeURIComponent(this.$route.params.name);
                console.log('Attempting to fetch product by name:', `/product/name/${encodedName}`);
                response = await axiosClient.get(`/product/name/${encodedName}`);
            } else if (this.$route.params.id) {
                response = await axiosClient.get(`/product/${this.$route.params.id}`);
            } else {
                throw new Error('No valid product identifier provided');
            }
            
            this.product = response.data;
            console.log('Product data:', response.data);

            // get line item info for price
            if (this.product.lineItem_id) {
                try {
                    const lineItemResponse = await axiosClient.get(`/lineitems/${this.product.lineItem_id}`);
                    console.log('Line item response:', lineItemResponse.data);
                    this.product.lineItem = lineItemResponse.data;  // This will contain the price
                } catch (err) {
                    console.error('Error fetching line item:', err);
                }
            }
            // category data if we have a category_id
            if (this.product.category_id) {
                const categoryResponse = await axiosClient.get(`/category/${this.product.category_id}`);
                this.product.category = categoryResponse.data;
            }

            // get the product ID either from the route or the loaded product
            const productId = this.$route.params.id || this.product.id;
            const reviewsResponse = await axiosClient.get(`/products/${productId}/reviews`);
            this.reviews = reviewsResponse.data.reviews;

            console.log('Fetched reviews:', this.reviews);

            // calculate average rating and review count
            if (this.reviews.length > 0) {
                const totalRating = this.reviews.reduce((sum, review) => sum + review.rating, 0);
                this.averageRating = (totalRating / this.reviews.length).toFixed(1); // One decimal place
                this.reviewCount = this.reviews.length;
            } else {
                this.averageRating = 0;
                this.reviewCount = 0;
            }
            console.log('Reviews data:', this.reviews);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
        // retrieve the user's email from localStorage, set on signin view
        this.userEmail = localStorage.getItem('userEmail') || '';
        this.userName = localStorage.getItem('userName') || '';
    },
    methods: {
        // format dates into usable string
        formatDate(dateString) {
            const date = new Date(dateString);
            if (isNaN(date)) {
                return '';
            }
            const options = { year: 'numeric', month: 'long', day: 'numeric' };
            return date.toLocaleDateString(undefined, options);
        },
        goToAddReview() {
            // navigations method to add review page
            this.$router.push({ name: 'ReviewView', params: { id: this.product.id } });
        },

        //method for adding to cart
        addToCart() {
            // Create cart item from product data
            const cartItem = {
                id: this.product.id,
                name: this.product.name,
                price: this.product.lineItem?.price || 0,
                quantity: 1,
                image: this.product.imageURL
            };
            
            // get existing cart from localStorage or create new array
            let cart = JSON.parse(localStorage.getItem('sessionCart') || '[]');
            
            // check if item already exists in cart
            const existingItemIndex = cart.findIndex(item => item.id === cartItem.id);
            
            if (existingItemIndex !== -1) {
                // if item exists, increment quantity
                cart[existingItemIndex].quantity += 1;
            } else {
                // if item doesn't exist, add it
                cart.push(cartItem);
            }
            
            // save back to localStorage
            localStorage.setItem('sessionCart', JSON.stringify(cart));
            
            // show confirmation to user
            const notification = this.$refs.notification;
            notification.classList.add('show');
            
            // hide notification after 2 seconds
            setTimeout(() => {
                notification.classList.remove('show');
            }, 2000);
        },

        addToWishlist() {
            // create wishlist item from product data, couldnt make from object, backend didnt accomadate
            const wishlistItem = {
                id: this.product.id,
                name: this.product.name,
                price: this.product.lineItem?.price || 0,
                image: this.product.imageURL
            };
            
            // get existing wishlist from localStorage or create new array
            let wishlist = JSON.parse(localStorage.getItem('userWishlist') || '[]');
            
            // check if item already exists in wishlist
            const existingItemIndex = wishlist.findIndex(item => item.id === wishlistItem.id);
            
            const notification = this.$refs.notification;
            if (existingItemIndex !== -1) {
                // Item already in wishlist - could show message
                const notification = this.$refs.notification;
                notification.textContent = 'Item already in wishlist!';
                notification.classList.add('show');
            } else {
                // if item doesn't exist, add it
                wishlist.push(wishlistItem);
                // save back to localStorage
                localStorage.setItem('userWishlist', JSON.stringify(wishlist));
                
                // show confirmation to user
                const notification = this.$refs.notification;
                notification.textContent = 'Added to wishlist successfully!';
                notification.classList.add('show');
            }
            
            // hide notification after 2 seconds
            setTimeout(() => {
                notification.classList.remove('show');
            }, 2000);
        },

        // method when user clciks sign out
        signOut() {
            localStorage.removeItem('userEmail');
            localStorage.removeItem('userRole');
            localStorage.removeItem('userName');
            localStorage.removeItem('sessionCart');   
            localStorage.removeItem('userWishlist'); 
            this.userEmail = '';
            this.userName = '';
            this.$router.push({ name: 'signin' });
        },
    },
};
</script>


<style scoped>
/* from homeview*/
#product-page {
    background-color: #000;
    color: #fff;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    width: 85vw;
    padding: 0px, auto;
    overflow-y: hidden;
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


.header-top .header-right {
    display: flex;
    align-items: center;
}

.user-email {
    margin-right: 16px;
    font-size: 16px;
    color: #fff;
}

.sign-in-button,
.sign-out-button {
    background-color: #1a73e8;
    color: white;
    padding: 8px 12px;
    margin-left: 8px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    text-decoration: none;
    font-size: 14px;
    line-height: normal;
    display: inline-flex;
    align-items: center;
    width: fit-content
}

.sign-in-button:hover,
.sign-out-button:hover {
    background-color: #155cb0;
}


hr {
    border: 0;
    border-top: 1px solid #333;
    margin: 0;
}

.header-center {
    display: flex;
    justify-content: left;
    align-items: center;
    gap: 8px;
    padding: 16px 32px;
}

.header-center input {
    width: 100%;
    max-width: 200px;
    padding: 8px 12px;
    border: 0.5px solid #555;
    border-radius: 16px;
    background-color: #333;
    color: white;
}

.header-center button {
    padding: 8px 16px;
    background-color: transparent;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: color 0.3s ease;
}

.header-center button:hover,
.header-center button:active {
    color: grey;
}

header button:hover {
    background-color: #000;
}

.product-content {
    padding: 16px 32px;
    flex: 1;
}

.product-container {
    display: flex;
    gap: 32px;
    margin: 0 auto;
    padding: 24px;
    background-color: #121212;
    border-radius: 8px;
}

.product-image-section {
    flex: 1;
}

.product-image {
    width: 100%;
    height: auto;
    border-radius: 8px;
}

.product-info-section {
    flex: 1;
    padding: 20px;
}

.product-title {
    font-size: 32px;
    text-align: left;
}

.product-category {
    margin-bottom: 24px;
}

.product-description {
    font-size: 16px;
    color: #ccc;
    margin-bottom: 24px;
}

.product-price {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 24px;
}

.product-actions {
    display: flex;
    gap: 16px;
}

.add-to-cart-btn {
    background-color: #1a73e8;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
}

.add-to-wishlist-btn {
    background-color: #1a73e8;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
}

.add-to-cart-btn:hover,
.add-to-wishlist-btn:hover {
    opacity: 0.9;
}

.review-section {
    margin-top: 48px;
}

.review-section h2 {
    font-size: 24px;
    margin-bottom: 24px;
    text-align: left;
}

.review {
    border-bottom: 1px solid #333;
    padding: 16px 0;
}

.review-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.review-header strong {
    font-size: 16px;
}

.review-date {
    color: #777;
    font-size: 14px;
}

.review-comment {
    margin-top: 8px;
    font-size: 16px;
    color: #ccc;
}

.review-rating {
    margin-top: 8px;
    font-size: 16px;
    color: #ccc;
}

.star {
    font-size: 20px;
    color: #ccc;
}

.star.filled {
    color: #FFD700;
}


.product-rating {
    display: flex;
    align-items: center;
    margin-bottom: 16px;
}

.product-rating .average-rating {
    margin-left: 8px;
    font-size: 18px;
}

.product-rating .review-count {
    margin-left: 8px;
    color: #999;
    font-size: 14px;
}

.add-review-btn {
    background-color: #1a73e8;
    color: white;
    padding: 6px 12px;
    border: none;
    border-radius: 4px;
    font-weight: bold;
    cursor: pointer;
    margin-bottom: 16px;
    text-align: left;
    width: fit-content;
}

.add-review-btn:hover {
    opacity: 0.9;
}

.notification {
    position: fixed;
    top: 20px;
    left: 50%;
    transform: translateX(-50%) translateY(-20px);
    background-color: #1a73e8;
    color: white;
    padding: 16px 24px;
    border-radius: 8px;
    z-index: 1000;
    opacity: 0;
    transition: opacity 0.3s, transform 0.3s;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.notification.show {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
}

.header-center {
    display: flex;
    justify-content: space-between; 
    align-items: center;
    padding: 16px 32px;
    width: 100%;
}

.browse-btn {
    background-color: #1a73e8;
    color: white;
    padding: 12px 24px;
    border: none;
    border-radius: 4px;
    font-size: large;
    font-weight: bold;
    cursor: pointer;
    margin-bottom: 16px;
    text-align: left;
    width: fit-content;
}

.browse-btn:hover {
    background-color: #155cb0;
}

.header-right-side {
    display: flex;
    gap: 24px; 
    align-items: center;
}

.icon-link {
    filter: invert(1);
}

.icon-link:hover {
    color: #1a73e8;
    transform: scale(1.1);
}

.icon-link svg {
    vertical-align: middle;
}
</style>