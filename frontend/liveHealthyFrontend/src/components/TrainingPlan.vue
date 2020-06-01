<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <v-card-title>Training plan</v-card-title>
    <v-expansion-panels popout hover>
      <v-expansion-panel v-for="(item,i) in 5" :key="i">
        <v-expansion-panel-header>Day {{i+1}}</v-expansion-panel-header>
        <v-expansion-panel-content>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-card>
</template>

<script>
export default {
  name: "TrainingPlan",
  data() {
    return {};
  },
  methods: {}
};
</script>