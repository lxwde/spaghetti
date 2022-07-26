import axios from "axios";

const state = {
  tpFields: [],
  fieldTypeFields: [],
  eventTypeFields: [],
  codeListFields: [],
};

const getters = {
  allTpFields: state => state.tpFields,
  allFieldTypeFields: state => state.fieldTypeFields,
  allEventTypeFields: state => state.eventTypeFields,
  allCodeListFields: state => state.codeListFields,
};

const mutations = {
  setTpFields: (state, tpFields) => state.tpFields = tpFields,
  setFieldTypeFields: (state, fieldTypeFields) => state.fieldTypeFields = fieldTypeFields,
  setEventTypeFields: (state, eventTypeFields) => state.eventTypeFields = eventTypeFields,
  setCodeListFields: (state, codeListFields) => state.codeListFields = codeListFields,
};

const actions = {
  async fetchTpFields({ commit }, entityId) {
    const resp = await axios.get(`http://localhost:8080/api/processTypeFields?entityId=${entityId}`);
    commit("setTpFields", resp.data);
  },
  async fetchFieldTypeFields({ commit }, entityId) {
    const resp = await axios.get(`http://localhost:8081/api/itemTypeFields?entityId=${entityId}`);
    commit("setFieldTypeFields", resp.data);
  },
  async fetchEventTypeFields({ commit }, entityId) {
    const resp = await axios.get(`http://localhost:8081/api/eventTypeFields?entityId=${entityId}`);
    commit("setEventTypeFields", resp.data);
  },
  async fetchCodeListFields({ commit }, entityId) {
    const resp = await axios.get(`http://localhost:8081/api/codeListFields?entityId=${entityId}`);
    commit("setCodeListFields", resp.data);
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
};