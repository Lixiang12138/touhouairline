/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
  Button, Row, Col, Layout, Menu, Icon, Descriptions, Input
} from 'antd';
const { SubMenu } = Menu;
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';
import { TextField, PrimaryButton } from 'office-ui-fabric-react';

export default class UserInfoPart extends React.Component {
  constructor(props) {
    super(props);
    const user = this.props.user;
    console.log(user);
    this.state = {
      showChangeInfo: false,
      userName: user.userName,
      password: user.password,
      passwordAgain: user.password,
      email: user.email,
      userPhone: user.userPhone,
      nickName: user.nickName
    };
  }

  handleInputChange = (name, value) => {
    this.setState({
      [name]: value
    });
  }

  changeUser = () => {
    let userEntity = this.props.user;
    userEntity.nickName = this.state.nickName;
    userEntity.password = (this.state.password==this.state.passwordAgain)
      ? this.state.password
      : this.props.user.password;
    userEntity.email = this.state.email;
    userEntity.userPhone = this.state.userPhone;

    const _this = this;

    axios.post('change_user_info', {user:userEntity})
      .then(function (response) {
        const data = response.data.result;
        if (data.success == true) {
          _this.props.changeLoginStatus(true, userEntity)
        } else {
          _this.setState({showChangeInfo:false})
        }
      })
  }

  render() {
    const user = this.props.user;
    const userInfo = this.state.showChangeInfo
      ? (
        <Content>
          <TextField label="用户名(不可更改)" value={this.state.userName} disabled={true} />
          <TextField label="昵称" value={this.state.nickName}
            onChange={e => this.handleInputChange('nickName', e.target.value)} />
          <TextField label="密码"
            onChange={e => this.handleInputChange('password', e.target.value)} />
          <TextField label="再次输入密码"
            onChange={e => this.handleInputChange('passwordAgain', e.target.value)} />
          <TextField label="电子邮箱" value={this.state.email}
            onChange={e => this.handleInputChange('email', e.target.value)} />
          <TextField label="电话" value={this.state.userPhone}
            onChange={e => this.handleInputChange('userPhone', e.target.value)} />
          <PrimaryButton style={{ marginTop: '16px' }} onClick={() => this.changeUser()}>提交</PrimaryButton>
        </Content>
      ) : (
        <Content>
          <Descriptions style={{ marginLeft: "10%" }} title="账户总览" column={1}>
            <Descriptions.Item label="用户名">{user.userName}</Descriptions.Item>
            <Descriptions.Item label="昵称">{user.nickName}</Descriptions.Item>
            <Descriptions.Item label="电子邮箱">{user.email}</Descriptions.Item>
            <Descriptions.Item label="电话">{user.userPhone}</Descriptions.Item>
          </Descriptions>
          <PrimaryButton style={{ marginTop: '16px' }}
            onClick={() => { this.setState({ showChangeInfo: true }) }}
          >修改个人信息</PrimaryButton>
        </Content>
      );
    return (
      <Layout>
        {userInfo}
      </Layout>
    );
  }
}
