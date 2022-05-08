<template>
  <li class="node-tree">
    <span class="label">{{ node.label }}</span>

    <ul v-if="node.children && node.children.length">
      <node v-for="(child, idx) in node.children" :key="idx" :node="child"></node>
    </ul>
  </li>
</template>

<script>
export default {
  name: "node",
  props: {
    node: Object
  }
};
</script>