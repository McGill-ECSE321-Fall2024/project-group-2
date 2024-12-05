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
      // Navigate to the home page (search for us)
      this.$router.push('/search');
    }
  }
}
</script>