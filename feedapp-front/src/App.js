import './App.css';
import {Route, BrowserRouter as Router} from "react-router-dom";
import React from "react";
import Login from "./pages/login";
import Dash from "./pages/Dash";

function App() {
  return (
      <Router>
              <Route path="/dash" component={Dash} />
              <Route path="/login" component={Login} />
      </Router>
  );
}

export default App;
