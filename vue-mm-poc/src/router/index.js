import { createRouter, createWebHashHistory } from 'vue-router';
import Home from '@/components/Home.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/details/:id',
    name: 'ModelDetails',
    component: () => import(/* webpackChunkName: 'ModelDetails' */ '../components/ModelDetails.vue')
  }
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;