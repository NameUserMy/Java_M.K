
import { useContext, useEffect, useState } from 'react';
import './shop.css';
import { Link, Outlet } from "react-router-dom";
import AppContext from '../../Components/AppContext';

export default function Shop() {
    const [categories, setCatigories] = useState([]);

    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=categories")
            .then(setCatigories)
            .catch(console.log);


    }, [])

    return (

        <section id='shop-wraper' className="boxShadow">
            <nav id='shop-menu'>
                <dl>
                    <Link to="/shop/categories"><dt>All categories</dt></Link>

                    {categories.map(
                        c => <Link to={"/shop/category/" + c.categorySlug} key={c.categoryId} >
                            <dd>{c.categoryTitle}</dd>
                        </Link>
                    )}
                </dl>
            </nav>
            <section id='shop-content'>

                <Outlet />

            </section>
        </section>
    );
}