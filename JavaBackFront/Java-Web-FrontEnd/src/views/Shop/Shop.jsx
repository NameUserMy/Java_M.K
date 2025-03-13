import { useContext, useEffect, useState } from "react";
import AppContext from "../../Components/AppContext";
import './shop.css';
import { Link } from "react-router-dom";

export default function Shop() {
    const [categories, setCatigories] = useState([]);
    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=categories")
            .then(setCatigories)
            .catch(console.log);


    }, [])

    return (

        <>
            <h1>Shop</h1>
            <div className='categories' >

                {categories.map(
                    c => <Link to={"/category/"+c.categorySlug} key={c.categoryId} className="category-card">
                        <img  src={c.categoryImageId} alt="logo"  />
                        {c.categoryTitle}
                    </Link>
                )}

            </div>
        </>

    );
}