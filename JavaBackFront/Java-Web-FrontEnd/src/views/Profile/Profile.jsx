import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import AppContext from "../../AppContext";
import { useForm } from "react-hook-form";
import "./profile.css";

function Profile() {
    const { user, setUser } = useContext(AppContext);
    return (<>{user == null ? <AnonView /> : <AuthView />}</>);
}

function AuthView() {

    const { user, setUser, request } = useContext(AppContext);

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            userId: user.userId,
            name: user.name,
            phone: user.phone,
            city: user.city,
            dofb: user.dofb,
            age: user.age,
            money: user.money
        }
    });
    const saveChange = (formData) => {

        //console.log("AA", formData)
         request("/user", {
            method: "PUT",
            headers: {
                'Content-type': "application/json"
            },
            body: JSON.stringify(formData)
        }).then(data => {
            console.log(data);
            setUser(data);
        }).catch(err => console.log(err));
       // console.log(user.userId, formData.name, formData.phone);
    };
    const deleteProfile = () => {

        console.log(user.userId, del)
    };
    return (
        <>
            <div id='header-profile'>
                <span className='user-info'>
                    Your Email: {user.email}
                </span>
                <br />
                <span className='user-info'>
                    Your Name: {user.name}
                </span>
                <br />
                <span className='user-info'>
                   Your birthday:  {user.dofb}
                </span>


            </div>
            <div id='content-profile'>
                <form id='form-change-profile' onSubmit={handleSubmit(saveChange)} >

                    <label label htmlFor="username">Name</label>
                    <input {...register("name")} id="username" type="text" placeholder='Enter name *' />
                    {errors.name && <span className="tool-tip">{errors.name.message}</span>}

                    <label label htmlFor="phone">Phone</label>
                    <input {...register('phone')} id="phone" type='text' placeholder='Enter phone *' />
                    {errors.phone && <span className="tool-tip">{errors.phone.message}</span>}

                    <label label for="city">City</label>
                    <input {...register('city')} id="city" type="text" placeholder="Enter city *" />
                    {errors.city && <span className="tool-tip">{errors.city.message}</span>}

                    <label label for="DateOfBirthday">Date of birthday</label>
                    <input {...register('dofb',{valueAsDate: true,})} id="DateOfBirthday" type="date" />
                    {errors.dofb && <span className="tool-tip">{errors.dofb.message}</span>}

                    <label label for="age">Age</label>
                    <input {...register('age')} id="age" type="number" placeholder="Enter age" />

                    <label label for="money">Money</label>
                    <input {...register('money')} id="money" type="number" min="0" step=".01" placeholder="Enter money" />

                    <button  >Save</button>  <button onClick={deleteProfile} >Delete </button>
                </form>

            </div>

        </>
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