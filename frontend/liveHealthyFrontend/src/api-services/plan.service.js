import Axios from 'axios';

const ENDPOINTS = {
    CREATE: 'plan/create/'
}

export default {
    create(userId, createPlanInfoDto) {
        return Axios.post(ENDPOINTS.CREATE + userId, createPlanInfoDto);
    }
}