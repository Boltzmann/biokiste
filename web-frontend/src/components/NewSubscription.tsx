import {UserDetails} from "../model/UserDetails";

type NewSubscriptionProps = {
    userDetails: UserDetails | undefined
    subscribeToBox: (boxId: string) => void
}

export default function NewSubscription({ userDetails, subscribeToBox }: NewSubscriptionProps ){

    return <div>
        <button onClick={() => subscribeToBox("4711")}> Add {userDetails && userDetails.id} to Box 4711</button>
    </div>
}
