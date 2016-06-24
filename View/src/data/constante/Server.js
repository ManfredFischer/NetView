export default {
    HOST : '',
    REST: '',

    getUrl(path){
        return this.HOST + this.REST + path;
    }
}