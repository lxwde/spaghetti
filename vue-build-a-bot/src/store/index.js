import { createStore } from 'vuex';
import robots from './modules/robots';
import users from './modules/users';

export default createStore({
  state: {
    foo: 'root-foo',
  },
  modules: {
    robots,
    users,
  },
  getters: {
    foo(state) {
      return `root-getter/${state.foo}`;
    },
  },
});
