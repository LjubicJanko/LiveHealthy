import Axios from 'axios';

const ENDPOINTS = {
    SUBMIT: 'intake/submit/'
}

export default {
    submit(userId, submitDto) {
        return Axios.post(ENDPOINTS.SUBMIT + userId, submitDto);
    }
}