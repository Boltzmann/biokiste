import {FormEvent, useContext, useState} from "react";
import {AuthContext} from "../context/AuthProvider";

type LoginPageProps = {
    verificationId?: string
}

export default function LoginForm({verificationId}: LoginPageProps){
    const [username, setUsername] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const {login, firstLogin} = useContext(AuthContext)

    const onSubmit = (event:FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        verificationId ?
            firstLogin({username: username, password: password, verificationId: verificationId}) :
            login({username: username, password: password})
    }

    return (
        <div>
            <form onSubmit={onSubmit}>
                {verificationId &&
                    <input readOnly type={"text"} value={verificationId} />
                }
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
            <button type={"submit"} id="active">Login</button>
        </form>
    </div>
    )
}
