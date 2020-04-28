<template>
  <div>
    <v-data-table :headers="headers" :items="certificates">
      <template v-slot:item="row">
        <tr>
          <td>
            <v-layout justify-center>{{row.item.id}}</v-layout>
          </td>
          <td>
            <v-layout justify-center>{{row.item.aliasName}}</v-layout>
          </td>
          <td>
            <v-layout justify-center>{{row.item.serialNo}}</v-layout>
          </td>
          <td>
            <v-layout justify-center>{{row.item.startDate}}</v-layout>
          </td>
          <td>
            <v-layout justify-center>{{row.item.endDate}}</v-layout>
          </td>
          <td>
            <v-layout justify-center>
              <v-btn @click="certificateDetails(row.item.id)">
                <v-icon dark>mdi-format-list-bulleted-square</v-icon>
              </v-btn>
            </v-layout>
          </td>
        </tr>
      </template>
    </v-data-table>
  </div>
</template>



<script>
import CertificateService from "../api-services/certificate.service";

export default {
  name: "CertificatesTableComponent",
  data() {
    return {
      headers: [
        {
          text: "Id",
          align: "start",
          sortable: false,
          value: "id"
        },
        { text: "Name", value: "aliasName", align: "center" },
        { text: "Serial NO", value: "serialNo", align: "center" },
        { text: "Creation date", value: "startDate", align: "center" },
        { text: "Valid until", value: "endDate", align: "center" },
        { text: "More", value: "more", align: "center" }
      ],
      certificates: []
    };
  },
  methods: {
    certificateDetails(id) {
      this.$router.push('/certificate/' + id);
      
    }
  },
  created: function() {
    CertificateService.getAll().then(response => {
      response.data.forEach(certificate => {
        this.certificates.push({
          id: certificate.id,
          aliasName: certificate.aliasName,
          serialNo: certificate.serialNo,
          startDate: certificate.startDate,
          endDate: certificate.endDate
        });
      });
    });
  }
};
</script>