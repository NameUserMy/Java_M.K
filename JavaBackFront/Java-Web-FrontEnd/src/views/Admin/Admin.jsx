import { useEffect, useState, useContext } from "react";
import AppContext from "../../AppContext";

export default function Admin() {

    const [categories, setCatigories] = useState([]);
    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=categories")
            .then(setCatigories)
            .catch(console.log);


    }, [])

    const formSubmit = e => {

        e.preventDefault();
        request("/product",{
                    method: "POST",
                    body: new FormData(e.target)
        
                }).then(console.log)
        .catch(console.error);
        // fetch("http://localhost:8080/server/product", {
        //     method: "POST",
        //     body: new FormData(e.target)

        // }).then(r=>r.text()).then(console.log);

    }

    return (
        <>
            <h1>Admin</h1>
            <form onSubmit={formSubmit} encType="multypart/form-data">
                <input name="product-title" placeholder="Title" />
                <br />
                <input name="product-description" placeholder="description" />
                <br />
                <input name="product-price" type="number" step="0.01" placeholder="price" />
                <br />
                <input name="product-stock" type="number" placeholder="count" />
                <br />
                <input name="product-code" placeholder="code" />
                <br />
                <input name="product-image" type="file" />
                <select name="category-id">
                    {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.categoryTitle}</option>)}
                </select>
                <br />
                <button type="submin"> Add product</button>
            </form>

        </>


    );

}