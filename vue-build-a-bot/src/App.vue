<template>
  <header>
      <nav>
        <ul>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name: 'Home'}" exact>
              <img class="logo" src="./assets/build-a-bot-logo.png"/>
              Build a Bot
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name: 'Build'}" exact>
              Build
            </router-link>
          </li>
          <li class="nav-item">
            User: {{user.userName}}
          </li>
          <li class="nav-item cart">
            <router-link class="nav-link" to="/cart" exact>
              Cart
            </router-link>
            <div class="cart-items">
              {{cart.length}}
            </div>
          </li>
        </ul>
      </nav>
    </header>
    <div class="container">
      <aside class="aside">
        <router-view name="sidebar"></router-view>
      </aside>
      <main>
        <router-view></router-view>
      </main>
    </div>

    <div>
      Root Foo: {{rootFoo}} <br/>
      Robots Foo: {{robotsFoo}} <br/>
      Users Foo: {{usersFoo}} <br/>
      <br/>
      Root Getter Foo: {{rootGetterFoo}} <br/>
      Robots Getter Foo: {{robotsGetterFoo}} <br/>
      Users Getter Foo: {{usersGetterFoo}} <br/>
    </div>
</template>

<script>
// import HomePage from './home/HomePage.vue';
// import RobotBuilder from './build/RobotBuilder.vue';
// import Search from './search/Search.vue';
import { mapState, mapGetters } from 'vuex';

export default {
  name: 'App',
  components: {
    // HomePage,
    // RobotBuilder,
    // Search,
  },
  mounted() {
    document.title = 'build-a-bot';
  },
  data() {
    return {
      // use objects or reactive api instead of primitive type
      // userName: 'Jim',
      user: { userName: 'Jim' },
    };
  },
  provide() {
    return { user: this.user };
  },
  computed: {
    ...mapState({
      rootFoo: 'foo',
      usersFoo: (state) => state.users.foo,
    }),
    // only works for namespaced modules
    ...mapState('robots', { robotsFoo: 'foo' }),
    ...mapGetters({ rootGetterFoo: 'foo' }),
    ...mapGetters('robots', { robotsGetterFoo: 'foo' }),
    // rootFoo() {
    //   return this.$store.state.foo;
    // },
    // robotFoo() {
    //   return this.$store.state.robots.foo;
    // },
    // usersFoo() {
    //   return this.$store.state.users.foo;
    // },
    // rootGetterFoo() {
    //   return this.$store.getters.foo;
    // },
    // robotsGetterFoo() {
    //   return this.$store.getters['robots/foo'];
    // },
    usersGetterFoo() {
      return this.$store.getters['users/foo'];
    },
    cart() {
      return this.$store.state.robots.cart;
    },
  },
};
</script>

<style>
body {
  background: linear-gradient(to bottom, #555, #999);
  background-attachment: fixed;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
</style>

<style scoped>
main{
  /* margin: 0 auto; */
  padding: 30px;
  background-color: white;
  /* width: 1024px; */
  width: 964px;
  min-height: 300px;
}
header {
  background-color: #999;
  /* width: 1084px; */
  width: 1184px;
  margin: 0 auto;
}
ul {
  padding: 3px;
  display: flex;
}
.nav-item {
  display: inline-block;
  padding: 5px 10px;
  font-size: 22px;
  border-right: 1px solid #bbb;
}
.nav-item.cart {
  position: relative;
  margin-left: auto;
  border-right: none;
}
.logo {
  vertical-align: middle;
  height: 30px;
}
.nav-link {
  text-decoration: none;
  color: inherit;
}
.router-link-active {
  color: white;
}
.container {
  display: flex;
  margin: 10px auto 0 auto;
  justify-content: center;
}
.aside {
  padding: 30px;
  background-color: #aaa;
  width: 100px;
  min-height: 300px;
}
.cart-items {
  position: absolute;
  top: -5px;
  right: -9px;
  font-size: 18px;
  width: 20px;
  text-align: center;
  display: inline-block;
  border-radius: 100px;
  background-color: mediumseagreen;
}
</style>
