import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import SignInView from '../views/SignInView.vue'; // Import the SignIn View
import EmployeeView from '../views/EmployeeView.vue' // Ensure this view is imported
import CustomerView from '../views/CustomerView.vue' // Ensure this view is imported
import OwnerView from '../views/OwnerView.vue' // Ensure this view is imported
import AccountView from '../views/AccountView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {
        path: '/account',  // Changed to a unique path for the games view
        name: 'account',
        // route level code-splitting
        component: AccountView,
      },
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
    { path: "/customer", name: "customer", component: CustomerView },
    { path: "/employees", name: "employee", component: EmployeeView },
    { path: "/owner", name: "owner", component: OwnerView }
  ],
});

export default router;

