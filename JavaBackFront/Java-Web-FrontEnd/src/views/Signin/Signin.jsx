import { useContext } from "react";
import { useForm } from "react-hook-form";
import AppContext from "../../AppContext";
import { useNavigate } from "react-router-dom";

function Signin() {

    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: { email: '', password: '' }
    });

    const { user, setUser, request } = useContext(AppContext);

    const sendForm = (data) => {
        //rfc7617

        let datac = data.email + ':' + data.password;
        let credentials = btoa(datac);
        request('/user', {
            method: "GET",
            headers: {
                'Authorization': 'Basic ' + credentials,
            },
        }).then(data=>{
            setUser(data);
            navigate('/profile');
        }).catch(console.log);



        // fetch("http://localhost:8080/server/user", {
        //     method: "GET",
        //     headers: {
        //         'Authorization': 'Basic ' + credentials,
        //     },
        // }).then(r => r.json()).then(j => {
        //     if (j.status == 200) {

        //         setUser(j.data);
        //         console.log(j)
        //         navigate("/profile");
        //     }
        // });
        // console.log(credentials);
    }


    return (

        <form onSubmit={handleSubmit(sendForm)}>

            <input  {...register("email")} type='email' placeholder='Enter email *' />
            <input {...register('password')} type='password' placeholder='Enter password *' />
            <button>Sign in</button>
        </form>

    );

}


export default Signin;