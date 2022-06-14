import {UserDetails} from "../model/UserDetails";
import {useNavigate} from "react-router-dom";
import UserDetail from "./UserDetail";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

type UserDetailsProps = {
    userDetails: UserDetails | undefined
}
export default function ImportantUserDetails({userDetails}: UserDetailsProps) {

    const token = useContext(AuthContext);
    const navigateThisTime = useNavigate();

    return <button className="head-element ImportantUserDetails" id="active" onClick={() => navigateThisTime('/user-details')}>

            <div>
                {userDetails && token ?
                    <UserDetail key={userDetails.username} detail={userDetails.username}/> :
                    <div>Please</div>}
                {userDetails && token ?
                    <UserDetail key={userDetails.customerId} detail={userDetails.customerId}/> :
                    <div>log in</div>}
            </div>

    </button>
}
