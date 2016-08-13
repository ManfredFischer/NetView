import fetch from 'isomorphic-fetch';
import Server from '../../data/constante/Server';

export default function generateFactory(url, method, contentType, bodyData) {
    return fetch(Server.getUrl(url), {
        method: method,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: bodyData
    }).then(res => res.json()).then(json => {
        return json.access_token ? json.access_token : false
    }).catch(err => err)
}