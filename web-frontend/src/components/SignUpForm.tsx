import {FormEvent, useState} from "react";
import useVerification from "../hooks/useVerification";

export default function SignUpForm(){

    const startVerificationProzess = useVerification()
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [email, setEmail] = useState<string>("")

    const onSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        startVerificationProzess({
            "username": username,
            "email": email,
            "password": password
        })
    }

    return <div>
        <form onSubmit={onSubmit}>
            <input type={"text"}
                   value={username}
                   placeholder={"username"}
                   onChange={(event) => setUsername(event.target.value) }
            />
            <input type={"password"}
                   value={password}
                   placeholder={"Password"}
                   onChange={(event) => setPassword(event.target.value)}
            />
            <input type={"email"}
                   value={email}
                   placeholder={"E-Mail"}
                   onChange={(event) => setEmail(event.target.value)}
            />
            <button type={"submit"} id="active">Sign up</button>
        </form>
    </div>
}
