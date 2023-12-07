import React, { useContext } from "react";
import "./App.css";
import SignIndex from "./pages/SignIndex/SignIndex";
import Login from "./pages/Login/Login";
import {Route, Switch, Redirect } from "react-router-dom";
import Signup from "./pages/Signup/Signup";
import Home from "./pages/Home/Home";
import Explore from "./pages/Explore/Explore";
import Reward from "./pages/Reward/Reward";
import Success from "./pages/Reward/Success";
import Profile from "./pages/Profile/Profile";
import Bookmarks from "./pages/Bookmarks/Bookmarks";
import Messages from "./pages/Messages/Messages";
import Lists from "./pages/Lists/Lists";
import { AuthContext, AuthProvider } from './components/AuthProvider';

const ProtectedRoute = ({ component: Component, ...rest }) => {
  const { accessToken } = useContext(AuthContext);

  return (
    <Route
      {...rest}
      render={props =>
        accessToken ? (
          <Component {...props} />
        ) : (
          <Redirect to="/login" />
        )
      }
    />
  );
};


function App() {
  return (
    <AuthProvider>
      <Switch>
        <Route path="/" component={SignIndex} exact />
        <Route path="/login" component={Login} />
        <Route path="/signup" component={Signup} />
        <Route path="/home" component={Home} />
        <Route path="/explore" component={Explore} />
        <Route path="/reward" component={Reward} />
        <Route path="/reward-success" component={Success} />
        <Route path="/profile" component={Profile} />
        <Route path="/bookmarks" component={Bookmarks} />
        <Route path="/messages" component={Messages} />
        <Route path="/lists" component={Lists} />
      </Switch>
    </AuthProvider>
  );
}

export default App;
