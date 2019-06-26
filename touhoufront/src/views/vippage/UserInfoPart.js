/* eslint-disable */
import React from 'react';
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
      phone: user.phone,
      nickName: user.nickName
    };
  }

  handleInputChange = (name, value) => {
    this.setState({
      [name]: value
    });
  }

  changeUser = () => {
    const userEntity = {
      userName: this.state.userName,
      password: this.state.password,
      email: this.state.email,
      userPhone: this.state.phone,
      nickName: this.state.nickName
    };
    const props = this.props;
    const _this = this;

    axios.post('changeUserInfo', userEntity)
      .then(function (response) {
        const data = response.data.result;
        if (data.success == true) {
          _this.setState({
            showMessage: false,
            errorMessage: ""
          });
          props.success();
          alert("注册成功！请前往登录");
        } else {
          _this.setState({
            showMessage: true,
            errorMessage: data.message
          });
        }
      })
  }

  render() {
    const user = this.props.user;
    const userInfo = this.state.showChangeInfo
      ? (
        <Content>
          <TextField label="用户名(不可更改)" value={this.props.user.userName} disabled={true} />
          <TextField label="昵称" value={this.props.user.nickName}
            onChange={e => this.handleInputChange('nickName', e.target.value)} />
          <TextField label="密码"
            onChange={e => this.handleInputChange('password', e.target.value)} />
          <TextField label="再次输入密码"
            onChange={e => this.handleInputChange('passwordAgain', e.target.value)} />
          <TextField label="电子邮箱" value={this.props.user.email}
            onChange={e => this.handleInputChange('email', e.target.value)} />
          <TextField label="电话" value={this.props.user.userPhone}
            onChange={e => this.handleInputChange('userPhone', e.target.value)} />
          <PrimaryButton style={{ marginTop: '16px' }} onClick={this.changeUser()}>提交</PrimaryButton>
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
