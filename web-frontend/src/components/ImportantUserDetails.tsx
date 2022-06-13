import {UserDetails} from "../model/UserDetails";
import {useNavigate} from "react-router-dom";
import UserDetail from "./UserDetail";

type UserDetailsProps = {
    userDetails: UserDetails | undefined
}
export default function ImportantUserDetails({userDetails}: UserDetailsProps) {

    const navigateThisTime = useNavigate();

    return <button className="head-element ImportantUserDetails" id="active" onClick={() => navigateThisTime('/user-details')}>
        {userDetails && <UserDetail detail={userDetails.username}/>}
        {userDetails && <UserDetail detail={userDetails.customerId}/>}
    </button>
}
