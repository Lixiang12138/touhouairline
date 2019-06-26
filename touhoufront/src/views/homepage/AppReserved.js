/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
  AxiosProvider,
  Request,
  Get,
  Delete,
  Head,
  Post,
  Put,
  Patch,
  withAxios
} from 'react-axios';
import logo from './logo.svg';
import './App.css';

class AppReserved extends React.Component {
  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          3 To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <div>
          <Get url="test" params={{ id: "12345", dd: "12345" }}>
            {(error, response, isLoading, onReload) => {
              if (error) {
                return (<div>Something bad happened: {error.message} <button onClick={() => onReload({ params: { reload: true } })}>Retry</button></div>)
              }
              else if (isLoading) {
                return (<div>Loading...</div>)
              }
              else if (response !== null) {
                console.log(response);
                return (<div>{response.data.result}<button onClick={() => onReload({ params: { refresh: true } })}>Refresh</button></div>)
              }
              return (<div>Default message before request is made.</div>)
            }}
          </Get>
        </div>
      </div>
    );
  }
}

export default App;
