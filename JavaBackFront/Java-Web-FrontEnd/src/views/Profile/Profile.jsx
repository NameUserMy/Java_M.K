import { useContext} from "react";
import { Link } from "react-router-dom";
import AppContext from "../../Components/AppContext";
import { useForm } from "react-hook-form";
import "./profile.css";

function Profile() {
    const {user,role} = useContext(AppContext);
    return (<>{user == null || role!=='guest' ? <AnonView /> : <AuthView />}</>);
}

function AuthView() {

    const { user, setUser, request,setAccessToken,accessToken} = useContext(AppContext);

//accessToken
  
    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            userId: user.userId,
            name: user.name,
            phone: user.phone,
            city: user.city,
            dofb: user.dofb,
            age: user.age,
            money: user.money,
            email: user.email
        }
    });

    register("email", { pattern: { value: /^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$/, message: "example@aaa.com" } });




    const saveChange = (formData) => {
        request("/user", {
            method: "PUT",
            headers: {
                'Content-type': "application/json"
            },
            body: JSON.stringify(formData)
        }).then(data => {
          
            setUser(data);
        }).catch(err => console.log(err));

    };
    const deleteProfile = () => {

        if (confirm("Таки да?"))
            request("/user?id=" + user.userId, {
                method: "DELETE",
            }).then(data => {
                console.log(data);
                setUser(null);
                setAccessToken(null);
            }).catch(err => console.log(err));
        console.log(user.userId, "DEL")
    };
    return (
      
          
            <div id='content-profile'>
                <form id='form-change-profile' onSubmit={handleSubmit(saveChange)} >
                    <div>
                        <label htmlFor="username">Name</label>
                        <input {...register("name")} id="username" type="text" placeholder='Enter name *' />
                        {errors.name && <span className="tool-tip">{errors.name.message}</span>}
                    </div>

                    <div>
                        <label htmlFor="phone">Phone</label>
                        <input {...register('phone')} id="phone" type='text' placeholder='Enter phone *' />
                        {errors.phone && <span className="tool-tip">{errors.phone.message}</span>}
                    </div>

                    <div>
                        <label htmlFor="city">City</label>
                        <input {...register('city')} id="city" type="text" placeholder="Enter city *" />
                        {errors.city && <span className="tool-tip">{errors.city.message}</span>}

                    </div>

                    <div>
                        <label htmlFor="DateOfBirthday">Date of birthday</label>
                        <input {...register('dofb', { valueAsDate: true, })} id="DateOfBirthday" type="date" />
                        {errors.dofb && <span className="tool-tip">{errors.dofb.message}</span>}
                    </div>

                    <div>
                        <label htmlFor="age">Age</label>
                        <input {...register('age')} id="age" type="number" placeholder="Enter age" />
                    </div>

                    <div>
                        <label htmlFor="money">Money</label>
                        <input {...register('money')} id="money" type="number" min="0" step=".01" placeholder="Enter money" />
                    </div>

                    <div>
                        <label htmlFor="email" >Email</label>
                        <input  {...register("email")} id="email" type='email' placeholder='Enter email *' />
                        {errors.email && <span className="tool-tip">{errors.email.message}</span>}
                    </div>

                    <button  >Save</button>  <button onClick={deleteProfile} >Delete </button>

                </form>

            </div>

        
    )
}

function AnonView() {

    return (
        <dl>
            <p>You need to login</p>
            <p><Link to="/signin"><dt>Sign in</dt></Link></p>
        </dl>
    )

}

export default Profile;