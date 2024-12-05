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
        </div>
      </div>
      <hr />
    </header>

    <!-- Main Banner Section -->
    <section id="featured-section">
      <div class="featured-content">
        <!-- Main Banner -->
        <div class="featured-banner">
          <img :src="featuredItems[selectedIndex].image" alt="Featured" class="featured-image" />
          <div class="banner-details">
            <h2>{{ featuredItems[selectedIndex].title }}</h2>
            <p>{{ featuredItems[selectedIndex].description }}</p>
            <button class="save-now-btn">Buy Now</button>
          </div>
        </div>

        <!-- Side List -->
        <div class="featured-side-list">
          <div
            v-for="(item, index) in featuredItems"
            :key="item.id"
            class="side-list-item"
            :class="{ active: index === selectedIndex }"
            @click="selectFeatured(index)"
          >
            <img :src="item.thumbnail" alt="Thumbnail" class="side-thumbnail" />
            <span class="side-text">{{ item.title }}</span>
          </div>
        </div>
      </div>
    </section>

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

      <!-- Free Games Section -->
      <section class="free-games">
        <div class="free-games-header">
          <h2>🎁 Free Games</h2>
        </div>
        <div class="free-games-container">
          <div class="free-game-card" v-for="game in freeGames" :key="game.id">
            <img :src="game.image" alt="Free Game" class="free-game-image" />
            <div class="free-game-details">
              <h3>{{ game.name }}</h3>
              <button :class="game.status === 'free' ? 'free-now-btn' : 'coming-soon-btn'">
                {{ game.status === 'free' ? 'FREE NOW' : 'COMING SOON' }}
              </button>
              <p>{{ game.description }}</p>
            </div>
          </div>
        </div>
      </section>

      <!-- Trending and Top New Releases Section -->
      <section class="horizontal-section">
        <!-- New Releases -->
        <div class="vertical-section">
          <h2>New Releases ></h2>
          <div class="list-card-container">
            <div class="list-card" v-for="game in newSellers" :key="game.id">
              <img :src="game.image" :alt="game.name" class="list-game-image" />
              <div class="list-card-content">
                <h3 class="game-name">{{ game.name }}</h3>
                <p class="game-price">{{ game.price }}</p>
              </div>
            </div>
          </div>
        </div>

        <div class="vertical-divider"></div>

        <!-- Top Upcoming Wishlisted -->
        <div class="vertical-section">
          <h2>Top Upcoming Wishlisted ></h2>
          <div class="list-card-container">
            <div class="list-card" v-for="game in topUpcoming" :key="game.id">
              <img :src="game.image" :alt="game.name" class="list-game-image" />
              <div class="list-card-content">
                <h3 class="game-name">{{ game.name }}</h3>
                <p class="game-price">{{ game.price }}</p>
              </div>
            </div>
          </div>
        </div>

        <div class="vertical-divider"></div>

        <!-- Most Played -->
        <div class="vertical-section">
          <h2>Most Played ></h2>
          <div class="list-card-container">
            <div class="list-card" v-for="game in mostPlayed" :key="game.id">
              <img :src="game.image" :alt="game.name" class="list-game-image" />
              <div class="list-card-content">
                <h3 class="game-name">{{ game.name }}</h3>
                <p class="game-price">{{ game.price }}</p>
              </div>
            </div>
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
import picture16 from '@/assets/picture16.jpg';
import picture17 from '@/assets/picture17.jpg';
import picture18 from '@/assets/picture18.jpg';
import picture19 from '@/assets/picture19.jpg';
import picture20 from '@/assets/picture20.jpg';
import picture23 from '@/assets/picture23.jpg';
import picture24 from '@/assets/picture24.jpg';
import picture25 from '@/assets/picture25.jpg';
import picture26 from '@/assets/picture26.jpg';
import picture27 from '@/assets/picture27.jpg';
import picture28 from '@/assets/picture28.jpg';
import picture29 from '@/assets/picture29.jpg';
import logo from '@/assets/logo.png';


