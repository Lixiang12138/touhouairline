/* eslint-disable */
import React from 'react';
import {
  Link
} from 'react-router-dom';
import {
  Button, Row, Col, Layout, Menu
} from 'antd';
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';

import LoginPage from './views/loginpage/LoginPage';
import RegisterPage from './views/loginpage/RegisterPage';
import './App.css';

const pageList = {
  register: 'register',
  home: 'home',
  login: 'login'
}

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userName: "",
      nickName: "",
      loginStatus: false,
      pageName: pageList.home,
      showLoginPart: false
    };
  }

  getPage() {
    switch (this.state.pageName) {
      case pageList.register:
        return (<RegisterPage />);
    }
  }

  changeLoginStatus(val, usr, nic) {
    this.setState({
      loginStatus: val,
      userName: usr,
      nickName: nic
    });
  }

  gotoLogin() {
    this.setState({
      showLoginPart: true,
      pageName: pageList.login
    });
  }

  hideLogin() {
    this.setState({
      showLoginPart: false
    })
  }

  checkout() {
    this.setState({
      userName: "",
      nickName: "",
      loginStatus: false,
      showLoginPart: false
    })
  }

  render() {
    let TitleLoginStyle = {
      fontSize: '12px',
      display: 'inline',
      listStyle: 'none'
    }

    const loginTitle = this.state.loginStatus
      ? (
        <div>尊敬的{this.state.nickName},欢迎您登录！</div>
      )
      : (
        <ul style={TitleLoginStyle}>
          <li style={TitleLoginStyle} onClick={() => this.setState({ pageName: pageList.register })} success={()=>this.gotoLogin()}>注册</li>
          <li style={TitleLoginStyle} onClick={() => this.gotoLogin()}>|登录</li>
        </ul>
      );

    const loginPart = !this.state.loginStatus && this.state.showLoginPart && (this.state.pageName == pageList.login)
      ? (
        <LoginPage changeLoginStatus={(val, usr, nic) => this.changeLoginStatus(val, usr, nic)} />
      )
      : undefined;

    const pagePart = this.getPage();

    return (
      <Layout className="layout" style={{ background: '#FFFFFF' }}>
        <Header style={{}}>
          <li style={{color:'#FFFFFF',float:'left', listStyle: 'none'}}>TouHou Airline</li>
          <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['2']}
            style={{ lineHeight: '64px', marginLeft: '60%'}}
          >
            <Menu.Item key="1">
              <Link to="/touhouairline_Web_exploded/" onClick={() => this.hideLogin()}>首页</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to="/touhouairline_Web_exploded/" onClick={() => this.hideLogin()}>航班信息</Link>
            </Menu.Item>
            <Menu.Item key="3">
              <Link to="/touhouairline_Web_exploded/" onClick={() => this.hideLogin()}>在线值机</Link>
            </Menu.Item>
            <Menu.Item key="4">
              <Link to="/touhouairline_Web_exploded/" onClick={() => this.gotoLogin()}>会员服务</Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Layout>
          <Sider theme="light" style={{background:'#F0F0F0'}}>
            //TODO: add side list menu for this slider
          </Sider>
          <Layout>
            <Content style={{ lineHeight: '32px', textAlign: 'right', background: '#FFFFFF' }}>
              {loginTitle}
            </Content>
            <Content style={{ background: '#FFFFFF' }}>
              {loginPart}
              {loginPart==undefined?pagePart:undefined}
            </Content>
          </Layout>
        </Layout>
      </Layout>
    );
  }
}
