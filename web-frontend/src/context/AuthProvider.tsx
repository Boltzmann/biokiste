import {createContext, ReactElement, useEffect, useState} from "react";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import { decodeJwt } from "jose";

const AUTH_KEY = "AuthToken"

// ts-ignore
export const AuthContext =
    createContext<{ token: string | undefined, login: (credentials: {username: string, password: string}) => void, logout: () => void}> (
        {token: undefined, login: () => {toast.error("Login not initialized.")}, logout: () =>  {toast.error("Logout not initialized.")}}
)

export type AuthProviderProps = {
    children : ReactElement;
}

export default function AuthProvider({children}: AuthProviderProps){
    const [token, setToken] = useState<string | undefined>(localStorage.getItem(AUTH_KEY) ?? undefined)
    const navigate = useNavigate()

    useEffect(() => {
        checkTokenExpiration()
    })

    const login = (credentials: {username: string, password: string}) => {
        axios.post("/auth/login", credentials)
            .then(response => response.data)
            .then((newToken) => {
                setToken(newToken)
                localStorage.setItem(AUTH_KEY, newToken)
            })
            .then(() => navigate("/"))
            .catch(() => toast.error("Login failed. Credentials invalid?"))
    }

    const logout = () => {
        localStorage.removeItem(AUTH_KEY)
        setToken("")
        toast.info("You are now logged out.")
        navigate("/login")
    }

    const checkTokenExpiration = () => {
        let decodedToken;
        if(token) {
            try {
                decodedToken = decodeJwt(token)
            } catch {
                toast.error("Token is corrupted.")
                logout()
            }
        }
        const dateNow = Math.floor(new Date().getTime() / 1000)
        if(decodedToken && decodedToken.exp && decodedToken.exp < Number(dateNow) ) {
            toast.info("Session expired.")
            logout()
        }
    }

    return <div>
        <AuthContext.Provider value={{token, login, logout}}>
            {children}
        </AuthContext.Provider>
    </div>
}
