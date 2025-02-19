import { useFieldArray, useForm } from "react-hook-form";

import "./signUp.css"
function Signup() {

  const { register, handleSubmit, control, formState: { errors }, watch } = useForm({
    defaultValues: { 
      name: '', 
      phone: '',  
      city: '',
      dofb:'',
      age:'',
      money:'', 
      email: '', 
      login: '', 
      password: '', 
      confirmPassword: '' }
  });

  const { fields, append, remove } = useFieldArray({
    control,
    name: "extraEmail"
  })
//pattern: { value: /^([a-zA-Z]){5,20}$/, message: "min 5 max 20, letters" } 
  register("name", { required: "Name requared"});
  register("phone", { required: "phone   requared" });
  register("dofb", { required: "Date   requared" });
  register("email", { required: "Email requared", pattern: { value: /^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/, message: "example@aaa.com" } });
  register("city", { required: "City  requared", pattern: { value: /^([A-Z]){1}[a-z]{3,20}$/, message: "Example City" } });
  register("login", { required: "Login requared", pattern: { value: /^[a-zA-Z0-9._%±-]{5,20}$/, message: "leters,numbers,(. _ % ± -) min 5 max 20 simbols" } });
  register("password", { required: "Password  requared", pattern: { value: /^[a-zA-Z0-9._%±-]{5,20}$/, message: "leters,numbers,(. _ % ± -) min 5 max 20 simbols" } });
  
  
  register('confirmPassword', {
    required: "Confirm password requared",
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

      <input {...register('phone')} type='text' placeholder='Enter phone *' />
      {errors.phone && <span className="tool-tip">{errors.phone.message}</span>}

      <input {...register('city')} type="text" placeholder="Enter city *" />
      {errors.city && <span className="tool-tip">{errors.city.message}</span>}

      <input {...register('dofb')} type="date" />
      {errors.dofb && <span className="tool-tip">{errors.dofb.message}</span>}

      <input {...register('age')} type="number" placeholder="Enter age" />
     
      <input {...register('money')} type="number" min="0"  step=".01" placeholder="Enter money" />
     

      <div className="email" >
        <input  {...register("email")} type='email' placeholder='Enter email *' />
        {errors.email && <span className="tool-tip">{errors.email.message}</span>}

        <div className="extra-email-container">
          <button title="Add extra email"
            type="button"
            onClick={() => append()}
          > +</button>
          <button title="Remove extra email"
            type="button"
            onClick={() => remove()}
          > - </button>
          <ul className="extra-email-list">
            {fields.map((item, index) => (
              <li key={item.id}>
                <input  {...register(`extraEmail.${index}.extraMail`)} type='email' placeholder='Enter extra email *' />
              </li>
            ))}
          </ul>

        </div>
      </div>

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

export default Signup;
