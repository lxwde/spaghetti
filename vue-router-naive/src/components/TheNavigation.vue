<template>
    <nav id="nav">
        <!-- <router-link to="/">Home</router-link>
        <router-link to="/brazil">Brazil</router-link>
        <router-link to="panama">Panama</router-link>
        <router-link to="hawaii">Hawaii</router-link>
        <router-link to="jamaica">Jamaica</router-link> -->
        <ul class="nav-links">
          <li class="links">
            <router-link to="/">
              Home
            </router-link>
          </li>
          <li v-for="destination in destinations"
          :key="destination.name" class="links">
            <router-link :to="{
              name: 'DestinationDetails',
              params: {id: destination.id}
            }">
              {{ destination.name }}
            </router-link>
          </li>
        </ul>
    </nav>
</template>

<script>
import store from '@/store.js'
export default {
  data () {
    return {
      destinationId: this.$route.params.id,
      destinations: store.destinations
    }
  }
}
</script>

<style scoped lang="less">
#nav {
display: flex;
    justify-content: center;
    padding: 30px;

    a {
        font-weight: bold;
        color: #2c3e50;
        padding: 0 10px;

        // &.router-link-exact-active {
        //     color: purple;
        // }
        &.vue-school-active-class {
            color: purple
        }
    }
}
.nav-links {
  display: flex;
}

.links {
  padding-right: 20px;
  list-style: none;
}

.links:hover {
  text-decoration: underline;
}
</style>
