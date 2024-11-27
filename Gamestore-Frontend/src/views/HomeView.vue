<template>
  <div id="homepage">
    <!-- Header -->
    <header>
      <div class="header-top">
        <div class="header-left">
          <img src="@/assets/logo.png" alt="Logo" class="logo" />
          <h1>Game Store</h1>
        </div>
        <div class="header-right">
          <router-link to="/signin" class="sign-in-button">Sign In</router-link>
          <button class="download-btn">Download</button>
        </div>
      </div>
      <hr />
      <div class="header-center">
        <input type="text" placeholder="Search store" />
        <button>Browse</button>
      </div>
    </header>

    <!-- Main Content -->
    <main>
      <!-- Top Sellers Section -->
      <section class="top-sellers">
        <h2>Top Sellers ></h2>
        <div class="card-container">
          <div class="game-card" v-for="game in topSellers" :key="game.id">
            <img :src="game.image" :alt="game.name" class="game-image" />
            <h3>{{ game.name }}</h3>
            <p>{{ game.price }}</p>
          </div>
        </div>
      </section>

      <!-- Trending and Top New Releases Section -->
      <section class="horizontal-section">
        <!-- Top New Releases -->
        <div class="vertical-section">
          <h2>Top New Releases ></h2>
          <div class="vertical-card-container">
            <div class="game-card" v-for="game in topSellers" :key="game.id">
              <img :src="game.image" :alt="game.name" class="game-image" />
              <h3>{{ game.name }}</h3>
              <p>{{ game.price }}</p>
            </div>
          </div>
        </div>

        <div class="vertical-divider"></div>

        <!-- Trending -->
        <div class="vertical-section">
          <h2>Trending</h2>
          <div class="vertical-card-container">
            <div class="game-card" v-for="game in mostPlayed" :key="game.id">
              <img :src="game.image" :alt="game.name" class="game-image" />
              <h3>{{ game.name }}</h3>
              <p>{{ game.price }}</p>
            </div>
          </div>
        </div>

        <div class="vertical-divider"></div>

        <!-- Trending -->
                <div class="vertical-section">
                  <h2>Trending</h2>
                  <div class="vertical-card-container">
                    <div class="game-card" v-for="game in mostPlayed" :key="game.id">
                      <img :src="game.image" :alt="game.name" class="game-image" />
                      <h3>{{ game.name }}</h3>
                      <p>{{ game.price }}</p>
                    </div>
                  </div>
                </div>
      </section>

      <!-- Top Upcoming Wishlisted Section -->
      <section class="top-upcoming">
        <h2>Top Upcoming Wishlisted</h2>
        <div class="card-container">
          <div class="game-card" v-for="game in topUpcoming" :key="game.id">
            <img :src="game.image" :alt="game.name" class="game-image" />
            <h3>{{ game.name }}</h3>
            <p>{{ game.price }}</p>
          </div>
        </div>
      </section>

    <!-- Categories Section -->
          <section class="categories">
            <h2>Categories</h2>
            <div class="card-container">
              <div class="game-card" v-for="category in categories" :key="category.id">
              <img :src="category.image" :alt="category.name" class="game-image" />
                <h3>{{ category.name }}</h3>
              </div>
            </div>
          </section>
        </main>

    <!-- Footer -->
    <footer>
      <p>© 2024 Game Store. All Rights Reserved.</p>
      <ul>
        <li><a href="#">Terms of Service</a></li>
        <li><a href="#">Privacy Policy</a></li>
        <li><a href="#">Contact Us</a></li>
      </ul>
    </footer>
  </div>
</template>

<script>
import picture1 from '@/assets/picture1.jpg';
import picture2 from '@/assets/picture2.jpg';
import picture3 from '@/assets/picture3.jpg';
import picture4 from '@/assets/picture4.jpg';
import picture5 from '@/assets/picture5.jpg';
import picture6 from '@/assets/picture6.jpg';
import picture7 from '@/assets/picture7.jpg';
import picture8 from '@/assets/picture8.jpg';
import picture9 from '@/assets/picture9.jpg';
import picture10 from '@/assets/picture10.png';
import picture12 from '@/assets/picture12.png';
import picture13 from '@/assets/picture13.png';
import picture14 from '@/assets/picture14.png';
import picture15 from '@/assets/picture15.png';
import logo from '@/assets/logo.png';


export default {
  name: "HomeView",
  data() {
    return {
      topSellers: [
        { id: 1, name: "EA SPORTS FC™ 25", price: "$89.99", image: picture1 },
        { id: 2, name: "Alan Wake 2", price: "$66.99", image: picture2 },
        { id: 3, name: "Cyberpunk 2077", price: "$79.99", image: picture3 },
      ],
      mostPlayed: [
        { id: 1, name: "Fortnite", price: "Free", image: picture4 },
        { id: 2, name: "Rocket League", price: "Free", image: picture5 },
        { id: 3, name: "Grand Theft Auto V", price: "$29.99", image: picture6 },
      ],
      topUpcoming: [
        { id: 1, name: "Marvel Rivals", price: "Available 12/05/24", image: picture7 },
        { id: 2, name: "Assassin’s Creed Shadows", price: "Available 02/14/25", image: picture8 },
        { id: 3, name: "Path of Exile 2", price: "Available 12/06/24", image: picture9 },
      ],
      categories: [
        { id: 1, name: "Action", image: picture10 },
        { id: 2, name: "Action-Adventure", image: picture12 },
        { id: 3, name: "Adventure", image: picture13 },
        { id: 4, name: "Fighting", image: picture14 },
        { id: 5, name: "Horror", image: picture15 },
      ],
    };
  },
  methods: {
    goToSignIn() {
      this.$router.push('/signin'); // Navigate to the SignInView page
    },
  },
};

</script>

<style scoped>
/* Global Container */

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
  color: #fff; /* White text */
  min-height: 100vh; /* Cover full screen */
  display: flex;
  flex-direction: column;
  min-width: 210%; /* Ensure full width */
  margin-left: -5.7vh;
  overflow-y: hidden;

}

/* Header */
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

.header-top .header-left h1 {
  font-size: 24px;
  font-weight: bold;
}

.header-top .header-left {
  display: flex; /* Use flexbox to align logo and heading */
  align-items: center;
}

.header-top .header-right button {
  background-color: #333;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  margin-left: 8px;
}

.header-top .header-right .download-btn {
  background-color: #1a73e8;
}

.header-top .header-right button:hover {
  background-color: #555;
}

hr {
  border: 0;
  border-top: 1px solid #333;
  margin: 0;
}

.header-center {
  display: flex;
  justify-content: left;
  margin-left: 140px;
  align-item: left;
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
  background-color: #000;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

header button:hover {
  background-color: #000;
}

/* Main Content */
main {
  flex: 1;
  padding: 32px;
  width: 100%; /* Full width */
}

section {
  margin-bottom: 32px;
}

section h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
  text-align: left;
}

/* Card Container */
.card-container {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* 5 cards in a row */
  gap: 1px; /* Space between cards */
  justify-content: center; /* Center the grid */
}

.game-card {
  background-color: #000; /* Darker card background */
  color: white;
  font-size: 12px;
  border-radius: 8px;
  padding: 3px;
  text-align: center;
  max-width: 200px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
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
  gap: 16px;
  width: 100px;
  align-items: center;
  margin-left: 130px;
}

.horizontal-section {
  display: flex;
  gap: 16px;
  justify-content: space-between;
  margin-bottom: 32px;
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

</style>








