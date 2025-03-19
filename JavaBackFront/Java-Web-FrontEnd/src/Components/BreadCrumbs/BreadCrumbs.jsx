import { Link, useLocation } from "react-router-dom";

import './breadCrumbs.css'
import { Router } from "../Routing/Router";

let crumbs = [];
const BreadCrumbs = (props) => {

    const location = useLocation();
    const crumbsLink = Router.routes[0].children[4].children.map(p => p.path);
    const index = crumbsLink.findIndex(x => x.includes(location.pathname.split("/")[2]))

    if (crumbs.length == 0) {
        for (let i = 0; i <= index; i++) {
            crumbs.push(<Link to={crumbsLink[i].replace(":id", props.link)}>{crumbsLink[i].split('/')[2]}/</Link>);
        }
    }
    if (index == crumbs.length) {

        crumbs.push(<Link to={crumbsLink[index].replace(":id", props.link)}>{crumbsLink[index].split('/')[2]}/</Link>);

    }
    if (crumbs.length > index + 1) {

        crumbs.pop();
    }
    return (

        <span className="crumbs">
            {crumbs}{props.link}
        </span>

    );
}

export default BreadCrumbs;