<template>
  <v-content>
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-card class="ma-3 pa-6" @click="goTo('/body-type')">
            <v-card-text class="text-center">
              <h1>Find your body type</h1>
            </v-card-text>
          </v-card>
          <v-card v-if="isLoggedIn" class="ma-3 pa-6" @click="myPlan()">
            <v-card-text class="text-center">
              <h1>My food and training plan</h1>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
import store from '@/store';

export default {
  name: "Home",
  computed: {
    isLoggedIn() {
      if(store.state.userLoggedIn){
        return true;
      }else {
        return false;
      }
    }
  },
  methods: {
    goTo(url) {
      this.$router.push(url);
    },
    myPlan() {
      this.$router.push('myPlan/' + store.state.userId);
      // this.$router.push('myPlan/');
    }
  }
};
</script>
