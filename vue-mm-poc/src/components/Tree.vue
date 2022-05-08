<template>
  <div class="tree">
    <ul class="tree-list">
      <tree-node :node="treeData"></tree-node>
    </ul>
  </div>
</template>

<script>
import TreeNode from "./TreeNode";

export default {
  props: {
    treeData: Object
  },
  components: {
    TreeNode
  }
};
</script>

<style>
.tree-list ul {
  padding-left: 16px;
  margin: 6px 0;
}
</style>