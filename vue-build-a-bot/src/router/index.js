import { createRouter, createWebHashHistory } from 'vue-router';

export default createRouter({
  history: createWebHashHistory(),
  routes: [{
    path: '/',
    name: 'Home',
    components: {
      default: () => import(/* webpackChunkName: "home" */ '../home/HomePage.vue'),
      sidebar: () => import(/* webpackChunkName: "sidebar-std" */ '../sidebar/SidebarStandard.vue'),
    },
  }, {
    path: '/build',
    name: 'Build',
    components: {
      default: () => import(/* webpackChunkName: "build" */ '../build/RobotBuilder.vue'),
      sidebar: () => import(/* webpackChunkName: "sidebar-build" */ '../sidebar/SidebarBuild.vue'),
    },
  }, {
    path: '/parts/browse',
    name: 'browseParts',
    component: () => import(/* webpackChunkName: "browse" */ '../parts/BrowseParts.vue'),
    children: [{
      name: 'BrowseHeads',
      path: 'heads',
      component: () => import(/* webpackChunkName: "heads" */ '../parts/RobotHeads.vue'),
    }, {
      name: 'BrowseArms',
      path: 'arms',
      component: () => import(/* webpackChunkName: "arms" */ '../parts/RobotArms.vue'),
    }, {
      name: 'BrowseTorsos',
      path: 'torsos',
      component: () => import(/* webpackChunkName: "torsos" */ '../parts/RobotTorsos.vue'),
    }, {
      name: 'BrowseBases',
      path: 'bases',
      component: () => import(/* webpackChunkName: "browse" */ '../parts/RobotBases.vue'),
    }],
  }, {
    path: '/parts/:partType/:id',
    name: 'Parts',
    component: () => import(/* webpackChunkName: "parts" */ '../parts/PartInfo.vue'),
    props: true,
    beforeEnter(to, from, next) {
      const isValidId = Number.isInteger(+to.params.id);
      next(isValidId);
    },
  }, {
    path: '/cart',
    name: 'Cart',
    component: () => import(/* webpackChunkName: "cart" */ '../cart/ShoppingCart.vue'),
  },
  ],
});
