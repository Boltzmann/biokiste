import React from 'react';
import './App.css';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {Routes, Route} from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import RequireAuth from './routing/RequireAuth';
import useUserDetails from "./hooks/useUserDetails";
import '@ionic/react/css/core.css';
import MeAllDetails from "./components/MeAllDetails";
import MeShortDetailsButton from "./components/MeShortDetailsButton";
import AppHead from "./components/AppHead";



function App() {
    const {userDetails} = useUserDetails()

    return (
        <div className="App">
            <ToastContainer/>
            <AppHead/>
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path='/'
                           element={<MeShortDetailsButton userDetails={userDetails}/>}
                    />
                </Route>
                <Route path='/login' element={<LoginPage />}/>
                <Route path='/user-details' element={<MeAllDetails userDetails={userDetails}/>}/>
            </Routes>

        </div>
    );
}

export default App;
