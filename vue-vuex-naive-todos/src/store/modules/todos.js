import axios from "axios";

const state = {
    todos: []
};

const getters = {
    allTodos: state => state.todos
};

const mutations = {
    setTodos: (state, todos) => state.todos = todos,
    newTodo: (state, todo) => state.todos.unshift(todo),
    removeTodo: (state, id) => state.todos = state.todos.filter(todo => todo.id !== id),
    updateTodo: (state, todo) => {
        const idx = state.todos.findIndex(curr => curr.id === todo.id);
        if (idx != -1) {
            state.todos.splice(idx, 1, todo);
        }
    }
};

const actions = {
    async fetchTodos({ commit }) {
        const response = await axios.get("https://jsonplaceholder.typicode.com/todos");
        commit("setTodos", response.data);
    },
    async addTodo({ commit }, title) {
        const response = await axios.post("https://jsonplaceholder.typicode.com/todos",
            { title, completed: false });
        commit("newTodo", response.data)
    },
    async deleteTodo({ commit }, id) {
        await axios.delete(`https://jsonplaceholder.typicode.com/todos/${id}`);
        commit("removeTodo", id);
    },
    async filterTodos({ commit }, e) {
        const options = e.target.options;
        const limit = parseInt(options[options.selectedIndex].innerText);
        const response = await axios.get(`https://jsonplaceholder.typicode.com/todos?limit=${limit}`);
        commit("setTodos", response.data);
    },
    async updateTodo({ commit }, todo) {
        await axios.put(`https://jsonplaceholder.typicode.com/todos/${todo.id}`, todo);
        commit("updateTodo", todo);
    }
};


export default {
    state,
    getters,
    mutations,
    actions
};