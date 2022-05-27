import React, {useContext} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../context/AuthProvider";

export default function AppHead(){

    const navigate = useNavigate()
    const {token} = useContext(AuthContext)

    return (
        <header>
            <button onClick={() => navigate("/")}><h1>Biokiste</h1></button>

            {token ? <div>Eingeloggt.</div> : <div>Bitte einloggen.</div>}
        </header>
    )
}
