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
            <v-layout justify-center>{{row.item.crlReason.substring(10)}}</v-layout>
          </td>
        </tr>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  name: "CertificateRevokeList",
  props: ["certificates"],
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
        { text: "Revokation reason", value: "crlReason", align: "center" }
      ],
      snackbar: {
        show: false,
        color: "",
        msg: ""
      }
    };
  }
};
</script>