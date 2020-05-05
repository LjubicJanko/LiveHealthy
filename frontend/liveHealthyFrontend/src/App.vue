<template>
  <div id="app">
    <v-app>
      <v-app-bar color="black" dense dark short app fixed>
        <v-btn @click="goTo('/')">
          <v-icon>mdi-home</v-icon>
        </v-btn>
        <v-toolbar-title>
          <v-icon large>mdi-alpha-l-box-outline</v-icon>
          <v-icon large>mdi-alpha-h-box</v-icon>Live Healthy
        </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn v-if="isLoggedIn == true" @click="goTo('/login')">Login</v-btn>
          <v-btn v-if="isLoggedIn == false" @click="goTo('/logout')">Logout</v-btn>
      </v-app-bar>
      <v-content>
        <router-view />
      </v-content>
    </v-app>
  </div>
</template>

<script>
import store from '@/store';

export default {
  name: "App",
  data: () => ({
  }),
  computed: {
    isLoggedIn() {
      if(store.state.userLoggedIn){
        return false;
      }else {
        return true;
      }
    }
  },
  components: {},
  
  methods: {
    goTo(url) {
      this.$router.push(url);
    }
  }
};
</script>
