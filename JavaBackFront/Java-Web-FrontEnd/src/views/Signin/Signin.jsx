import { useForm } from "react-hook-form";

function Signin() {
    const { register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: { email: '', password: '' }
    });



    const sendForm = (data) => {
        //rfc7617

        let datac=data.email + ':' + data.password;
        let credentials=btoa(datac);



        fetch("http://localhost:8080/server/user", {
          method: "GET",
          headers: {
            'Authorization': 'Basic '+ credentials,
          },
        }).then(r => r.json()).then(j => { console.log(j) });
        console.log(credentials);
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