import Axios from 'axios';

const ENDPOINTS = {
    LOGIN: 'basic/determine',
    SIGNIN: 'basic/signIn'
}

export default {
    login(bodyInfo) {
        return Axios.post(ENDPOINTS.LOGIN, bodyInfo);
    },
    signIn(registration){
        return Axios.post(ENDPOINTS.SIGNIN, registration)
    }
}