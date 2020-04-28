<template>
  <v-card class="mx-10 px-5 py-5 mt-12 text-center">
    <h3>CERTIFICATE INFO</h3>
    <!--Certificate details-->
    <certificate-details v-bind:certificate="certificate"></certificate-details>
    <!--Revoke dialog-->
    <revoke-dialog v-bind:serialNo="certificate.serialNo" v-on:finished="closeRevokeDialog($event)"></revoke-dialog>
    <!--Snackbar-->
    <v-snackbar v-model="snackbar.show" :timeout="5000" :color="snackbar.color" :top="true">
      {{snackbar.msg}}
      <v-btn dark text @click="snackbar.show = false">Close</v-btn>
    </v-snackbar>
  </v-card>
</template>

<script>
import CertificateService from "../api-services/certificate.service";
import CertificateDetails from "../components/CertificateDetails.vue";
import RevokeDialog from "../components/RevokeDialog.vue";

export default {
  name: "CertificateOverview",
  components: {
    "revoke-dialog": RevokeDialog,
    "certificate-details": CertificateDetails
  },
  data() {
    return {
      certificate: {
        id: 0,
        aliasName: "",
        startDate: null,
        endDate: null,
        serialNo: 0,
        isRevoked: false,
        crlReason: null,
        subject: {
          cn: "",
          o: "",
          ou: "",
          l: "",
          st: "",
          c: "",
          e: ""
        }
      },

      snackbar: {
        show: false,
        color: "",
        msg: ""
      }
    };
  },
  methods: {
    closeRevokeDialog(data) {
      this.showSnackbar(data.msg, data.color);
      setTimeout(() => this.$router.push("/all-certificates"), 500);
    },
    showSnackbar(message, color) {
      this.snackbar.color = color;
      this.snackbar.msg = message;
      this.snackbar.show = true;
    }
  },
  created: function() {
    CertificateService.get(this.$route.params["id"])
      .then(response => {
        this.certificate.id = response.data.id;
        this.certificate.aliasName = response.data.aliasName;
        this.certificate.startDate = response.data.startDate;
        this.certificate.endDate = response.data.endDate;
        this.certificate.serialNo = response.data.serialNo;
        this.certificate.isRevoked = response.data.isRevoked;
        this.certificate.crlReason = response.data.crlReason;
        this.certificate.subject = response.data.subject;
      })
      .catch(error => {
        this.showSnackbar(error.response.data, "error");
      });
  }
};
</script>