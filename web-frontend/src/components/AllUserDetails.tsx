import {UserDetails} from "../model/UserDetails";
import UserDetail from "./UserDetail";

type UserDetailsProps = {
    userDetails: UserDetails | undefined
}
export default function AllUserDetails({userDetails}: UserDetailsProps) {
    return (
        <div id="inactive">
            {userDetails && Object.values(userDetails).map(
                (value) => <UserDetail detail={value}/>
            )}
        </div>
    )
}