export default {
  name: "HomeView",
  data() {
    return {
    selectedIndex: 0,
          featuredItems: [
            {
              id: 1,
              title: "S.T.A.L.K.E.R. 2: Heart of Chornobyl",
              description: "Discover the vast Chornobyl Exclusion Zone full of dangerous enemies, deadly anomalies, and powerful artifacts.",
              image: picture23,
              thumbnail: picture23,
            },
            {
              id: 2,
              title: "Black Friday - Cyber Monday Sale",
              description: "Save on premium edition video games. Offer ends Dec 3.",
              image: picture26,
              thumbnail: picture26,
            },
            {
              id: 3,
              title: "Star Wars Outlaws: Wild Card",
              description: "Pre-order now to secure exclusive rewards.",
              image: picture25,
              thumbnail: picture25,
            },
            {
              id: 4,
              title: "Discord Nitro Giveaway",
              description: "Claim your free Discord Nitro for this month.",
              image: picture24,
              thumbnail: picture24,
            },
            {
              id: 5,
              title: "Free Fortnite Outfit",
              description: "Claim your free Discord Nitro for this month.",
              image: picture27,
              thumbnail: picture27,
              },
      ],
      freeGames: [
              {
                id: 1,
                name: "Beholder",
                image: picture28,
                status: "free",
                description: "Free Now - Nov 28 at 11:00 AM",
              },
              {
                id: 2,
                name: "Brotato",
                image: picture29,
                status: "coming-soon",
                description: "Free Nov 28 - Dec 05",
              },
      ],
      topSellers: [
        { id: 1, name: "EA SPORTS FC™ 25", price: "$89.99", image: picture1 },
        { id: 2, name: "Alan Wake 2", price: "$66.99", image: picture2 },
        { id: 3, name: "Cyberpunk 2077", price: "$79.99", image: picture3 },
        { id: 4, name: "Hogwarts Legacy", price: "$79.99", image: picture19 },
        { id: 5, name: "Farming Simulator 25", price: "$64.99", image: picture20 },
      ],
      newSellers: [
              { id: 1, name: "Torque Drift 2", price: "$17.24", image: picture16 },
              { id: 2, name: "Phoenix Contract", price: "Free", image: picture17 },
              { id: 3, name: "Karate Survivor", price: "$6.49", image: picture18 },
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

.logo {
  height: 50px;
  width: 50px;
  margin-right: 10px; 
  display: inline-block;
  vertical-align: middle; 
  border-radius: 28px;
  margin-left: 10px;
}

#homepage {
  background-color: #000; 
  color: #fff; 
  min-height: 100vh; 
  display: flex;
  flex-direction: column;
  min-width: 210%; 
  margin-left: -5.7vh;
  overflow-y: hidden;

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

.header-top .header-left h1 {
  font-size: 24px;
  font-weight: bold;
}

.header-top .header-left {
  display: flex;
  align-items: center;
}

.header-top .header-right .sign-in-button,
.header-top .header-right .download-btn {
  display: inline-block;
  background-color: transparent; 
  color: white; 
  border: none; 
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: color 0.3s ease;
  text-decoration: none;
}

.header-top .header-right .sign-in-button:hover,
.header-top .header-right .download-btn:hover,
.header-top .header-right .sign-in-button:active,
.header-top .header-right .download-btn:active {
  color: grey; 
}

hr {
  border: 0;
  border-top: 1px solid #333;
  margin: 0;
}

.header-center {
  display: flex;
  justify-content: left;
  margin-left: 0px;
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

.header-center .center-button {
  background-color: transparent; 
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.header-center .center-button:hover,
.header-centre button:active {
  color: grey;
}



main {
  flex: 1;
  padding: 32px;
  width: 100%; 
}

.header-right {
  position: relative;
  z-index: 1;
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


.card-container {
  display: grid;
  grid-template-columns: repeat(5, 1fr); 
  gap: 16px; 
  justify-content: center; 
}

.game-card {
  background-color: #000; 
  color: white;
  font-size: 12px;
  border-radius: 8px;
  padding: 0px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.game-card img {
  width: 170px; 
  height: 200px; 
  border-radius: 3px;
  margin-bottom: 8px;
}

.game-card h3 {
  font-weight: bold;
  margin: 8px 0 4px;
}

.game-card p {
  font-weight: normal; 
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
  text-align: left; 
}
.game-name {
  font-weight: bold; 
  margin: 0;
}

.game-price {
  font-weight: normal; 
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
</style>







