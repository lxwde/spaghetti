import { createRouter, createWebHashHistory } from 'vue-router';
import Home from '@/components/Home.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/details/:id',
    name: 'ModelDetails',
    component: () => import(/* webpackChunkName: 'ModelDetails' */ '../components/ModelDetails.vue'),
  },
  {
    path: '/whereUsed/:id',
    name: 'WhereUsedList',
    component: () => import(/* webpackChunkName: 'WhereUsedList' */ '../components/WhereUsedList.vue'),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;