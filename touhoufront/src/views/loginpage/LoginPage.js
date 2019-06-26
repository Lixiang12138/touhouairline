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
import {
  TextField, PrimaryButton
} from 'office-ui-fabric-react';

import '../../App.css';

class LoginPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: ""
    };
  }


  handleInputChange = (name, value) => {
    console.log(value);
    this.setState({
      [name]: value
    });
  }

  handleClick = () => {
    const state = this.state;
    const usn = this.state.username;
    const pwd = this.state.password;
    const props = this.props;
    axios.post('login', {
      username: usn,
      password: pwd
    })
      .then(function (response) {
        let data = response.data.result;
        if (data.success == true) {
          props.changeLoginStatus(true, data.object.userName, data.object.nickName);
        }
      })
  }

  render() {
    return (
      <div className="App">
        <div>
          <TextField label="用户名" onChange={e => this.handleInputChange('username', e.target.value)} />
          <TextField label="密码" onChange={e => this.handleInputChange('password', e.target.value)} />
          <PrimaryButton onClick={this.handleClick}>登录</PrimaryButton>
        </div>
      </div>
    );
  }
}

export default LoginPage;
