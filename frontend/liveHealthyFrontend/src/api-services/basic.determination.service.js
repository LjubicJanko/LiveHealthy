import Axios from 'axios';

const ENDPOINTS = {
    DETERMINE: 'basic/determine/'
}

export default {
    determine(bodyInfo, userId) {
        return Axios.post(ENDPOINTS.DETERMINE + userId, bodyInfo);
    }
}