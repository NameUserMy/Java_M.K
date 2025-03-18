import { useParams } from 'react-router-dom';
import './product.css';
const Product = () => {

    const { id } = useParams;

    return (

        <h1>Product {id}</h1>
    );



}

export default Product;