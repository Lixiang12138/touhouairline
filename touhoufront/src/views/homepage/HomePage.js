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
import { Button } from 'office-ui-fabric-react';

import '../../App.css';

class HomePage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "123",
      password: "132",
      loginStatus: false
    };
  }

  gotoHomePage() {
    axios.post('home', {
      params: {
        id: "pwd"
      }
    })
      .then(function (response) {
        console.log(response);
        console.log(response.data);
      })
  }

  handleClick = () => {
    const pwd = this.state.password;
    axios.post('home', {
      params: {
        id: pwd
      }
    })
      .then(function (response) {
        console.log(response);
        console.log(response.data);
      })
  }

  render() {
    return (
      <div>
        <Button
          text="首页"
          ariaDescription="前往首页"
          onClick={this.handleClick}
        />
        <Button
          text="GayHub"
          ariaDescription="前往GayHub"
          onClick={this.gotoHomePage}
        />
      </div>
    );
  }
}

export default HomePage;
