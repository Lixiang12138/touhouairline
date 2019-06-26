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
      password: "",
      errorMessage: ""
    };
  }


  handleInputChange = (name, value) => {
    console.log(value);
    this.setState({
      [name]: value
    });
  }

  handleClick = () => {
    const usn = this.state.username;
    const pwd = this.state.password;
    const _this = this;
    const props = this.props;
    axios.post('login', {
      username: usn,
      password: pwd
    })
      .then(function (response) {
        let data = response.data.result;

        if (data.success == true) {
          props.changeLoginStatus(true, data.object.userName, data.object.nickName);
          _this.setState({errorMessage:""});
        } else {
          _this.setState({errorMessage:"用户名或密码错误！"});
        }
      })
  }

  render() {
    return (
      <div style={{textAlign:"center",marginLeft:"30%",marginRight:"30%"}}>
        <div>
          <TextField label="用户名" onChange={e => this.handleInputChange('username', e.target.value)} />
          <TextField label="密码" type="password" onChange={e => this.handleInputChange('password', e.target.value)} />
          <PrimaryButton style={{marginTop:"16px"}} onClick={this.handleClick}>登录</PrimaryButton>
        </div>
        <div style={{color:"red"}}>{this.state.errorMessage}</div>
      </div>
    );
  }
}

export default LoginPage;
