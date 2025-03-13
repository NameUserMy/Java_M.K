

class DecodeJWT {

    #payLoad;
    #role;
    #exp;
  
    jwtDecode(jwt) {
        const part = jwt.split('.');
        this.#payLoad = JSON.parse(atob(part[1]));
        this.#role = this.#payLoad.roleId;
        this.#exp = this.#payLoad.exp;
    }

    getRole() {
        return this.#role;
    }

    getExp() {

        return this.#exp;
    }


}

export default DecodeJWT;