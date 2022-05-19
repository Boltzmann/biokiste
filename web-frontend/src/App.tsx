import React from 'react';
import './App.css';
import {ToastContainer} from "react-toastify";
import { Routes, Route } from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import RequireAuth from './routing/RequireAuth';
import BoxOverview from './components/BoxOverview';

function App() {
    return (
        <div className="App">
            <ToastContainer/>
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path='/'
                           element={<BoxOverview/>}/>
                </Route>
                <Route path='/login' element={<LoginPage />}/>
            </Routes>

        </div>
    );
}

export default App;
