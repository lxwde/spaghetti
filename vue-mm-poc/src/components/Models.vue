<template>
  <div>
      <h3>Models</h3>
      <div class="models">
          <div 
          v-for="model in allModels"
          :key="model.id"
          class="model"
          >
            <router-link 
            :to="{name: 'ModelDetails', params: {id: model.id}}"
            >
                {{ model.name }}
            </router-link>
          </div>
      </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
    name: "Models",
    methods: {
        ...mapActions(["fetchModels"]),
    },
    computed: {
        ...mapGetters(['allModels']),
    },
    created() {
       this.fetchModels();
    }
}
</script>

<style scoped>
.models {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 1rem;
}

.model {
    border: 1px solid #ccc;
    background: #41b883;
    padding: 1rem;
    border-radius: 5px;
    text-align: center;
    position: relative;
    cursor: pointer;
    user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    -webkit-user-select: none;
}

</style>