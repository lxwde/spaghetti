import axios from "axios";

const state = {
  whereUsed: {}
};

const getters = {
  whereUsed: state => state.whereUsed,
};

const mutations = {
  setWhereUsed: (state, whereUsed) => {
    let converted = {};
    convertWhereUsed(whereUsed, converted);
    state.whereUsed = converted;
  },
};

const convertWhereUsed = (from, to) => {
  to.label = from.object + " " + from.objectType;
  if (from.categories) {
    to.children = [];
    for (let i = 0; i < from.categories.length; i++) {
      to.children.push(from.categories[i]);
      convertWhereUsed(from.categories[i], to.children[i]);
    }
  }
};

const actions = {
  async fetchWhereUsed({ commit }, fieldId) {
    axios.get(`http://localhost:8081/api/whereUsed?fieldId=${fieldId}`)
      .then((resp) => {
        commit("setWhereUsed", resp.data[0]);
      });
  }
};

export default {
  state,
  getters,
  mutations,
  actions,
};