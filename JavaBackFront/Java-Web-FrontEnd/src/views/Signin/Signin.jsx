import { useContext } from "react";
import { useForm } from "react-hook-form";
import AppContext from "../../AppContext";
import { useNavigate } from "react-router-dom";

function Signin() {

    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: { email: '', password: '' }
    });

    const { setAccessToken,setUser, request } = useContext(AppContext);

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
            setUser(data.user);
            setAccessToken(data.accessToken);
            //setAccessToken(data.jwtToken);
            navigate('/profile');
        }).catch(console.log);
       
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