import Axios from 'axios';

const ENDPOINTS = {
    DETERMINE: 'basic/determine'
}

export default {
    determine(bodyInfo) {
        return Axios.post(ENDPOINTS.DETERMINE, bodyInfo);
    }
}