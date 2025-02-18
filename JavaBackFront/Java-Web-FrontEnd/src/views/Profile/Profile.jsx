import { useContext, useState } from "react";
import AppContext from "../../AppContext";

function Profile() {
    const { user, setUser } = useContext(AppContext);

    return (

        <>
            {user == null ? <AnonView /> : <AuthView />}
        </>
    )

}

function AuthView() {


    const { user, setUser, request } = useContext(AppContext);
    const [name, setName] = useState(user.name);
    const [phone, setPhone] = useState(user.phone);
    const saveChange = () => {

        request("/user", {
            method: "PUT",
            headers: {
                'Content-type': "application/json"
            },
            body: JSON.stringify({
                "userId": user.userId,
                name,
                phone
            })
        }).then(data => {
            console.log(data);
            setUser(data);
        }).catch(err => console.log(err));
        console.log(user.userId, name, phone);
    };
    const deleteProfile = () => {

        console.log(user.userId, del)
    };
    return <>
        Name: <input type="text" value={name} onChange={e => setName(e.target.value)} />
        <br />
        Email: {user.email}
        <br />
        Phone: <input type="text" value={phone} onChange={e => setPhone(e.target.value)} />
        <br />
        <button onClick={saveChange} >Save</button>
        <button onClick={deleteProfile} >Delete </button>

    </>

}

function AnonView() {

    return <p>Avtorization</p>
}

export default Profile;