export default {
    HOST : 'http://localhost:8080/',
    REST: '',

    getUrl(path){
        return this.HOST + this.REST + path;
    }
}