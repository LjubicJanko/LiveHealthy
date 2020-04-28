import Axios from 'axios';

const ENDPOINTS = {
    DETERMINE: 'basic/determine'
}

export default {
    determine(bodyInfo) {
        console.log(bodyInfo)
        return Axios.post(ENDPOINTS.DETERMINE, bodyInfo, {responseType: 'blob'}).then(response => {
            alert(response)
        })
    }
}