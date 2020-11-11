import './App.css';
import {Route, BrowserRouter as Router} from "react-router-dom";
import React from "react";
import Login from "./pages/login.jsx"
import Dash from "./pages/Dash.jsx";

const App = () => {
  return (
      <Router>
              <Route path="/dash" component={Dash} />
              <Route path="/login" component={Login} />
      </Router>
  );
}

export default App;