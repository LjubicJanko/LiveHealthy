<template>
  <v-snackbar v-model="show" :timeout="timeout" :color="color" top>
    {{message}}
    <v-btn flat  @click.native="show = false">Close</v-btn>
  </v-snackbar>
</template>

<script>

import store from "@/store";

export default {
  data () {
    return {
      show: false,
      message: '',
      color: '',
      timeout: 5000,
    }
  },
  created: function () {
    store.watch(state => state.snackbar.message, () => {
      const msg = store.state.snackbar.message
      if (msg !== '') {
        this.show = true;
        this.message = store.state.snackbar.message;
        this.color = store.state.snackbar.color;
        this.$nextTick(() => {
          store.commit('setSnack', {msg: '', color: ''});
        })
      }
    })
  }

}
</script>