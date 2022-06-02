import {UserDetails} from "../model/UserDetails";
import useOrganicBox from "../hooks/useOrganicBox";

type UserDetailsProps = {
    userDetails: UserDetails | undefined
}

export default function NewSubscription({ userDetails }: UserDetailsProps ){
    const {subscribeToBox} = useOrganicBox()

    return <div>
        <button onClick={() => subscribeToBox("4711")}> Add {userDetails && userDetails.id} to Box </button>
    </div>
}
