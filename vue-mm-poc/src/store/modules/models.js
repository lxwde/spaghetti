import axios from "axios";

const state = {
    models: [],
    currentModel: {}
};

const getters = {
    allModels: state => state.models,
    currentModel: state => state.currentModel
};

const mutations = {
    setModels: (state, models) => state.models = models,
    setCurrentModel: (state, model) => state.currentModel = model,
};

const actions = {
    async fetchModels({ commit }) {
        const response = await axios.get("http://localhost:8080/api/models");
        commit("setModels", response.data);
    },
    async fetchModel({ commit }, id) {
        const response = await axios.get(`http://localhost:8080/api/models/${id}`);
        commit("setCurrentModel", response.data);
    }
};

export default {
    state,
    getters,
    mutations,
    actions,
};