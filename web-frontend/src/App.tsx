import React from 'react';
import './App.css';
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import {Route, Routes} from 'react-router-dom';
import LoginPage from "./pages/LoginPage";
import RequireAuth from './routing/RequireAuth';
import '@ionic/react/css/core.css';
import AppHead from "./components/AppHead";
import AllUserDetails from "./components/AllUserDetails";
import OverviewPage from "./pages/OverviewPage";
import BoxDetailsPage from "./pages/BoxDetailsPage";
import useUserDetailsBoxesAndBoxItems from "./hooks/useUserDetailsBoxesAndBoxItems";


function App() {
    const {
        subscriptions, userDetails,
        subscribeToBox, boxItems,
        getBoxItems, subscribables,
        removeFromSubscriptionOnce, items,
        addNewItem, addItemToBox,
        removeItemFromBox, changeItemName
    } = useUserDetailsBoxesAndBoxItems()

    return (
        <div className="App">
            <ToastContainer/>
            <AppHead/>
            <div className="AllButHeader">
            <Routes>
                <Route element={<RequireAuth/>}>
                    <Route path='/'
                           element={<OverviewPage
                               subscribeToBox={subscribeToBox}
                               subscriptions={subscriptions}
                               removeFromSubscriptionOnce={removeFromSubscriptionOnce}
                               userDetails={userDetails}
                               subscribables={subscribables}
                           />}
                    />
                    <Route path='/user-details' element={<AllUserDetails userDetails={userDetails}/>}/>
                    <Route path={'/box/:id'}
                           element={<BoxDetailsPage
                               boxItems={boxItems}
                               getBoxItems={getBoxItems}
                               items = {items}
                               subscriptions = {subscriptions}
                               addNewItem = {addNewItem}
                               addItemToBox={addItemToBox}
                               removeItemFromBox={removeItemFromBox}
                               changeItemName={changeItemName}
                           />}
                    />
                </Route>
                <Route path='/login' element={<LoginPage />}/>
                </Routes>
            </div>

        </div>
    );
}

export default App;
