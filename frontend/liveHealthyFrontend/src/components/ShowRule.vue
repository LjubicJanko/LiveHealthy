<template>
  <v-card class="mx-auto " color="dark" dark shaped>
    <v-card-title>
      <v-icon large left>mdi-alert-octagon</v-icon>
      <span class="title font-weight-light">Created rule</span>
    </v-card-title>

    <v-card-text 
      class="headline font-weight-bold pre-formatted text-xs-center justify-center"
      style="max-width: 50%"
    >{{rule}}</v-card-text>
  </v-card>
</template>
<script>
export default {
  props: ["rule"],
  name: "ShowRule",
  data: () => ({
    //
  })
};
</script>
<style>
.pre-formatted {
  white-space: pre;
}
</style>