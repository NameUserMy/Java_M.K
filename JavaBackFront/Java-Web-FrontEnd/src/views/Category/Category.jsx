import { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import AppContext from "../../Components/AppContext";

export default function Categoty() {

    const { id } = useParams();


    const [category, setCatigory] = useState(
        {
            "categoryId": "",
            "categorySlug": id,
            "categoryTitle": "",
            "categoryDescription": "",
            "categoryImageId": "",
            "products": []
        });
    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=category&slug=" + id)
            .then(setCatigory)
            .catch(console.log);


    }, [id])

    return (
        <>
            <h1>Category {id}</h1>
            {category.products.map(p => <div key={p.ProductId}>
                {p.ProductTitle}

            </div>)}
            <Link to="/category/glass">Glass</Link>
        </>

    );
}