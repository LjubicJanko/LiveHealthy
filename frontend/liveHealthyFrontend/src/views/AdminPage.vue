<template>
  <v-content id="admin">
    <v-container>
      <v-row>
        <v-col cols="12">
          <v-card v-if="!bodyTypeDetermined" class="ma-3 pa-6" @click="goTo('/adminPage')">
            <v-card-text class="text-center">
              <h1>Create new rule</h1>
            </v-card-text>
          </v-card>
          <v-card class="ma-3 pa-6">
            <v-card-text class="text-center">
              <h1>Something</h1>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
// import store from "@/store";
// import UserService from "../api-services/user.service";

export default {
  name: "AdminPage",
  data: () => ({
  }),
  methods: {
    goTo(url) {
      this.$router.push(url);
    }
  }
};
</script>
<style>
</style>

