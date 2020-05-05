import Vue from 'vue';
import Vuex from 'vuex';
// import { auth } from './modules';

Vue.use(Vuex)

// const storeData = {
//   modules: {
//     auth,
//   }
// }

// export default new Vuex.Store({
//   storeData
// });

export default new Vuex.Store({
  state: {
    userLoggedIn: false 
  },
  
  getters: {
  },
  
  mutations: {
    login(state) {
      state.userLoggedIn = true;
    },
    logout(state) {
      state.userLoggedIn = false;
    }
  },
  
  actions: {
    login(context) {
        context.commit('login')
    },
    logout(context) {
        context.commit('logout')
    }
  }
});