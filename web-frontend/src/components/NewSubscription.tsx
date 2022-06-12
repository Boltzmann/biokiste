import {SubscriptionOverviewDto} from "../dto/SubscriptionOverviewDto";
import {AiOutlinePlus} from "react-icons/ai";

type NewSubscriptionProps = {
    subscribeToBox: (boxId: string) => void
    subscribables: SubscriptionOverviewDto[] | undefined
}

export default function NewSubscription({ subscribeToBox, subscribables}: NewSubscriptionProps ) {

    return <div>
        {subscribables &&
            subscribables.map(
                subsls =>
                    <div key={subsls.id}>
                        <button id="active" onClick={
                            () => subscribeToBox(subsls.id)
                        }><AiOutlinePlus/>
                        </button>
                        <div>{subsls.name}</div>
                    </div>)}
    </div>
}
