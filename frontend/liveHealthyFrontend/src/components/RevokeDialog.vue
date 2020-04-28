<template>
  <div>
    <v-dialog v-model="revokeFormDialog" max-width="500px" persistent>
      <template v-slot:activator="{ on }">
        <v-layout justify-center>
          <v-btn class="ma-2" color="red" x-large dark v-on="on">
            Revoke Certificate
            <v-icon dark right>mdi-cancel</v-icon>
          </v-btn>
        </v-layout>
      </template>
      <v-card>
        <v-form ref="form" v-model="form" lazy-validation>
          <v-card-title primary-title>
            <span class="headline">You are about to revoke a certificate</span>
          </v-card-title>
          <v-card-text>
            <v-radio-group v-model="crlReasons">
              <v-radio label="Unspecified reasons" value="0"></v-radio>
              <v-radio label="Key is compromised" value="1"></v-radio>
              <v-radio label="Ca is compromised" value="2"></v-radio>
              <v-radio label="Affiliation is changed" value="3"></v-radio>
              <v-radio label="Superseded" value="4"></v-radio>
              <v-radio label="Cessation of operation" value="5"></v-radio>
              <v-radio label="Certificate is on hold" value="6"></v-radio>
              <v-radio label="Privilege withdrawn" value="9"></v-radio>
            </v-radio-group>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken" text @click="revokeFormDialog = false">Close</v-btn>
            <v-btn :disabled="!form" color="error" @click="revoke">Revoke</v-btn>
          </v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import CertificateService from "../api-services/certificate.service";

export default {
  name: "RevokeDialog",
  props: ["serialNo"],
  data: () => ({
    form: true,
    revokeFormDialog: false,
    crlReasons: "0"
  }),
  created() {},
  methods: {
    revoke() {
      CertificateService.revoke(this.serialNo, Number(this.crlReasons))
        .then(response => {
          console.log(response);
          this.revokeFormDialog = false;
          this.$emit("finished", {
            msg: "Certificate revoked",
            color: "success"
          });
        })
        .catch(response => {
          console.log(response);
          this.revokeFormDialog = false;
          this.$emit("finished", {
            msg: "Error! Something went wrong...",
            color: "error"
          });
        });
    }
  }
};
</script>