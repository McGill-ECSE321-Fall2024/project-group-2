<template>
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
      <router-link to="/" class="center-button">Discover</router-link>
      <router-link to="/browse" class="center-button">Browse</router-link>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
};
</script>

<style scoped>
/* Copy the styles for the header from your current HomeView page */
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

.header-left {
  display: flex;
  align-items: center;
}

.header-left h1 {
  font-size: 24px;
  font-weight: bold;
}

.header-right .sign-in-button,
.header-right .download-btn {
  background-color: transparent;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: color 0.3s ease;
  text-decoration: none;
}

.header-right .sign-in-button:hover,
.header-right .download-btn:hover {
  color: grey;
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

.center-button {
  background-color: transparent;
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.center-button:hover {
  color: grey;
}

hr {
  border: 0;
  border-top: 1px solid #333;
}
</style>
