import { useForm } from "react-hook-form";
import './App.css'

function App() {

  const { register, handleSubmit, formState: { errors }, watch } = useForm({
    defaultValues: { name: '', email: '', dob: '',login:'', password: '', confirmPassword: '',city:'' }
  });


  register("name",{ required:"Name requared", pattern: { value: /^([a-zA-Z]){5,20}$/, message: "min 5 max 20, letters" }});
  register("email", { required: "Email requared", pattern: { value: /^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/, message: "example@aaa.com" } });
  register("login", { required: "Login requared", pattern: { value: /^[a-zA-Z0-9._%±-]{5,20}$/, message: "leters,numbers,(. _ % ± -) min 5 max 20 simbols" } });
  register("password", { required: "Password  requared", pattern: { value: /^[a-zA-Z0-9._%±-]{5,20}$/, message: "leters,numbers,(. _ % ± -) min 5 max 20 simbols" } });
  register("city", { required: "City  requared", pattern: { value: /^([A-Z]){1}[a-z]{5,20}$/, message: "Example City" } });
  register("dob", { required: "Date of birthday  requared" });
  register('confirmPassword', {required: "Confirm password requared",
     validate: () => {

      if (watch('password') != watch('confirmPassword')) {
        return "Confirmation password do not match";
      }
    }
  })

  const sendForm = (data) => {
      fetch("http://localhost:8080/server/home", {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      }).then(r => r.json()).then(j => { console.log(j) });
  }
  
  return (
    <form id='formRegistration' onSubmit={handleSubmit(sendForm)}>
      <input {...register("name")} type="text" placeholder='Enter name *' />
      {errors.name && <span className="tool-tip">{errors.name.message}</span>}
      
      <input {...register('dob', { valueAsDate: true })} type='date' placeholder='Enter date of birth *' />
      {errors.dob && <span className="tool-tip">{errors.dob.message}</span>}
      
      <input {...register('city')} type="text" placeholder="Enter city" />
      {errors.city && <span className="tool-tip">{errors.city.message}</span>}
      
      <input  {...register("email")} type='email' placeholder='Enter email *' />
      {errors.email && <span className="tool-tip">{errors.email.message}</span>}
      
      <input  {...register("login")} type='text' placeholder='Enter Login *' />
      {errors.login && <span className="tool-tip">{errors.login.message}</span>}
      
      <input {...register('password')} type='password' placeholder='Enter password *' />
      {errors.password && <span className="tool-tip">{errors.password.message}</span>}
      
      <input {...register('confirmPassword')} type='password' placeholder='Repeat password *' />
      {errors.confirmPassword && <span className="tool-tip">{errors.confirmPassword.message}</span>}
      
      <button>Registration</button>
    </form>
  );
}

export default App;
