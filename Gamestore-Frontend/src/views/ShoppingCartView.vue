<template>
  <div class="shopping-cart-page">
    <div class="shopping-cart-container">
      <h1 class="cart-title">Your Shopping Cart</h1>

      <div v-if="cartItems.length > 0" class="cart-content">
        <div class="cart-items">
          <div v-for="item in cartItems" :key="item.id" class="cart-item">
            <img :src="item.image ? `data:image/jpeg;base64,${item.image}` : ''" :alt="item.name" class="item-image" />
            <div class="item-details">
              <h2>{{ item.name }}</h2>
              <p class="item-price">${{ item.price.toFixed(2) }}</p>
            </div>
            <div class="item-quantity">
              <button @click="decreaseQuantity(item)" class="quantity-btn" :disabled="item.quantity <= 1">-</button>
              <span class="quantity-display">{{ item.quantity }}</span>
              <button @click="increaseQuantity(item)" class="quantity-btn">+</button>
            </div>
            <div class="item-total">${{ (item.price * item.quantity).toFixed(2) }}</div>
            <button @click="removeItem(item)" class="remove-btn">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="3 6 5 6 21 6"></polyline>
                <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                <line x1="10" y1="11" x2="10" y2="17"></line>
                <line x1="14" y1="11" x2="14" y2="17"></line>
              </svg>
            </button>
          </div>
        </div>

        <div class="order-summary">
          <h2>Order Summary</h2>
          <div class="summary-row">
            <span>Subtotal</span>
            <span>${{ subtotal.toFixed(2) }}</span>
          </div>
          <div class="summary-row">
            <span>Estimated Tax</span>
            <span>${{ tax.toFixed(2) }}</span>
          </div>
          <div class="summary-row">
            <span>Estimated Shipping</span>
            <span>${{ shipping.toFixed(2) }}</span>
          </div>
          <div class="summary-row total">
            <span>Estimated Total</span>
            <span>${{ total.toFixed(2) }}</span>
          </div>
          <button @click="proceedToCheckout" class="checkout-btn">Proceed to Checkout</button>
        </div>
      </div>

      <div v-else class="empty-cart">
        <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none"
          stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="9" cy="21" r="1"></circle>
          <circle cx="20" cy="21" r="1"></circle>
          <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
        </svg>
        <p>Your cart is empty</p>
        <button @click="continueShopping" class="continue-shopping-btn">Continue Shopping</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ShoppingCartView',
  data() {
    return {
      cartItems: [],
      shipping: 15.00
    }
  },
  created() {
    // load cart items when component is created
    this.loadCartItems();
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0)
    },
    tax() {
      return this.subtotal * 0.13 // 13% tax
    },
    total() {
      return this.subtotal + this.tax + this.shipping
    }
  },
  methods: {
    loadCartItems() {
      this.cartItems = JSON.parse(localStorage.getItem('sessionCart') || '[]');
    },
    increaseQuantity(item) {
      item.quantity++;
      this.updateLocalStorage();
    },
    decreaseQuantity(item) {
      if (item.quantity > 1) {
        item.quantity--;
        this.updateLocalStorage();
      }
    },
    removeItem(item) {
      const index = this.cartItems.findIndex(cartItem => cartItem.id === item.id);
      if (index !== -1) {
        this.cartItems.splice(index, 1);
        this.updateLocalStorage();
      }
    },
    updateLocalStorage() {
      localStorage.setItem('sessionCart', JSON.stringify(this.cartItems));
    },
    proceedToCheckout() {
      // Navigate to checkout page
      this.$router.push('/checkout')
    },
    continueShopping() {
      // Navigate to home page or game catalog
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.shopping-cart-page {
  background-color: #000;
  min-height: 100vh;
  padding: 2rem;
  color: white;
}

.shopping-cart-container {
  max-width: 1200px;
  margin: 0 auto;
}

.cart-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 2rem;
  text-align: center;
}

.cart-content {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 2rem;
}

.cart-items {
  background-color: #121212;
  border-radius: 8px;
  padding: 1.5rem;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 1px solid #333;
}

.cart-item:last-child {
  border-bottom: none;
}

.item-image {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 1rem;
}

.item-details {
  flex: 1;
}

.item-details h2 {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.item-price {
  font-size: 1.1rem;
  color: #007bff;
}

.item-quantity {
  display: flex;
  align-items: center;
  margin: 0 1rem;
}

.quantity-btn {
  background-color: #333;
  color: white;
  border: none;
  width: 30px;
  height: 30px;
  font-size: 1.2rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
}

.quantity-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.quantity-display {
  margin: 0 0.5rem;
  font-size: 1.1rem;
}

.item-total {
  font-size: 1.1rem;
  font-weight: bold;
  margin: 0 1rem;
}

.remove-btn {
  background: none;
  border: none;
  color: #ff4d4d;
  cursor: pointer;
}

.order-summary {
  background-color: #121212;
  border-radius: 8px;
  padding: 1.5rem;
  position: sticky;
  top: 2rem;
  height: fit-content;
}

.order-summary h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.summary-row.total {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 1px solid #333;
  font-weight: bold;
  font-size: 1.2rem;
}

.checkout-btn {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 1rem;
  width: 100%;
  font-size: 1.1rem;
  font-weight: bold;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 1rem;
  transition: background-color 0.3s ease;
}

.checkout-btn:hover {
  background-color: #0056b3;
}

.empty-cart {
  text-align: center;
  padding: 4rem 0;
}

.empty-cart svg {
  margin-bottom: 1rem;
}

.empty-cart p {
  font-size: 1.2rem;
  margin-bottom: 2rem;
}

.continue-shopping-btn {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 1rem 2rem;
  font-size: 1.1rem;
  font-weight: bold;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.continue-shopping-btn:hover {
  background-color: #0056b3;
}

@media (max-width: 768px) {
  .cart-content {
    grid-template-columns: 1fr;
  }

  .cart-item {
    flex-wrap: wrap;
  }

  .item-quantity,
  .item-total {
    margin-top: 1rem;
  }

  .remove-btn {
    margin-left: auto;
  }
}
</style>