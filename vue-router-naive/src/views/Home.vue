<template>
  <div class="home">
    <h1>
      All Destinations
    </h1>
    <div class="destinations">
      <div v-for="destination in destinations" :key="destination.name">
        <router-link :to="{ name: 'DestinationDetails', params: {id: destination.id} }">
          <h2>{{destination.name}}</h2>
        </router-link>
        <figure>
          <router-link :to=" { name: 'DestinationDetails', params: {id: destination.id} }">
            <img :src="require(`@/assets/${destination.image}`)"
            :alt="destination.name">
          </router-link>
        </figure>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import store from '@/store.js'

@Component({
  components: {}
})
export default class Home extends Vue {
  data () {
    return {
      destinations: store.destinations
    }
  }
}
</script>

<style scoped>
.home {
  max-width: 1400px;
  margin: 0 auto;
}
img {
  max-width: 200px;
}
.destinations {
  display: flex;
  justify-content: space-between;
}
</style>
