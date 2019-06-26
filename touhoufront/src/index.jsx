/* eslint-disable */
import React from 'react';
import ReactDOM from 'react-dom';
import { Route, Switch, BrowserRouter as Router } from 'react-router-dom';

import App from './App';
import './index.css';
import HomePage from './views/homepage/HomePage';


ReactDOM.render(
  (
    <Router>
      <App />
    </Router>
  ),
  document.getElementById('root')
);
