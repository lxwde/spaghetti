<template>
  <main>
    <h1>Model Editor</h1>
    <router-view/>
  </main>
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  created() {
    document.title = "mm-poc";
  }
}
</script>

<style>
body {
  background: linear-gradient(to bottom, rgb(238, 236, 236), #999);
  background-attachment: fixed;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
}
#main {
  margin: 0 auto;
  padding: 30px;
  background-color: white;
  width: 1024px;
  min-height: 300px;
}
</style>
