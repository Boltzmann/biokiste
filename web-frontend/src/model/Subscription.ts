import {Item} from "./Item";

export type Subscription = {
    id: String
    name: String
    weekOfYear: number
    size: string
    content: Item[]
}
