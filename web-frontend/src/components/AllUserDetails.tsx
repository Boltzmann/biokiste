import {UserDetails} from "../model/UserDetails";
import UserDetail from "./UserDetail";
import {useContext} from "react";
import {AuthContext} from "../context/AuthProvider";

type UserDetailsProps = {
    userDetails: UserDetails | undefined
}
export default function AllUserDetails({userDetails}: UserDetailsProps) {
    const {logout} = useContext(AuthContext)

    return (
        <div>
        <div id="inactive">
            {userDetails && Object.values(userDetails).map(
                (value) => {return typeof value === 'string' ? <UserDetail detail={value}/> : <div/>}
            )}
        </div>
            <div id="active" onClick={() => logout()}>logout</div>
        </div>
    )
}
