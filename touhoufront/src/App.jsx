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
import { Route, Switch, BrowserRouter as Router } from 'react-router-dom';

import LoginPage from './views/loginpage/LoginPage';
import RegisterPage from './views/loginpage/RegisterPage';
import SearchPage from './views/searchpage/SearchPage';
import './App.css';

const pageList = {
  register: 'register',
  home: 'home',
  login: 'login',
  vip: 'vip',
  aboard: 'aboard',
  search: 'search'
}

export default class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      userName: "",
      nickName: "",
      loginStatus: false,
      pageName: pageList.home
    };
  }

  getPage() {
    switch (this.state.pageName) {
      case pageList.register:
        return (<RegisterPage success={() => this.gotoLogin()} />);

      case pageList.login:
        return (<LoginPage changeLoginStatus={(val, usr, nic) => this.changeLoginStatus(val, usr, nic)} />);

      case pageList.vip:
        return this.state.loginStatus
          ? undefined
          : (<LoginPage changeLoginStatus={(val, usr, nic) => this.changeLoginStatus(val, usr, nic)} />);

      case pageList.search:
        return (<SearchPage userName={this.state.userName} />)

      default:
        return undefined;
    }
  }

  changeLoginStatus(val, usr, nic) {
    this.setState({
      loginStatus: val,
      userName: usr,
      nickName: nic
    });
    if (this.state.loginStatus && (this.state.pageName == pageList.login)) {
      this.setState({ pageName: pageList.home });
    }
  }

  gotoLogin() {
    this.setState({
      pageName: pageList.login
    });
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
          <li style={TitleLoginStyle} onClick={() => this.setState({ pageName: pageList.register })}>注册</li>
          <li style={TitleLoginStyle} onClick={() => this.gotoLogin()}>|登录</li>
        </ul>
      );

    return (
      <Layout className="layout" style={{ background: '#FFFFFF' }}>
        <Header style={{}}>
          <li style={{ color: '#FFFFFF', float: 'left', listStyle: 'none' }}>TouHou Airline</li>
          <Menu
            theme="dark"
            mode="horizontal"
            defaultSelectedKeys={['1']}
            style={{ lineHeight: '64px', marginLeft: '60%' }}
          >
            <Menu.Item key="1">
              <Link to="/touhouairline_Web_exploded/" onClick={() => this.setState({ pageName: pageList.home })}>首页</Link>
            </Menu.Item>
            <Menu.Item key="2">
              <Link to="/touhouairline_Web_exploded/search" onClick={() => this.setState({ pageName: pageList.search })}>航班查询</Link>
            </Menu.Item>
            <Menu.Item key="3">
              <Link to="/touhouairline_Web_exploded/aboard" onClick={() => this.setState({ pageName: pageList.aboard })}>在线值机</Link>
            </Menu.Item>
            <Menu.Item key="4">
              <Link to="/touhouairline_Web_exploded/vip" onClick={() => this.setState({ pageName: pageList.vip })}>会员服务</Link>
            </Menu.Item>
          </Menu>
        </Header>
        <Layout>
          <Layout>
            <Content style={{ lineHeight: '32px', textAlign: 'right', background: '#FFFFFF' }}>
              {loginTitle}
            </Content>
            <Content style={{ background: '#FFFFFF' }}>
              {this.getPage()}
            </Content>
          </Layout>
        </Layout>
      </Layout>
    );
  }
}
