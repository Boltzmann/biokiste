import React from "react";
import "./AppHead.css"
import {useNavigate} from "react-router-dom";

export default function AppHead(){
    const navigate = useNavigate()

    return (
        <header>
            <h1 className="centered" onClick={() => navigate("/")}>Biokiste</h1>
        </header>
    )
}
