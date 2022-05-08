<template>
  <div>
    <tree :tree-data="whereUsed"></tree>
  </div>
</template>

<script>
import Tree from "./Tree";
import {mapGetters, mapActions} from "vuex";

export default {
  components: {
    Tree,
  },
  data() {
    return {
      name: '',
    }
  },
  methods: {
    ...mapActions(['fetchWhereUsed']),
  },
  computed: {
    ...mapGetters(['whereUsed']),
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.id) {
        vm.fetchWhereUsed(to.params.id).then(() =>  {
          
        });
      }
    });
  }
}
</script>