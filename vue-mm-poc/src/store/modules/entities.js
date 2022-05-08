import axios from "axios";

const state = {
  trackedProcesses: [],
  fieldTypes: [],
  eventTypes: [],
  codeLists: [],
  currentTp: {},
  currentFieldType: {},
  currentEventType: {},
  currentCodeList: {},
};

const getters = {
  allTps: state => state.trackedProcesses,
  allFieldTypes: state => state.fieldTypes,
  allEventTypes: state => state.eventTypes,
  allCodeLists: state => state.codeLists,
  currentTp: state => state.currentTp,
  currrentFieldType: state => state.currentFieldType,
  currentEventType: state => state.currentEventType,
  currentCodeList: state => state.currentCodeList,
};

const mutations = {
  setTps: (state, trackedProcesses) => state.trackedProcesses = trackedProcesses,
  setFieldTypes: (state, fieldTypes) => state.fieldTypes = fieldTypes,
  setEventTypes: (state, eventTypes) => state.eventTypes = eventTypes,
  setCodeLists: (state, codeLists) => state.codeLists = codeLists,
  setCurrentTp: (state, tp) => state.currentTp = tp,
  setCurrentFieldType: (state, fieldType) => state.currentFieldType = fieldType,
  setCurrentEventType: (state, eventType) => state.currentEventType = eventType,
  setCurrentCodeList: (state, codeList) => state.currentCodeList = codeList,
};

const actions = {
  async fetchTps({ commit }, modelId) {
    axios.get(`http://localhost:8081/api/processTypes?modelId=${modelId}`)
      .then((resp) => commit("setTps", resp.data));
  },
  async fetchFieldTypes({ commit }, modelId) {
    const resp = await axios.get(`http://localhost:8081/api/itemTypes?modelId=${modelId}`);
    commit("setFieldTypes", resp.data);
  },
  async fetchEventTypes({ commit }, modelId) {
    const resp = await axios.get(`http://localhost:8081/api/eventTypes?modelId=${modelId}`);
    commit("setEventTypes", resp.data);
  },
  async fetchCodeLists({ commit }, modelId) {
    const resp = await axios.get(`http://localhost:8081/api/codeLists?modelId=${modelId}`);
    commit("setCodeLists", resp.data);
  },
};

export default {
  state,
  getters,
  mutations,
  actions,
}