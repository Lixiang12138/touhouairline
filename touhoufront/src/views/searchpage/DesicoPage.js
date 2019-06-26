/* eslint-disable */
import React from 'react';
import axios from 'axios';
import {
  Link
} from 'react-router-dom';
import {
  Button, Row, Col, Layout, Menu, Form, Icon, Input, DatePicker, Radio
} from 'antd';
const { Header, Footer, Sider, Content } = Layout;
import 'antd/dist/antd.css';
import SearchPage from './SearchPage';
import BookingPage from './BookingPage'
import CreateOrderPage from './CreateOrderPage';
class DesicoPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      returnTimeDisabled: true,
      object: undefined,
      isOneWay: undefined,
      index: 0
    };
  }
  changePage(val, array, isSingle) {
    //console.log("before setState");
    console.log(val);
    console.log(array);
    console.log(isSingle);
    this.setState(
      { index: val, object:array, isOneWay: isSingle }
    );
    //console.log("breakpoint in desicopage");
  }

  render() {
    switch (this.state.index) {
      case 0:
        return (
          <SearchPage changePage={(val,array,isSingle) => this.changePage(val,array,isSingle)} />
        );
      case 1:
        return (
          <BookingPage object={this.state.object} changePage={(val,array,isSingle)=>this.changePage(val,array,isSingle)} />
        );
      default:
        return (<CreateOrderPage/>);
    }
  }
}
export default DesicoPage;