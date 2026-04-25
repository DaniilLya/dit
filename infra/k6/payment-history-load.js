import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8081';
const DURATION = __ENV.DURATION || '1m';

export const options = {
    scenarios: {
        import_payment_write: {
            executor: 'constant-arrival-rate',
            exec: 'importPayment',
            rate: 200,
            timeUnit: '1s',
            duration: DURATION,
            preAllocatedVUs: 100,
            maxVUs: 400,
        },
        get_latest_payments_read: {
            executor: 'constant-arrival-rate',
            exec: 'getLatestPayments',
            rate: 200,
            timeUnit: '1s',
            duration: DURATION,
            preAllocatedVUs: 100,
            maxVUs: 400,
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.01'],
        'http_req_duration{scenario:import_payment_write}': ['p(95)<2000'],
        'http_req_duration{scenario:get_latest_payments_read}': ['p(95)<1000'],
        'checks{scenario:import_payment_write}': ['rate>0.99'],
        'checks{scenario:get_latest_payments_read}': ['rate>0.99'],
    },
};

export function importPayment() {
    const response = http.post(`${BASE_URL}/api/v1/payment`, null, {
        headers: {
            Accept: 'application/json',
        },
        tags: {
            endpoint: 'import-payment',
        },
    });

    check(response, {
        'POST /api/v1/payment status is 200': (r) => r.status === 200,
        'POST /api/v1/payment returns json': (r) =>
            (r.headers['Content-Type'] || '').includes('application/json'),
    });
}

export function getLatestPayments() {
    const response = http.get(`${BASE_URL}/api/v1/payment`, {
        headers: {
            Accept: 'application/json',
        },
        tags: {
            endpoint: 'get-latest-payments',
        },
    });

    let payload = [];
    try {
        payload = response.json();
    } catch (_) {
        payload = [];
    }

    check(response, {
        'GET /api/v1/payment status is 200': (r) => r.status === 200,
        'GET /api/v1/payment returns array': () => Array.isArray(payload),
        'GET /api/v1/payment returns up to 10 records': () => payload.length <= 10,
    });
}
