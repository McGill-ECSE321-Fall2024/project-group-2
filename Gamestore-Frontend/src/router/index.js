import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EmployeeView from '../views/EmployeeView.vue' // Ensure this view is imported

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {
        path: '/account',  // Changed to a unique path for the games view
        name: 'account',
        // route level code-splitting
        component: () => import('../views/AccountView.vue'),
      },
    {
      path: '/employee',
      name: 'employees',
      component: EmployeeView,
    },
    {
      path: '/hello',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/games',  // Changed to a unique path for the games view
      name: 'games',
      // route level code-splitting
      component: () => import('../views/GamesView.vue'),
    },
  ],
})

export default router
