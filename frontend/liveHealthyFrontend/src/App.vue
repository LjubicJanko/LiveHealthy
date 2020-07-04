<template>
  <div id="app">
    <span class="bg"></span>
    <v-app>
      <v-app-bar color="black" dense dark short app fixed>
        <v-btn @click="goTo('/')" v-if="isUser == true">
          <v-icon>mdi-home</v-icon>
        </v-btn>
        <v-btn @click="goTo('/adminPage')" v-else>
          <v-icon>mdi-shield-home-outline</v-icon>
        </v-btn>
        <v-divider class="mx-4" vertical ></v-divider>
        <v-toolbar-title>
            <v-icon large v-if="isUser == true">mdi-alpha-l-box-outline</v-icon>
            <v-icon large v-if="isUser == true">mdi-alpha-h-box</v-icon>
            <v-icon large v-if="isUser == false">mdi-alpha-a-box</v-icon>
            <v-icon large v-if="isUser == false">mdi-alpha-d-box-outline</v-icon>
            <v-icon large v-if="isUser == false">mdi-alpha-m-box-outline</v-icon>
            <v-icon large v-if="isUser == false">mdi-alpha-i-box-outline</v-icon>
            <v-icon large v-if="isUser == false">mdi-alpha-n-box-outline</v-icon>
          <v-divider class="mx-4" vertical ></v-divider>
        </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn v-if="isUser == true" @click="goTo('/profile')"><v-icon>mdi-account</v-icon></v-btn>
          <v-divider class="mx-4" vertical ></v-divider>
          <v-btn v-if="isLoggedIn == false" @click="goTo('/login')">Login</v-btn>
          <v-btn v-if="isLoggedIn == true" @click="goTo('/logout')">Logout</v-btn>
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
      console.log("logged in")
      console.log(store.state.userLoggedIn)
      return store.state.userLoggedIn;
    },
    isUser() {
      if(store.state.user.authorities != undefined) {
        return store.state.user.authorities[0].authority == "ROLE_REGISTERED";
      } else {
        return false;
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
<style>
#app {
  background-image: url("resources/LiveHealthy.png");
  /* background-image: url("https://wallpapercave.com/wp/wp2639543.jpg"); */
  background-attachment: fixed;
  background-position: top;
  background-size: 100% ;
  /* background-color: lavender; */
}
</style>
