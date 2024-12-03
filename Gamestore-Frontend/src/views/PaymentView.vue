<template>
  <div class="payment-page">
    <div class="payment-container">
      <!-- Left Column - Payment Form -->
      <div class="payment-main">
        <h1 class="payment-title">Payment</h1>

        <div class="form-section">
          <h2>Payment Method</h2>
          <form @submit.prevent="handlePayment" class="payment-form">
            <!-- Payment Method Selection -->
            <div class="payment-methods">
              <label class="payment-method">
                <input
                    type="radio"
                    v-model="paymentMethod"
                    value="credit-card"
                    name="paymentMethod"
                    checked
                />
                <span class="method-details">
                  <span class="method-name">Credit Card</span>
                  <div class="card-icons">
                    <img src="@/assets/visaimage.png" alt="Visa" />
                    <img src="@/assets/Mastercard.png" alt="Mastercard" />
                    <img src="@/assets/AmexPhoto.png" alt="Amex" />
                  </div>
                </span>
              </label>
            </div>

            <!-- Credit Card Details -->
            <div v-if="paymentMethod === 'credit-card'" class="card-details">
              <div class="form-group">
                <label for="cardName">Name on Card *</label>
                <input
                    type="text"
                    id="cardName"
                    v-model="cardDetails.name"
                    required
                    placeholder="Enter name as shown on card"
                />
              </div>

              <div class="form-group">
                <label for="cardNumber">Card Number *</label>
                <input
                    type="text"
                    id="cardNumber"
                    v-model="cardDetails.number"
                    required
                    placeholder="1234 5678 9012 3456"
                    maxlength="19"
                    @input="formatCardNumber"
                />
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label for="expiryDate">Expiry Date *</label>
                  <input
                      type="text"
                      id="expiryDate"
                      v-model="cardDetails.expiry"
                      required
                      placeholder="MM/YY"
                      maxlength="5"
                      @input="formatExpiry"
                  />
                </div>
                <div class="form-group">
                  <label for="cvv">CVV *</label>
                  <input
                      type="text"
                      id="cvv"
                      v-model="cardDetails.cvv"
                      required
                      placeholder="123"
                      maxlength="4"
                      @input="formatCVV"
                  />
                </div>
              </div>
            </div>

            <!-- Billing Address -->
            <div class="billing-address">
              <h3>Billing Address</h3>
              <label class="same-as-shipping">
                <input
                    type="checkbox"
                    v-model="sameAsShipping"
                />
                Same as shipping address
              </label>

              <div v-if="!sameAsShipping" class="billing-form">
                <div class="form-row">
                  <div class="form-group">
                    <label for="billingFirstName">First Name *</label>
                    <input
                        type="text"
                        id="billingFirstName"
                        v-model="billingInfo.firstName"
                        required
                        placeholder="Enter your First Name"
                    />
                  </div>
                  <div class="form-group">
                    <label for="billingLastName">Last Name *</label>
                    <input
                        type="text"
                        id="billingLastName"
                        v-model="billingInfo.lastName"
                        required
                        placeholder="Enter your Last Name"
                    />
                  </div>
                </div>

                <div class="form-group">
                  <label for="billingAddress">Address Line 1 *</label>
                  <input
                      type="text"
                      id="billingAddress"
                      v-model="billingInfo.address"
                      required
                      placeholder="Enter your Address"
                  />
                </div>

                <div class="form-row">
                  <div class="form-group">
                    <label for="billingCity">City *</label>
                    <input
                        type="text"
                        id="billingCity"
                        v-model="billingInfo.city"
                        required
                        placeholder="Enter your City"
                    />
                  </div>
                  <div class="form-group">
                    <label for="billingPostalCode">Postal Code *</label>
                    <input
                        type="text"
                        id="billingPostalCode"
                        v-model="billingInfo.postalCode"
                        required
                        placeholder="Enter your Postal Code"
                    />
                  </div>
                </div>

                <div class="form-group">
                  <label for="billingProvince">Province *</label>
                  <select id="billingProvince" v-model="billingInfo.province" required>
                    <option value="">Select Province</option>
                    <option value="ON">Ontario</option>
                    <option value="BC">British Columbia</option>
                    <option value="QC">Quebec</option>
                  </select>
                </div>
              </div>
            </div>

            <button
                type="submit"
                class="submit-payment-button"
                :disabled="!isFormValid"
            >
              Submit Payment
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
              <span>${{ shippingCost.toFixed(2) }}</span>
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
  name: 'PaymentView',
  data() {
    return {
      paymentMethod: 'credit-card',
      cardDetails: {
        name: '',
        number: '',
        expiry: '',
        cvv: ''
      },
      sameAsShipping: true,
      billingInfo: {
        firstName: '',
        lastName: '',
        address: '',
        city: '',
        postalCode: '',
        province: ''
      },
      cartItems: [
        { id: 1, name: "Cyberpunk 2077", price: 79.99, quantity: 1, image: picture3 },
        { id: 2, name: "EA Sports FC 24", price: 89.99, quantity: 1, image: picture1 }
      ],
      shippingCost: 15.00
    };
  },
  computed: {
    subtotal() {
      return this.cartItems.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    },
    tax() {
      return this.subtotal * 0.13; // 13% tax
    },
    total() {
      return this.subtotal + this.tax + this.shippingCost;
    },
    isFormValid() {
      const { name, number, expiry, cvv } = this.cardDetails;
      return name && number && expiry && cvv &&
          (this.sameAsShipping || this.isBillingAddressValid);
    },
    isBillingAddressValid() {
      const { firstName, lastName, address, city, postalCode, province } = this.billingInfo;
      return firstName && lastName && address && city && postalCode && province;
    }
  },
  methods: {
    formatCardNumber(e) {
      const value = e.target.value.replace(/\s/g, '').replace(/\D/g, '');
      this.cardDetails.number = value.match(/.{1,4}/g)?.join(' ') || '';
    },
    formatExpiry(e) {
      const value = e.target.value.replace(/\D/g, '');
      this.cardDetails.expiry = value.length > 2 ? value.slice(0, 2) + '/' + value.slice(2) : value;
    },
    formatCVV(e) {
      this.cardDetails.cvv = e.target.value.replace(/\D/g, '');
    },
    handlePayment() {
      if (this.isFormValid) {
        // Redirect directly to the order complete page
        this.$router.push('/ordercomplete');
      }
    }
  }
};
</script>




<style scoped>
.payment-page {
  background-color: #000;
  min-height: 100vh;
  padding: 2rem;
  color: white;
}

.payment-container {
  width: 1000px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 2rem;
}

.payment-title {
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

.payment-methods {
  margin-bottom: 2rem;
}

.payment-method {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem;
  border: 1px solid #333;
  border-radius: 4px;
  cursor: pointer;
}

.card-icons {
  display: flex;
  gap: 0.5rem;
}

.card-icons img {
  height: 30px;
}

.card-details {
  margin-top: 1.5rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #ccc;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 0.75rem;
  border-radius: 4px;
  border: 1px solid #333;
  background-color: #1a1a1a;
  color: white;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.same-as-shipping {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin: 1rem 0;
}

.billing-form {
  margin-top: 1.5rem;
}

.submit-payment-button {
  background-color: #007bff;
  color: white;
  width: 100%;
  padding: 1rem;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 1.5rem;
}

.submit-payment-button:disabled {
  background-color: #333;
  cursor: not-allowed;
}

/* Order Summary Styles (same as checkout page) */
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