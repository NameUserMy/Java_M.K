import { createBrowserRouter } from "react-router-dom";
import Home from "../../views/Home/Home";
import Signup from "../../views/Signup/Signup";
import Signin from "../../views/Signin/Signin";
import Profile from "../../views/Profile/Profile";
import Admin from "../../views/Admin/Admin";
import Categoty from "../../views/Category/Category";
import Shop from "../../views/Shop/Shop";


export const Router = createBrowserRouter([

    { path: '/', element: <Home />,
        children:[
            { path:'/admin',element:<Admin />},
            { path:'/signup',element:<Signup />},
            { path:'/signin',element:<Signin />},
            { path:'/profile',element:<Profile />},
            { path:'/category/:id',element:<Categoty />},
            { path:'/shop',element:<Shop />}

        ]
        
     }
    

]);
