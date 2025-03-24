import { Link } from "react-router-dom";
import { useContext } from "react";
import AppContext from "../AppContext";
import CartWidget from "../Cart/CartWidget";

const Header = () => {
    const { user, cart } = useContext(AppContext);

    console.log(cart)

    const countProduct = !cart ? 0 : cart.cartItems.reduce((count, cartItm) => count += cartItm.quantity, 0);

    const sum = !cart ? 0 : cart.cartItems.reduce((sum, cartItm) => sum += cartItm.cardItemPrice, 0);


    return (
        <header className="boxShadow" >
            <div className='header-logo'>

                The Super-puper shop

            </div>

            <div className='user-info'>
                <span className="user-name">
                    Hello, {user ? user.name : "You need to login or registration"}.
                </span>

                <CartWidget countProduct={countProduct} sum={sum} />

            </div>

            <div className='header-menu'>
                <Link to="/signup">Sign Up</Link>
                <Link to="/signin">Sign In</Link>
                <Link to="/shop">Shop</Link>
                <Link to="/admin">Admin</Link>
            </div>
        </header>
    );
}

export default Header;