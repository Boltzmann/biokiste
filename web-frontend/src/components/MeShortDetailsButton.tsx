import {UserDetails} from "../model/UserDetails";
import {useNavigate} from "react-router-dom";
import MeShortDetails from "./MeShortDetails";

type UserDetailsProps = {
    userDetails: UserDetails|undefined
}

export default function MeShortDetailsButton({userDetails}: UserDetailsProps) {
    const navigate = useNavigate();

    return <div>
        <button onClick={() => navigate('/user-details')}>
            <MeShortDetails userDetails={userDetails}/>
        </button>
    </div>
}
