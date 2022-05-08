<template>
  <div>
    <div class="header">
      <span>{{ currentModel.name }}</span>
      <span>{{ currentModel.description }}</span>
    </div>
    <div>
      <span class="tab"
            v-for="(tab, index) in tabs"
            :key="index"
            @click="tabClick(tab)"
            :class="{activeTab: selectedTab === tab}">
            {{ tab }}
      </span>
    </div>
    <div class="container">
      <div class="leftSide">
        <table>
          <thead>
            <tr>
              <th class="name">Entity Name</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="entity in entities.value" :key="entity.id">
              <td :class="['entityName', {activeItem: selectedEntity.value.id === entity.id}]"
                  @click="entityClick(entity)"
              >{{entity.name}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="rightSide">
        <table>
          <thead>
            <tr>
              <th class="name">Field Name</th>
              <th>Type</th>
              <th>Length</th>
              <th>Target Entity</th>
              <th>Target Field</th>
              <th>Where Used</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="field in fields.value" :key="field.id">
              <td class="name">{{field.name}}</td>
              <td>{{field.type}}</td>
              <td>{{field.length}}</td>
              <td>{{field.targetEntityName}}</td>
              <td>{{field.targetFieldName}}</td>
              <td>
                <router-link :to="{name: 'WhereUsedList', params: {id: field.id}}">
                drill down
                </router-link>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import {ref} from "vue";
  
export default {
  data() {
    return {
      tabs: ["Tracked Processes", "Field Types", "Event Types", "Code Lists"],
      selectedTab: 'Tracked Processes',
      entities: ref([]),
      selectedEntity: ref({}),
      fields: ref([]),
    }
  },
  methods: {
    ...mapActions(["fetchModel", "fetchTps", "fetchFieldTypes", "fetchEventTypes", "fetchCodeLists",
                    "fetchTpFields", "fetchFieldTypeFields", "fetchEventTypeFields", "fetchCodeListFields"]),
    tabClick(tab) {
      this.selectedTab = tab;
      if (tab === 'Tracked Processes') { 
        this.fetchTps(this.currentModel.id)
          .then(() => {
            this.entities.value = this.allTps;
            if (this.entities.value.length > 0) this.entityClick(this.entities.value[0]);
          });
      } else if (tab === 'Field Types') {
        this.fetchFieldTypes(this.currentModel.id)
          .then(() => {
            this.entities.value = this.allFieldTypes;
            if (this.entities.value.length > 0) this.entityClick(this.entities.value[0]);
          });
      } else if (tab === 'Event Types') {
        this.fetchEventTypes(this.currentModel.id)
          .then(() => {
            this.entities.value = this.allEventTypes;
            if (this.entities.value.length > 0) this.entityClick(this.entities.value[0]);
          });
      } else if (tab === 'Code Lists') {
        this.fetchCodeLists(this.currentModel.id)
          .then(() => {
            this.entities.value = this.allCodeLists;
            if (this.entities.value.length > 0) this.entityClick(this.entities.value[0]);
          });
      }
      // this.entityClick(this.entities.value[0]);
    },
    entityClick(entity) {
      this.selectedEntity.value = entity;
      if (this.selectedTab === 'Tracked Processes') { 
        this.fetchTpFields(entity.id)
          .then(() => this.fields.value = this.allTpFields);
      } else if (this.selectedTab === 'Field Types') {
        this.fetchFieldTypeFields(entity.id)
          .then(() => this.fields.value = this.allFieldTypeFields);
      } else if (this.selectedTab === 'Event Types') {
        this.fetchEventTypeFields(entity.id)
          .then(() => this.fields.value = this.allEventTypeFields);
      } else if (this.selectedTab === 'Code Lists') {
        this.fetchCodeListFields(entity.id)
          .then(() => this.fields.value = this.allCodeListFields);
      }
    }
  },
  computed: {
    ...mapGetters(["currentModel", "allTps", "allFieldTypes", "allEventTypes", "allCodeLists",
                   "allTpFields", "allFieldTypeFields", "allEventTypeFields", "allCodeListFields"])
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.id) {
        vm.fetchModel(to.params.id).then(() =>  {
          vm.fetchTps(vm.currentModel.id)
            .then(() => {
              vm.entities.value = vm.allTps;
              vm.selectedEntity.value = vm.allTps[0];
              vm.fetchTpFields(vm.selectedEntity.value.id)
                .then(() => vm.fields.value = vm.allTpFields);
            });
        });
      }
    });
  },
  mounted() {
    // beforeRouteEnter() is not guaranteed to be executed before mounted() 
    // this.fetchModel(this.modelId).then(() =>  {
    //   this.fetchTps(this.currentModel.id)
    //       .then(() => {
    //         this.entities.value = this.allTps;
    //         this.selectedEntity.value = this.allTps[0];
    //         this.fetchTpFields(this.selectedEntity.value.id)
    //           .then(() => this.fields.value = this.allTpFields);
    //       });
    // });
  },
  
}
</script>
<style scoped>
.header{
  margin-bottom: 20px;
}
.tab {
  margin-left: 20px;
  cursor: pointer;
}
.activeTab {
  color: #16C0B0;
  text-decoration: underline;
}
.container {
  display: flex;
  margin: 10px auto 0 auto;
  justify-content: center;
}
.leftSide {
  padding: 30px;
  background-color: #aaa;
  width: 200px;
  min-height: 300px;
}
.rightSide{
  /* margin: 0 auto; */
  padding: 30px;
  background-color: white;
  /* width: 1024px; */
  width: 864px;
  min-height: 300px;
}
td, th {
  padding: 5px;
}
.name {
  text-align: left;
  padding-right: 100px;
  word-break: break-all;
  min-width: 180px;
}
.entityName {
  text-align: left;
  padding-right: 200px;
  word-break: break-all;
  min-width: 180px;
  cursor: pointer;
}
.activeItem {
  color: #09645b;
  text-decoration: underline;
}
</style>