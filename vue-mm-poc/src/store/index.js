import { createStore } from "vuex";
import models from "./modules/models";
import entities from "./modules/entities";
import fields from "./modules/fields";
import whereUsed from "./modules/whereUsed";

export default new createStore({
    state: {
    },
    mutations: {
    },
    actions: {
    },
    modules: {
        models,
        entities,
        fields,
        whereUsed,
    }
});