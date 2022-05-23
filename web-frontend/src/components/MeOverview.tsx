import {UserDetails} from "../model/UserDetails";

type UserDetailsProps = {
    userDetails: UserDetails|undefined
}

export default function MeOverview({userDetails}: UserDetailsProps){
    return <div>
        <p>{userDetails && userDetails.username},</p>
        <p>Kundennummer: {userDetails && userDetails.customerId}</p>
    </div>
}