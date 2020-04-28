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
        </v-col>
      </v-row>
    </v-container>
  </v-content>
</template>

<script>
export default {
  name: "Home",
  methods: {
    goTo(url) {
      this.$router.push(url);
    }
  }
};
</script>
