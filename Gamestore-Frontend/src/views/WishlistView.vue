<template>
    <div id="wishlist-page">
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
                    <button class="browse-btn" @click="$router.push('/search')">Browse</button>
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

        <div class="notification" ref="notification"></div>

        <main class="wishlist-content">
            <h1 class="text-2xl font-bold mb-6">My Wishlist</h1>

            <div v-if="!wishlistItems.length" class="empty-wishlist">
                <p>Your wishlist is empty</p>
            </div>

            <div v-else class="wishlist-items">
                <div v-for="item in wishlistItems" :key="item.id" class="wishlist-item">
                    <div class="item-details">
                        <router-link :to="{ name: 'product-by-name', params: { name: item.name } }" class="game-name">
                            {{ item.name }}
                        </router-link>
                        <span class="price">${{ item.price.toFixed(2) }}</span>
                    </div>
                    <div class="item-actions">
                        <button @click="removeFromWishlist(item.id)" class="remove-btn">
                            Remove
                        </button>
                        <button @click="addToCart(item)" class="add-to-cart-btn">
                            Add to Cart
                        </button>
                    </div>
                </div>
            </div>
        </main>
    </div>
</template>

<script>
import cartIcon from '../assets/shopping-cart.svg'
import wishlistIcon from '../assets/wishlist-icon.svg'

export default {
    name: 'WishlistView',
    data() {
        return {
            cartIcon,
            wishlistIcon,
            wishlistItems: [],
            userEmail: '',
            userName: ''
        };
    },
    created() {
        // Load wishlist from localStorage
        this.wishlistItems = JSON.parse(localStorage.getItem('userWishlist') || '[]');

        // Get user info from localStorage
        this.userEmail = localStorage.getItem('userEmail') || '';
        this.userName = localStorage.getItem('userName') || '';
    },
    methods: {
        removeFromWishlist(itemId) {
            this.wishlistItems = this.wishlistItems.filter(item => item.id !== itemId);
            localStorage.setItem('userWishlist', JSON.stringify(this.wishlistItems));

            // Show notification
            const notification = this.$refs.notification;
            if (notification) {
                notification.textContent = 'Removed from wishlist';
                notification.classList.add('show');
                setTimeout(() => {
                    notification.classList.remove('show');
                }, 2000);
            }
        },
        addToCart(item) {
            // Get existing cart
            let cart = JSON.parse(localStorage.getItem('sessionCart') || '[]');

            // Check if already in cart
            const existingItemIndex = cart.findIndex(cartItem => cartItem.id === item.id);

            if (existingItemIndex !== -1) {
                // If item exists, increment quantity
                cart[existingItemIndex].quantity += 1;
            } else {
                // If item doesn't exist, add it with quantity 1
                cart.push({ ...item, quantity: 1 });
            }

            // Save back to localStorage
            localStorage.setItem('sessionCart', JSON.stringify(cart));

            // Show notification
            const notification = this.$refs.notification;
            if (notification) {
                notification.textContent = 'Added to cart!';
                notification.classList.add('show');
                setTimeout(() => {
                    notification.classList.remove('show');
                }, 2000);
            }
        },
        // clean up local storage for next login
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
#wishlist-page {
    background-color: #000;
    color: #fff;
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    width: 85vw;
    padding: 0px, auto;
    max-width: 1300px;
    margin: 0 auto;
}

.wishlist-content {
    padding: 32px;
}

.empty-wishlist {
    text-align: center;
    padding: 32px;
    background-color: #121212;
    border-radius: 8px;
    margin-top: 20px;
}

.wishlist-items {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.wishlist-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background-color: #121212;
    border-radius: 8px;
    transition: transform 0.2s;
}

.wishlist-item:hover {
    transform: translateY(-2px);
}

.item-details {
    display: flex;
    align-items: center;
    gap: 20px;
}

.game-name {
    color: #fff;
    text-decoration: none;
    font-size: 18px;
    font-weight: bold;
    transition: color 0.2s;
}

.game-name:hover {
    color: #1a73e8;
}

.price {
    color: #ccc;
    font-size: 16px;
}

.item-actions {
    display: flex;
    gap: 12px;
}

.remove-btn {
    background-color: #dc3545;
    color: white;
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.2s;
}

.remove-btn:hover {
    background-color: #c82333;
}

.add-to-cart-btn {
    background-color: #1a73e8;
    color: white;
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
    transition: background-color 0.2s;
}

.add-to-cart-btn:hover {
    background-color: #155cb0;
}

.header-top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px 32px;
}

.header-left {
    display: flex;
    align-items: center;
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
    width: fit-content;
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
    justify-content: space-between;
    align-items: center;
    padding: 16px 32px;
    width: 100%;
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


.header-right-side {
    display: flex;
    gap: 24px;
    align-items: center;
}

.icon-link {
    filter: invert(1);
}

.icon-link:hover {
    transform: scale(1.1);
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
</style>