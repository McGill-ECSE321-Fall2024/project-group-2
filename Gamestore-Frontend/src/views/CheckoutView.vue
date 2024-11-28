<template>
  <div class="checkout-page">
    <div class="checkout-container">
      <!-- Left Column - Shipping and Payment Forms -->
      <div class="checkout-main">
        <h1 class="checkout-title">Checkout</h1>

        <!-- Shipping Information -->
        <div class="form-section">
          <h2>Shipping Information</h2>
          <form @submit.prevent="handleShipping" class="checkout-form">
            <div class="form-row">
              <div class="form-group">
                <label for="firstName">First Name *</label>
                <input
                    type="text"
                    id="firstName"
                    v-model="shippingInfo.firstName"
                    required
                    placeholder="Enter your First Name"
                />
              </div>
              <div class="form-group">
                <label for="lastName">Last Name *</label>
                <input
                    type="text"
                    id="lastName"
                    v-model="shippingInfo.lastName"
                    required
                    placeholder="Enter your Last Name"
                />
              </div>
            </div>

            <div class="form-group">
              <label for="address">Address Line 1 *</label>
              <input
                  type="text"
                  id="address"
                  v-model="shippingInfo.address"
                  required
                  placeholder="Enter your Address"
              />
            </div>

            <div class="form-group">
              <label for="address2">Address Line 2</label>
              <input
                  type="text"
                  id="address2"
                  v-model="shippingInfo.address2"
                  placeholder="Apartment, suite, etc. (optional)"
              />
            </div>

            <div class="form-row">
              <div class="form-group">
                <label for="city">City *</label>
                <input
                    type="text"
                    id="city"
                    v-model="shippingInfo.city"
                    required
                    placeholder="Enter your City"
                />
              </div>
              <div class="form-group">
                <label for="postalCode">Postal Code *</label>
                <input
                    type="text"
                    id="postalCode"
                    v-model="shippingInfo.postalCode"
                    required
                    placeholder="Enter your Postal Code"
                />
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label for="province">Province *</label>
                <select id="province" v-model="shippingInfo.province" required>
                  <option value="">Select Province</option>
                  <option value="ON">Ontario</option>
                  <option value="BC">British Columbia</option>
                  <option value="QC">Quebec</option>
                  <!-- Add other provinces -->
                </select>
              </div>
              <div class="form-group">
                <label for="phone">Phone Number *</label>
                <input
                    type="tel"
                    id="phone"
                    v-model="shippingInfo.phone"
                    required
                    placeholder="Enter your Phone Number"
                />
              </div>
            </div>

            <!-- Shipping Method Selection -->
            <div class="shipping-methods">
              <h3>Shipping Method</h3>
              <div class="method-options">
                <label class="method-option">
                  <input
                      type="radio"
                      v-model="shippingMethod"
                      value="standard"
                      name="shippingMethod"
                  />
                  <span class="method-details">
                    <span class="method-name">Standard Shipping</span>
                    <span class="method-price">Free</span>
                  </span>
                </label>
                <label class="method-option">
                  <input
                      type="radio"
                      v-model="shippingMethod"
                      value="express"
                      name="shippingMethod"
                  />
                  <span class="method-details">
                    <span class="method-name">Express Shipping</span>
                    <span class="method-price">$15.00</span>
                  </span>
                </label>
              </div>
            </div>

            <button
                type="submit"
                class="proceed-button"
                :disabled="!isFormValid"
            >
              Proceed to Payment
            </button>
          </form>
        </div>
      </div>

      <!-- Right Column - Order Summary -->
      <div class="order-summary">
        <div class="summary-card">
          <h2>Order Summary</h2>
          <div class="summary-items">
            <div v-for="item in cartItems" :key="item.id" class="summary-item">
              <img :src="item.image" :alt="item.name" class="item-image" />
              <div class="item-details">
                <h3>{{ item.name }}</h3>
                <p>Quantity: {{ item.quantity }}</p>
                <p class="item-price">${{ (item.price * item.quantity).toFixed(2) }}</p>
              </div>
            </div>
          </div>
          <div class="summary-totals">
            <div class="summary-row">
              <span>Subtotal</span>
              <span>${{ subtotal.toFixed(2) }}</span>
            </div>
            <div class="summary-row">
              <span>Shipping</span>
              <span>{{ shippingMethod === 'express' ? '$15.00' : 'Free' }}</span>
            </div>
            <div class="summary-row">
              <span>Tax</span>
              <span>${{ tax.toFixed(2) }}</span>
            </div>
            <div class="summary-row total">
              <span>Total</span>
              <span>${{ total.toFixed(2) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import picture1 from '@/assets/picture1.jpg';
import picture3 from '@/assets/picture3.jpg';

export default {
  name: 'CheckoutView',
  data() {
    return {
      shippingInfo: {
        firstName: '',
        lastName: '',
        address: '',
        address2: '',
        city: '',
        postalCode: '',
        province: '',
        phone: '',
      },
      shippingMethod: 'standard',
      cartItems: [
        {
          id: 1,
          name: "Cyberpunk 2077",
          price: 79.99,
          quantity: 1,
          image: picture3
        },
        {
          id: 2,
          name: "EA Sports FC 24",
          price: 89.99,
          quantity: 1,
          image: picture1
        }
      ]
    }
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0)
    },
    tax() {
      return this.subtotal * 0.13 // 13% tax
    },
    total() {
      const shippingCost = this.shippingMethod === 'express' ? 15 : 0
      return this.subtotal + this.tax + shippingCost
    },
    isFormValid() {
      const { firstName, lastName, address, city, postalCode, province, phone } = this.shippingInfo
      return firstName && lastName && address && city && postalCode && province && phone
    }
  },
  methods: {
    handleShipping() {
      if (this.isFormValid) {
        // Navigate to payment page
        this.$router.push('/payment')
      }
    }
  }
}
</script>

<style scoped>
.checkout-page {
  background-color: #000;
  min-height: 100vh;
  padding: 2rem;
  color: white;
}

.checkout-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 2rem;
}

.checkout-title {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.form-section {
  background-color: #121212;
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
}

.form-section h2 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
}

.checkout-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-size: 0.9rem;
  color: #ccc;
}

.form-group input,
.form-group select {
  padding: 0.75rem;
  border-radius: 4px;
  border: 1px solid #333;
  background-color: #1a1a1a;
  color: white;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #666;
}

.shipping-methods {
  margin-top: 1.5rem;
}

.method-options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 1rem;
}

.method-option {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  border: 1px solid #333;
  border-radius: 4px;
  cursor: pointer;
}

.method-details {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.proceed-button {
  background-color: #007bff;
  color: white;
  padding: 1rem;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 1.5rem;
}

.proceed-button:disabled {
  background-color: #333;
  cursor: not-allowed;
}

.order-summary {
  position: sticky;
  top: 2rem;
}

.summary-card {
  background-color: #121212;
  border-radius: 8px;
  padding: 1.5rem;
}

.summary-items {
  margin: 1.5rem 0;
}

.summary-item {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #333;
}

.item-image {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  object-fit: cover;
}

.item-details h3 {
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.item-price {
  font-weight: bold;
  color: #007bff;
}

.summary-totals {
  margin-top: 1.5rem;
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
</style>