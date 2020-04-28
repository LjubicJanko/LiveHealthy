import Axios from 'axios';

const ENDPOINTS = {
    CREATE: 'certificate',
    GETALL: 'certificate/all',
    GET: 'certificate/',
    REVOKE: 'certificate/revoke/',
    CRL: 'certificate/crl'
}

export default {
    create(certificate) {
        console.log(certificate)
        return Axios.post(ENDPOINTS.CREATE, certificate, {responseType: 'blob'}).then(response => {
            let fileName = response.headers['content-disposition'].split('=')[1];
            var fileURL = window.URL.createObjectURL(new Blob([response.data]));
            var fileLink = document.createElement('a');

            fileLink.href = fileURL;
            fileLink.setAttribute('download', fileName);
            document.body.appendChild(fileLink);

            fileLink.click();
        })
    },

    getAll() {
        return Axios.get(ENDPOINTS.GETALL);
    },

    get(id){
        return Axios.get(ENDPOINTS.GET + id);
    },

    revoke(serialNo, crlReason) {
        return Axios.post(ENDPOINTS.REVOKE + serialNo + '/' + crlReason);
    },

    getCrl(){
        return Axios.get(ENDPOINTS.CRL);
    }
}