<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <h3>CERTIFICATE REVOKE LIST</h3>
    <certificate-revoke-list v-bind:certificates="certificates"></certificate-revoke-list>
    <v-snackbar v-model="snackbar.show" :timeout="5000" :color="snackbar.color" :top="true">
      {{snackbar.msg}}
      <v-btn dark text @click="snackbar.show = false">Close</v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
import CertificateService from "../api-services/certificate.service";
import CertificateRevokeList from "../components/CertificateRevokeList.vue";

export default {
  name: "CertificateRevokeListOverview",
  components: {
    "certificate-revoke-list": CertificateRevokeList
  },
  data() {
    return {
      certificates: [],

      snackbar: {
        show: false,
        color: "",
        msg: ""
      }
    };
  },
  methods: {
    showSnackbar(message, color) {
      this.snackbar.color = color;
      this.snackbar.msg = message;
      this.snackbar.show = true;
    }
  },
  created: function() {
    CertificateService.getCrl()
      .then(response => {
        console.log(response.data);
        this.certificates = response.data;
      })
      .catch(error => {
        this.showSnackbar(error.response.data, "error");
      });
  }
};
</script>