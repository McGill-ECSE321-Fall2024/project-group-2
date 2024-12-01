<template>
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
        <router-link to="/">
                  <button>Discover</button>
                </router-link>
        <router-link to="/browse">
                  <button>Browse</button>
                </router-link>
      </div>
    </header>
  <div class="browse-view">
    <!-- Main Content -->
    <div class="main-content">
      <!-- Games Section -->
      <section class="games-section">
        <h2>Browse Games</h2>
        <div class="game-grid">
          <div class="game-card" v-for="game in filteredGames" :key="game.id">
            <img :src="game.image" :alt="game.name" class="game-image" />
            <h3>{{ game.name }}</h3>
            <p>{{ game.price }}</p>
          </div>
        </div>
      </section>
    </div>

    <!-- Sidebar -->
    <aside class="sidebar">
      <h2>Categories</h2>
      <ul class="categories-list">
        <li
          v-for="category in categories"
          :key="category.id"
          @click="filterByCategory(category.name)"
          :class="{ active: selectedCategory === category.name }"
        >
          {{ category.name }}
        </li>
      </ul>
    </aside>
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


export default {
  data() {
    return {
      // List of games to display
      games: [
        { id: 1, name: "EA SPORTS FC™ 25", price: "$89.99", image: picture1, category: "Sports" },
        { id: 2, name: "Alan Wake 2", price: "$66.99", image: picture2, category: "Adventure" },
        { id: 3, name: "Cyberpunk 2077", price: "$79.99", image: picture3, category: "RPG" },
        { id: 4, name: "Hogwarts Legacy", price: "$79.99", image: picture19, category: "Fantasy" },
        { id: 5, name: "Farming Simulator 25", price: "$64.99", image: picture20, category: "Simulation" },
        { id: 6, name: "The Witcher 3", price: "$49.99", image: picture15, category: "RPG" },
        { id: 7, name: "FIFA 25", price: "$89.99", image: picture28, category: "Sports" },
        { id: 8, name: "Red Dead Redemption 2", price: "$59.99", image: picture23, category: "Adventure" },
      ],

      // List of categories for the sidebar
      categories: [
        { id: 1, name: "All" },
        { id: 2, name: "Sports" },
        { id: 3, name: "Adventure" },
        { id: 4, name: "RPG" },
        { id: 5, name: "Fantasy" },
        { id: 6, name: "Simulation" },
      ],

      // Currently selected category
      selectedCategory: "All",
    };
  },
  computed: {
    // Dynamically filter games based on the selected category
    filteredGames() {
      if (this.selectedCategory === "All") {
        return this.games;
      }
      return this.games.filter(game => game.category === this.selectedCategory);
    },
  },
  methods: {
    // Update the selected category when a category is clicked
    filterByCategory(category) {
      this.selectedCategory = category;
    },
  },
};
</script>


<style scoped>
.logo {
  height: 50px;
  width: 50px;
  margin-right: 10px; /* Add spacing between the logo and the heading */
  display: inline-block;
  vertical-align: middle; /* Align logo vertically with text */
  border-radius: 28px;
  margin-left: 10px;
}

/* General Reset */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* Header */
header {
  width: 100%; /* Full width of the screen */
  background-color: #000;
  color: white;
  position: fixed; /* Make the header fixed at the top */
  top: 0;
  left: 0;
  z-index: 1000; /* Ensure it stays on top */
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
  display: flex; /* Align logo and heading horizontally */
  align-items: center;
}

.header-top .header-right .sign-in-button,
.header-top .header-right .download-btn {
  background-color: transparent; /* No background */
  color: white; /* Default text color */
  border: none; /* Remove borders */
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: color 0.3s ease; /* Smooth transition for text color */
  text-decoration: none; /* Remove underline for the link */
}

.header-top .header-right .sign-in-button:hover,
.header-top .header-right .download-btn:hover {
  color: grey; /* Change text color on hover */
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
  background-color: transparent; /* No background */
  color: white; /* Default text color */
  border: none; /* Remove borders */
  border-radius: 4px;
  cursor: pointer;
  transition: color 0.3s ease; /* Smooth transition for text color */
}

.header-center button:hover
.header-centre button:active {
  color: grey; /* Change text color on hover */
}

.header button:hover {
   background-color: #000;
}


/* Browse View Layout */
.browse-view {
  display: flex;
  margin-top: 150px; /* Create space below the fixed header */
  padding: 32px; /* Add padding for aesthetics */
  gap: 32px; /* Space between sidebar and main content */
}

/* Sidebar for Categories */
.sidebar {
  flex: 0 0 auto; /* Fixed width for the sidebar */
  width: 200px;
  padding: 16px 8px;
  margin-right: 20px;
  position: sticky;
}

.sidebar h2 {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
}

.categories-list {
  list-style: none;
  padding: 0;
}

.categories-list li {
  margin-bottom: 12px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.categories-list li:hover,
.categories-list li.active {
  background-color: #f0f0f0;
  font-weight: bold;
}

/* Main Content */
.main-content {
  flex-grow: 1; /* Take up the remaining space */
  display: flex;
  flex-direction: column;
}

/* Games Section */
.games-section h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 16px;
}

.game-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* Responsive grid */
  gap: 16px; /* Space between game cards */
}

.game-card {
  background-color: #000; /* Darker card background */
  color: white;
  font-size: 12px;
  border-radius: 8px;
  padding: 16px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5); /* Subtle shadow */
  transition: transform 0.3s;
  margin-left: 2px;
}

.game-card:hover {
  transform: scale(1.05); /* Add hover effect */
}

.game-card img {
  width: 140px;
  height: 140px;
  border-radius: 8px;
  margin-bottom: 12px;
  margin-left: 2px;
}

.game-card h3 {
  font-weight: bold;
  margin: 8px 0 4px;
  font-size: 16px;
}

.game-card p {
  font-weight: normal;
  margin: 0;
  font-size: 14px;
}

</style>