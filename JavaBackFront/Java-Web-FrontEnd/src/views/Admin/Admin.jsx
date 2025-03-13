import { useEffect, useState, useContext } from "react";
import AppContext from "../../Components/AppContext";
import './admin.css';
import { Link } from "react-router-dom";


export default function Admin() {
    const {user,role} = useContext(AppContext);
    return (<>{user == null||role!=='admin' ? <AnonView /> : <AuthViewAdmin />}</>);
}


 function AuthViewAdmin() {

    const [categories, setCatigories] = useState([]);
    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=categories")
            .then(setCatigories)
            .catch(console.log);


    }, [])

    const formSubmit = e => {

        e.preventDefault();
        request("/product", {
            method: "POST",
            body: new FormData(e.target)

        }).then(console.log)
            .catch(console.error);
    }

    return (
        <section id='admin-wraper' className="boxShadow">
            <nav id='admin-menu'>

            </nav>
            <section id='admin-content'>
                <form id='addProduct' onSubmit={formSubmit} encType="multypart/form-data">
                    <input name="product-title" placeholder="Title" />

                    <input name="product-description" placeholder="description" />

                    <input name="product-price" type="number" step="0.01" placeholder="price" />

                    <input name="product-stock" type="number" placeholder="count" />

                    <input name="product-code" placeholder="code" />

                    <input name="product-image" type="file" />

                    <select name="category-id">
                        {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.categoryTitle}</option>)}
                    </select>

                    <button type="submin"> Add product</button>
                </form>
            </section>
        </section>
    );

}

function AnonView() {

    return (
        <dl>
            <p>You need to login</p>
            <p><Link to="/signin"><dt>Sign in</dt></Link></p>
        </dl>
    )

}