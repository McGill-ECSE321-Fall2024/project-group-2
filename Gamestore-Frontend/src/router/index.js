import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import SignInView from '../views/SignInView.vue'; // Import the SignIn View
import CustomerDashboard from "../views/CustomerDashboard.vue";
import OwnerDashboard from "../views/OwnerDashboard.vue";
import EmployeeDashboard from "../views/EmployeeDashboard.vue";

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
    { path: "/customer-dashboard", name: "CustomerDashboard", component: CustomerDashboard },
    { path: "/employee-dashboard", name: "EmployeeDashboard", component: EmployeeDashboard },
    { path: "/owner-dashboard", name: "OwnerDashboard", component: OwnerDashboard }
  ],
});

export default router;

