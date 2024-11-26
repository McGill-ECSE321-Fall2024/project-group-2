import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import SignInView from '../views/SignInView.vue'; // Import the SignIn View

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/', // Default route for the home page
      name: 'home',
      component: HomeView,
    },
    {
      path: '/signin', // Route for the SignIn page
      name: 'signin',
      component: SignInView,
    },
  ],
});

export default router;

