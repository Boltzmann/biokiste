import React from 'react';
import './App.css';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {Routes, Route} from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import RequireAuth from './routing/RequireAuth';
import useUserDetails from "./hooks/useUserDetails";
import '@ionic/react/css/core.css';
import AppHead from "./components/AppHead";
import AllUserDetails from "./components/AllUserDetails";
import OverviewPage from "./pages/OverviewPage";



function App() {
    const {userDetails} = useUserDetails()

    return (
        <div className="App">
            <ToastContainer/>
            <AppHead/>
            <div className="AllButHeader">
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path='/'
                           element={<OverviewPage />}
                    />
                </Route>
                <Route path='/login' element={<LoginPage />}/>
                <Route path='/user-details' element={<AllUserDetails userDetails={userDetails}/>}/>
            </Routes>
            </div>

        </div>
    );
}

export default App;
