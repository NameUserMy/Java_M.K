import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./views/Home/Home";
import Signup from "./views/Signup/Signup";
import Signin from "./views/Signin/Signin";
import "./App.css";
function App() {



  return (
    <Router>

      <Routes>
        <Route path='/' element={<Home/>} />
        <Route path='/signup' element={<Signup/>} />
        <Route path='/signin' element={<Signin/>} />
      </Routes>

    </Router>
  );
}

export default App;
