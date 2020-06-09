import Axios from 'axios';

const ENDPOINTS = {
    CREATE: 'dynamic/bodyType/'
}

export default {
    create(createBodyTypeRuleDto) {
        return Axios.post(ENDPOINTS.CREATE, createBodyTypeRuleDto);
    }
}