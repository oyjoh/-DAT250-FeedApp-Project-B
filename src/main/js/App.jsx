import './App.css';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import React from "react";
import Login from "./pages/login.jsx"
import Dash from "./pages/Dash.jsx";
import Register from "./pages/Register.jsx"

const App = () => {
  return (
      <Router>
          <Switch>
              <Route exact path='/' component={Login} />
              <Route path="/dash" component={Dash} />
              <Route path="/login" component={Login} />
              <Route path="/register" component={Register} />
          </Switch>
      </Router>
  );
}

export default App;