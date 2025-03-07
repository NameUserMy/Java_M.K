import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState } from "react";
import Home from "./views/Home/Home";
import Signup from "./views/Signup/Signup";
import Signin from "./views/Signin/Signin";
import AppContext from "./AppContext";
import Profile from "./views/Profile/Profile";


function App() {

  const [user, setUser] = useState(null);
  const [accessToken, setAccessToken] = useState(null);


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


  return <AppContext.Provider value={{ user, setUser, request, accessToken, setAccessToken }}>

    <Router>

      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/profile' element={<Profile />}></Route>
        <Route path='/signup' element={<Signup />} />
        <Route path='/signin' element={<Signin />} />
      </Routes>

    </Router>

  </AppContext.Provider>


}



export default App;
