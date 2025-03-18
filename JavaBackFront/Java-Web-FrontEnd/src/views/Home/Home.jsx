import { Outlet } from "react-router-dom";
import { useState } from "react";
import "./home.css";
import Header from "../../Components/Header/Header";
import AppContext from "../../Components/AppContext";
export default function Home() {

    const [user, setUser] = useState(null);
    const [cart, setCart] = useState(null);
    const [accessToken, setAccessToken] = useState(null);
    const [role, setRole] = useState(null);

    const request = (url, conf) => new Promise((resolve, reject) => {
        const backHost = "http://localhost:8080/server";
        //если токен есть в контексте, а в параметрах conf нету, то добовляем из контекста 

        if (accessToken != null /*&&  typeof(accessToken.accessTokenId)!= 'undefined'*/) {


            if (!conf) {

                conf = {};
            }
            if (!conf.headers) {

                conf.headers = {};
            }

            if (!conf.headers["Authorization"]) {

                conf.headers["Authorization"] = "Bearer " + accessToken;
            }

        }
        fetch(backHost + url, conf)
            .then(r => r.json())
            .then(j => {
                if (j.status < 300) {
                    resolve(j.data);
                } else {
                    reject(j);
                }
            })
            .catch(reject);
    });

    return (

        <AppContext.Provider value={{ user, setUser, role, cart, setCart,setRole, request, accessToken, setAccessToken }} >
            <Header />
            <Outlet />
        </AppContext.Provider>
    );

}


