<template>
  <div class="order-complete-page">
    <div class="order-complete-container">
      <div class="order-complete-content">
        <h1 class="thank-you-message">Thank You for Your Order!</h1>

        <div class="order-status">
          <div class="status-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
              <polyline points="22 4 12 14.01 9 11.01"></polyline>
            </svg>
          </div>
          <p class="status-message">Your order is complete and is being processed.</p>
        </div>

        <div class="order-info">
          <h2>Order Information</h2>
          <div class="info-grid">
            <div class="info-item">
              <span class="info-label">Order Number:</span>
              <span class="info-value">{{ orderNumber }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">Order Date:</span>
              <span class="info-value">{{ orderDate }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">Total Amount:</span>
              <span class="info-value">${{ orderTotal.toFixed(2) }}</span>
            </div>
          </div>
        </div>

        <div class="order-summary">
          <h2>Order Summary</h2>
          <div class="summary-items">
            <div v-for="item in orderItems" :key="item.id" class="summary-item">
              <img :src="item.image" :alt="item.name" class="item-image" />
              <div class="item-details">
                <h3>{{ item.name }}</h3>
                <p>Quantity: {{ item.quantity }}</p>
                <p class="item-price">${{ (item.price * item.quantity).toFixed(2) }}</p>
              </div>
            </div>
          </div>
        </div>

        <div class="email-notification">
          <p>A confirmation email will be sent to you shortly.</p>
        </div>

        <button @click="returnToHome" class="return-home-button">
          Return to Home Page
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import picture1 from '@/assets/picture1.jpg';
import picture3 from '@/assets/picture3.jpg';
export default {
  name: 'OrderCompleteView',
  data() {
    return {
      orderNumber: 'ORD-12345-ABCDE',
      orderDate: new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' }),
      orderTotal: 184.98,
      userEmail: 'customer@example.com',
      orderItems: [
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
  methods: {
    returnToHome() {
      // Navigate to the home page
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.order-complete-page {
  background-color: #000;
  min-height: 100vh;
  padding: 2rem;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
}

.order-complete-container {
  max-width: 800px;
  width: 100%;
  background-color: #121212;
  border-radius: 8px;
  padding: 2rem;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.order-complete-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.thank-you-message {
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
  color: #007bff;
}

.order-status {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}

.status-icon {
  color: #4CAF50;
  margin-bottom: 1rem;
}

.status-message {
  font-size: 1.2rem;
  color: #4CAF50;
}

.order-info, .order-summary {
  width: 100%;
  margin-bottom: 2rem;
}

.order-info h2, .order-summary h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  text-align: left;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  text-align: left;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 0.9rem;
  color: #888;
}

.info-value {
  font-size: 1.1rem;
  font-weight: bold;
}

.summary-items {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.summary-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  text-align: left;
  padding: 1rem;
  background-color: #1a1a1a;
  border-radius: 8px;
}

.item-image {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  object-fit: cover;
}

.item-details {
  flex: 1;
}

.item-details h3 {
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
}

.item-price {
  font-weight: bold;
  color: #007bff;
}

.email-notification {
  margin-bottom: 2rem;
  font-size: 1.1rem;
}

.return-home-button {
  background-color: #007bff;
  color: white;
  padding: 1rem 2rem;
  border: none;
  border-radius: 4px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.return-home-button:hover {
  background-color: #0056b3;
}

@media (max-width: 600px) {
  .order-complete-container {
    padding: 1rem;
  }

  .thank-you-message {
    font-size: 2rem;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }

  .summary-item {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>