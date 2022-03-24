<template>
  <div>
    <span>{{ currentModel.name }}</span>
    <span>{{ currentModel.description }}</span>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

  
export default {
  methods: {
    ...mapActions(["fetchModel"])
  },
  computed: {
    ...mapGetters(["currentModel"])
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.id) {
        vm.fetchModel(to.params.id);
      }
    });
  }
  
}
</script>

<style>

</style>