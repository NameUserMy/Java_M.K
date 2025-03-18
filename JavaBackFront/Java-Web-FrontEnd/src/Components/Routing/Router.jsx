import { createBrowserRouter } from "react-router-dom";
import Home from "../../views/Home/Home";
import Signup from "../../views/Signup/Signup";
import Signin from "../../views/Signin/Signin";
import Profile from "../../views/Profile/Profile";
import Admin from "../../views/Admin/Admin";
import Categoty from "../../views/Shop/Category/Category";
import Shop from "../../views/Shop/Shop";
import Categories from "../../views/Shop/Categories/Categories";
import Product from "../../views/Shop/Product/Product";


export const Router = createBrowserRouter([

    { path: '/', element: <Home />,
        children:[
            { path:'/admin',element:<Admin />},
            { path:'/signup',element:<Signup />},
            { path:'/signin',element:<Signin />},
            { path:'/profile',element:<Profile />},
            { path:'/shop',element:<Shop />,

                children:[
                    { path:'/shop/categories',element:<Categories />},
                    { path:'/shop/category/:id',element:<Categoty />},
                    { path:'/shop/product/:id',element:<Product />}
                ]
            }

        ]
        
     }
    

]);
