import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import pinDirective from './shared/pin-directive';
import currencyFilter from './shared/currency-filter';

const app = createApp(App);
app.use(store)
  .use(router)
  .directive('pin', pinDirective)
  .mount('#app');

app.config.globalProperties.$filters = {
  currency: currencyFilter,
};
