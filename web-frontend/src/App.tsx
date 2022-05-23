import React from 'react';
import './App.css';
import {ToastContainer} from "react-toastify";
import { Routes, Route } from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import RequireAuth from './routing/RequireAuth';
import MeOverview from './components/MeOverview';
import useUserDetails from "./hooks/useUserDetails";

function App() {
    const {userDetails} = useUserDetails()

    return (
        <div className="App">
            <ToastContainer/>
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path='/'
                           element={<MeOverview userDetails={userDetails}/>}
                    />
                </Route>
                <Route path='/login' element={<LoginPage />}/>
            </Routes>

        </div>
    );
}

export default App;
