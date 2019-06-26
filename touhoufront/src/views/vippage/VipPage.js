/* eslint-disable */
import React from 'react';
import {
  Button, Row, Col, Layout, Menu, Icon, Descriptions
} from 'antd';
const { SubMenu } = Menu;
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';
import UserInfoPart from './UserInfoPart';

const pageList = {
  account: 'info',
  passenger: 'passenger'
}

export default class VipPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      pageName: pageList.account
    };
  }

  handleSelectItem(itemName) {
    this.setState({ pageName: itemName });
  }

  getPage() {
    switch (this.state.pageName) {
      case pageList.account:
        return (<UserInfoPart user={this.props.user} changeLoginStatus={(val, usr) => this.props.changeLoginStatus(val, usr)} />);

      default:
        return undefined;
    }
  }

  render() {
    return (
      <Layout style={{height:"1000px"}}>
        <Sider type="light" >
          <Menu
            defaultSelectedKeys={['1']}
            mode="inline"
          >
            <Menu.Item key="1" onclick={() => this.hasndleSelectItem(pageList.account)}>
              <Icon type="home" />
              <span>我的账户</span>
            </Menu.Item>
            <Menu.Item key="2" onclick={() => this.handleSelectItem(pageList.passenger)}>
              <Icon type="idcard" />
              <span>乘机人管理</span>
            </Menu.Item>
            <SubMenu
              key="sub1"
              title={
                <span>
                  <Icon type="profile" />
                  <span>订单管理</span>
                </span>
              }
            >
              <Menu.Item key="3" onclick={() => this.handleSelectItem(pageList.account)}>
                未出行订单
              </Menu.Item>
              <Menu.Item key="4" onclick={() => this.handleSelectItem(pageList.account)}>
                历史订单
              </Menu.Item>
            </SubMenu>
          </Menu>
        </Sider>
        <Content>
          {this.getPage()}
        </Content>
      </Layout>
    );
  }
}
