import { useParams } from 'react-router-dom';
import './product.css';
import BreadCrumbs from '../../../Components/BreadCrumbs/BreadCrumbs';
const Product = () => {

    const { id } = useParams();
    return (
        <>
            <BreadCrumbs link={id}/>
            <h1>Product {id}</h1>
        </>

    );



}

export default Product;