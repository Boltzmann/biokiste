import {UserDetails} from "../model/UserDetails";
import MeShortDetails from "./MeShortDetails";

type UserDetailsProps = {
    userDetails: UserDetails|undefined
}

export default function MeAllDetails({userDetails}: UserDetailsProps){
    return (
        <div>
            <MeShortDetails userDetails={userDetails}/>
        </div>
    )
}
