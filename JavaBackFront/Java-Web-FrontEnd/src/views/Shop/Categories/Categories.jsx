import { useContext, useEffect, useState } from 'react';
import AppContext from '../../../Components/AppContext';
import { Link } from 'react-router-dom';
import './categories.css';



const Categories = () => {

    const [categories, setCatigories] = useState([]);
    const { request } = useContext(AppContext);

    useEffect(() => {

        request("/product?type=categories")
            .then(setCatigories)
            .catch(console.log);


    }, [])

    return (

        <div className='categories' >

            {categories.map(
                c => <Link to={"/shop/category/" + c.categorySlug} key={c.categoryId} className="category-card">
                    <img src={c.categoryImageId} alt="logo" />
                    {c.categoryTitle}
                </Link>
            )}

        </div>
    );

}

export default Categories;