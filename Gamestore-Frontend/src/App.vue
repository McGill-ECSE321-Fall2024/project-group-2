<template>
  <div id="app">
    <header>
      <img src="@/assets/logo.svg" alt="Your logo" />
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/employee">Employee</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </header>

    <!-- This is where the route component (EmployeeView.vue, etc.) will be inserted -->
    <RouterView />
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'App',
});
</script>

<style>
#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  height: 100vh; /* Full screen height */
}

header {
  width: 100%;
  background-color: #333;
  color: #fff;
  padding: 10px;
}

nav {
  display: flex;
  justify-content: center;
  gap: 20px;
}

nav a {
  color: #fff;
  text-decoration: none;
}

#app {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  height: 100vh;
  padding: 20px;
}
</style>